package demo.elastic.search.out;

import com.alibaba.fastjson.JSONArray;
import demo.elastic.search.feign.ScrollService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.term.TermQuery;
import demo.elastic.search.po.response.ESResponse;
import demo.elastic.search.util.ExcelUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static demo.elastic.search.util.ExcelUtil.percent;

@SpringBootTest
@Slf4j
public class TagsTest {

    @Resource
    private SearchService searchService;

    @Resource
    private ScrollService scrollService;

    private final String index = "tags";

    @Test
    public void test() throws IOException, IllegalAccessException {
        InputStream resourceAsStream = TagsTest.class.getResourceAsStream("/out/TagsWind.txt");
        List<String> list = IOUtils.readLines(resourceAsStream, "UTF-8");

        List<TagsJP> tagsJPS = Collections.synchronizedList(new ArrayList<>(100000));
        for (String s : list) {
            try {
                log.info("开始处理:{}", s);
                SearchSourceBuilder<TermQuery, VoidAggs> request = new SearchSourceBuilder<>();
                request.from(0).size(10000).query(QueryBuilders.termQuery("windcode", s));
                log.info("请求body:{}", request.getRequestBody());
                String response = searchService.DSL_search_term(index, request);
                ESResponse parse = ESResponse.parse(response);
                parse.getHits().getHits().forEach(innerHits -> {
                    Map<String, Object> source = innerHits.getSource();
                    Tags masterTags = getTags(source);
                    String masterlabel = Arrays.toString(masterTags.getLabel().toArray());
                    masterTags.getLabel().forEach(masterLabel -> {
                        //获取竞品数据
                        List<Tags> jpTags = getOtherByLabel(masterLabel);
                        jpTags.forEach(jpTag -> {
                            TagsJP tagsJP = new TagsJP();
                            tagsJP.setMastercompname(masterTags.getCompname());
                            tagsJP.setMasteritemID(masterTags.getItemID());
                            tagsJP.setMasterlabel(masterlabel);
                            tagsJP.setMastersource(masterTags.getSource());
                            tagsJP.setMasterwindcode(masterTags.getWindcode());
                            //
                            tagsJP.setJpcompname(jpTag.getCompname());
                            tagsJP.setJpitemID(jpTag.getItemID());
                            tagsJP.setJplabel(Arrays.toString(jpTag.getLabel().toArray()));
                            tagsJP.setJpsource(jpTag.getSource());
                            tagsJP.setJpwindcode(jpTag.getWindcode());
                            //交集
                            jpTag.getLabel().retainAll(masterTags.getLabel());
                            tagsJP.setLabelOverlap(Arrays.toString(jpTag.getLabel().toArray()));
                            tagsJPS.add(tagsJP);
                        });
                    });
                });
                log.info("response:{}", response);
                Set<TagsJP> set = new HashSet<>(tagsJPS);
                List<TagsJP> result = new ArrayList<>(set);


                File file = new File("d:/tags/" + s + ".xlsx");
                ExcelUtil.writeVosSXSS(result, new FileOutputStream(file), true, (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                tagsJPS.clear();
            } catch (Exception e) {
                log.error("{} 处理异常", s, e);
            }
        }

    }


    /**
     * 获取标签相关的其他数据
     *
     * @param label
     * @return
     * @throws IOException
     */
    public List<Tags> getOtherByLabel(String label) {
        List<Tags> tagsList = new ArrayList<>(10000);
        SearchSourceBuilder<TermQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(100).query(QueryBuilders.termQuery("label", label));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_term(index, request);
        String result = searchService._search(index, "10m", request.getRequestBody());
        ESResponse parse = ESResponse.parse(result);

        parse.getHits().getHits().forEach(innerHits -> {
            Map<String, Object> source = innerHits.getSource();
            tagsList.add(getTags(source));
        });
        do {
            String scrollId = parse.getScrollId();
            result = scrollService._search("1m", scrollId);
            parse = ESResponse.parse(result);
            parse.getHits().getHits().forEach(innerHits -> {
                Map<String, Object> source = innerHits.getSource();
                tagsList.add(getTags(source));
            });
        } while (parse.getHits().getHits().size() > 0);

        return tagsList;
    }

    private Tags getTags(Map<String, Object> source) {
        Tags tags = new Tags();
        String compname = source.get("compname").toString();
        String itemID = source.get("itemID").toString();
        String sourceStr = source.get("source").toString();
        String windcode = source.get("windcode").toString();
        List<String> labelList = new ArrayList<>();
        Object labelObj = source.get("label");
//        log.info("labelObj:{}", labelObj);
        if (labelObj instanceof JSONArray) {
            //如果标签不为空
            JSONArray labelJsonArray = (JSONArray) labelObj;
            labelJsonArray.forEach(labelVo -> {
//                log.info("labelVo:{}", labelVo);
                labelList.add(labelVo.toString());
            });
        }
        tags.setSource(sourceStr);
        tags.setCompname(compname);
        tags.setItemID(itemID);
        tags.setLabel(labelList);
        tags.setWindcode(windcode);
        return tags;
    }


    @Data
    class Tags {
        String compname;
        String itemID;
        List<String> label;
        String source;
        String windcode;
    }

    @Data
    class TagsJP {
        String mastercompname;
        String masteritemID;
        String masterlabel;
        String mastersource;
        String masterwindcode;
        //jp
        String jpcompname;
        String jpitemID;
        String jplabel;
        String jpsource;
        String jpwindcode;
        //交集
        String labelOverlap;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TagsJP tagsJP = (TagsJP) o;
            return Objects.equals(mastercompname, tagsJP.mastercompname) &&
                    Objects.equals(masteritemID, tagsJP.masteritemID) &&
                    Objects.equals(masterlabel, tagsJP.masterlabel) &&
                    Objects.equals(mastersource, tagsJP.mastersource) &&
                    Objects.equals(masterwindcode, tagsJP.masterwindcode) &&
                    Objects.equals(jpcompname, tagsJP.jpcompname) &&
                    Objects.equals(jpitemID, tagsJP.jpitemID) &&
                    Objects.equals(jplabel, tagsJP.jplabel) &&
                    Objects.equals(jpsource, tagsJP.jpsource) &&
                    Objects.equals(jpwindcode, tagsJP.jpwindcode) &&
                    Objects.equals(labelOverlap, tagsJP.labelOverlap);
        }

        @Override
        public int hashCode() {
            return Objects.hash(mastercompname, masteritemID, masterlabel, mastersource, masterwindcode, jpcompname, jpitemID, jplabel, jpsource, jpwindcode, labelOverlap);
        }
    }
}
