package demo.elastic.search.test;

import demo.elastic.search.feign.plus.MappingServicePlus;
import demo.elastic.search.feign.plus.SearchServicePlus;
import demo.elastic.search.out.db.mysql.service.DBService;
import demo.elastic.search.po.request.QueryBaseRequest;
import demo.elastic.search.po.request.dsl.term.RegexpRequest;
import demo.elastic.search.thread.ThreadPoolExecutorService;
import demo.elastic.search.util.DateUtil;
import demo.elastic.search.util.IOUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

import static demo.elastic.search.util.ExcelUtil.percent;

@Slf4j
@SpringBootTest
public class SearchTest {

    @Autowired
    private MappingServicePlus mappingServicePlus;

    @Autowired
    private DBService dbService;

    @Autowired
    private SearchServicePlus searchServicePlus;

    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;


    @Test
    public void xx() throws Exception {
        InputStream resourceAsStream = SearchTest.class.getResourceAsStream("/公司尾缀.txt");
        List<String> list = IOUtils.readLines(resourceAsStream, "UTF-8");

        String index = "tb_object_6467";


        String tableName = null;
        tableName = index;
        if (index.matches("comstore_(.*)")) {
            tableName = index.replaceAll("comstore_(.*)", "$1");
        }
        if (index.matches("(.*)?_ext")) {
            tableName = index.replaceAll("(.*)?_ext", "$1");
        }
        if (index.matches("comstore_(.*)?_ext")) {
            tableName = index.replaceAll("comstore_(.*)?_ext", "$1");
        }

        String targetTable = tableName + "_" + DateUtil.getNow();
        dbService.cloneTableStruct(tableName, targetTable);//创建新的表
        List<String> fieldNames = mappingServicePlus.getFieldNamesList(index);//获取name
        fieldNames.remove("row_feature");
        fieldNames.remove("ES_MOD_TIME");
        List<List<String>> lists = new ArrayList<>();
        AtomicReference<Integer> i = new AtomicReference<>(0);


        List<String> resultToRegexList = new ArrayList<>();
        while (list.size() > 0) {
            List<String> tmpList = null;
            if (list.size() >= 5) {
                tmpList = list.subList(0, 5);
            } else {
                tmpList = list.subList(0, list.size());
            }
            StringBuilder queryBody = new StringBuilder("(");
            for (String line : tmpList) {
                line = line.replace(".", "\\.");
                if (line.matches("[\\u4e00-\\u9fa5]+")) {
                    //纯中文没有空格
//                    queryBody.append("[(（].*？[)）].*" + getIgnoreCaseRegex(line) + "|");
                    queryBody.append(".*?" + getIgnoreCaseRegex(line) + "[(（].*?[)）]" + "|");
//                    queryBody.append(".*" + getIgnoreCaseRegex(line) + "|");
                } else {
                    //非纯有空格
//                    queryBody.append("[(（].*？[)）].* " + getIgnoreCaseRegex(line) + "|");
                    queryBody.append(".*? " + getIgnoreCaseRegex(line) + "[(（].*?[)）]" + "|");
//                    queryBody.append(".* " + getIgnoreCaseRegex(line) + "|");
                }
            }
            String result = queryBody.substring(0, queryBody.length() - 1) + ")";
            resultToRegexList.add(result);
            tmpList.clear();
        }

        int dealLine = 0;
        int sumLine = resultToRegexList.size();

        for (String liresultToRegex : resultToRegexList) {
            dealLine++;
            log.info("处理进度:{}/{}->{}", sumLine, percent(dealLine, sumLine));
            String requestBody = QueryBaseRequest.builderRequest(0, 1000, RegexpRequest.builderQuery("F2_6467", liresultToRegex)).getRequestBody();
            lists = searchServicePlus._searchScrollToList(index, "1m", requestBody, false, (size, total) -> {
                log.info("读取进度:{}/{}->{}", size, total, percent(size, total));
            }, new Consumer<List<List<String>>>() {
                @SneakyThrows
                @Override
                public void accept(List<List<String>> lists) {
                    if (lists.size() >= 10000) {
                        List<List<String>> tmp = new ArrayList<>(lists);
                        threadPoolExecutorService.addWork(new Runnable() {
                            @Override
                            public void run() {
                                dbService.batchInsert(targetTable, tmp, fieldNames);
                            }
                        });
                        lists.clear();
                    }
                }
            });
            if (lists.size() > 0) {
                List<List<String>> tmp = new ArrayList<>(lists);
                threadPoolExecutorService.addWork(new Runnable() {
                    @Override
                    public void run() {
                        dbService.batchInsert(targetTable, tmp, fieldNames);
                    }
                });
                lists.clear();
            }


        }


        threadPoolExecutorService.waitComplete();
    }

    /**
     * 获取忽略大小写的正则
     *
     * @return
     */
    public String getIgnoreCaseRegex(String source) {
        String result = "";
        String english = "[a-zA-Z]";
        char[] chars = source.toCharArray();
        for (char c : chars) {
            if (String.valueOf(c).matches(english)) {
                result += "[" + Character.toUpperCase(c) + Character.toLowerCase(c) + "]";
            } else {
                result += c;
            }
        }
        return result;
    }

}
