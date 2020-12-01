package demo.elastic.search.controller.helper;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.enums.EnumOutType;
import demo.elastic.search.feign.plus.MappingServicePlus;
import demo.elastic.search.feign.plus.SearchServicePlus;
import demo.elastic.search.framework.Response;
import demo.elastic.search.out.db.mysql.service.DBService;
import demo.elastic.search.out.resource.controller.ResourceController;
import demo.elastic.search.out.resource.service.ResourceService;
import demo.elastic.search.po.helper.DSLHelper;
import demo.elastic.search.po.helper.DSLHelperPlus;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.compound.BoolQuery;
import demo.elastic.search.po.request.dsl.term.TermsQuery;
import demo.elastic.search.service.RedisService;
import demo.elastic.search.thread.ESThreadPoolExecutorService;
import demo.elastic.search.thread.ThreadPoolExecutorService;
import demo.elastic.search.util.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.JSQLParserException;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

import static demo.elastic.search.util.ExcelUtil.percent;

/**
 * helper使用
 */
@Slf4j
@RequestMapping(value = "/HelperController")
@RestController
public class HelperController {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SearchServicePlus searchServicePlus;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private DBService dbService;

    @Autowired
    private MappingServicePlus mappingServicePlus;

    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;

    @Autowired
    private ESThreadPoolExecutorService esThreadPoolExecutorService;

    @Autowired
    private ResourceController resourceController;


    private static final Integer LIMIT_EXCEL = 500000;
    private static final Integer LIMIT_CSV = 10000;
    private static final Integer LIMIT_DB = 50000;
    private static final Integer LIMIT_ES = 10000;
    private static final Integer PROCESS_LIMIT = 10000;//进度处理
    private static final Integer TERMS_ES_LIMIT = 1000;//ES检索的限制
    private static final Integer TERMS_DB_LIMIT = 5000;//入库数量的限制
    private static final Integer TERMS_LIMIT_QUEUE = 100000;//队列的size

    @ApiOperation(value = "请求结构体转换成ES结构体")
    @PostMapping(value = "/DSLHelper")
    public Response DSLHelper(@RequestBody DSLHelper dslHelper) throws JsonProcessingException {
        BoolQuery boolQuery = DSLHelper.DSLHelperToBoolQuery(dslHelper);
        JsonMapper jsonMapper = new JsonMapper();
        String s = StringEscapeUtils.unescapeJavaScript(boolQuery.getRequestBody());//去除双\\
        return Response.Ok(jsonMapper.readTree(s));
    }

    @ApiOperation(value = "导出全部的查询结果(收编入ES体系)")
    @PostMapping(value = "/_search/outputToExcel/{index}")
    public Response _searchOutputToExcel(
            @ApiParam(defaultValue = "tb_object") @PathVariable(value = "index") String index,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)") @RequestParam(value = "scroll") String scroll,
            @ApiParam(value = "导出的size(-1代表全部)") @RequestParam(value = "outPutSize", required = false) Integer outPutSize,
            @RequestBody String body,
            @ApiParam(hidden = true) @RequestHeader(value = "host") String host,
            HttpServletRequest httpServletRequest
    ) throws IOException, IllegalAccessException {
        List<String> filesNames = new ArrayList<>();//文件名称
        List<List<String>> lists = new ArrayList<>();
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        lists = searchServicePlus._searchScrollToList(index, scroll, body, true, (size, total) -> {
            log.info("读取进度:{}/{}->{}", size, total, percent(size, total));
        }, new Function<List<List<String>>, Boolean>() {
            @SneakyThrows
            @Override
            public Boolean apply(List<List<String>> lists) {
                if (lists.size() >= LIMIT_EXCEL) {
                    String fileName = index + DateUtil.getNow() + ".xlsx";
                    filesNames.add(fileName);
                    File file = resourceService.addNewFile(fileName);
                    ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                    sum.set(sum.get() + lists.size());
                    lists.clear();
                }
                //限制导出大小
                if (outPutSize != -1) {
                    if (sum.get() >= outPutSize) {
                        //如果大于导出数据 -> 停止
                        return false;
                    }
                }
                sum.set(sum.get() + 1);//每次接收一条记录 只需要+1
                return true;
            }
        });

        if (lists.size() > 0) {
            String fileName = index + DateUtil.getNow() + ".xlsx";
            filesNames.add(fileName);
            File file = resourceService.addNewFile(fileName);
            ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
            lists.clear();
        }

        List<String> urls = new ArrayList<>();
        filesNames.forEach(path -> {
            String url = resourceController.getDownloadByFileName(host, path);
            urls.add(url);
        });
        return Response.Ok(urls);
    }

    /**
     * 导出为 CSV
     */
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "导出全部的查询结果(收编入ES体系)")
    @PostMapping(value = "/_search/outputToCSV/{index}")
    public Response _searchOutputToCSV(
            @ApiParam(defaultValue = "tb_object") @PathVariable(value = "index") String index,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)") @RequestParam(value = "scroll") String scroll,
            @ApiParam(value = "导出的size(-1代表全部)") @RequestParam(value = "outPutSize", required = false) Integer outPutSize,
            @RequestBody String body,
            @ApiParam(hidden = true) @RequestHeader(value = "host") String host,
            HttpServletRequest httpServletRequest
    ) throws IOException, IllegalAccessException {

        LocalStopWatch stopWatch = new LocalStopWatch();
        List<String> filesNames = new ArrayList<>();//文件名称
        AtomicReference<Integer> sum = new AtomicReference<>(0);
        AtomicReference<List<List<String>>> result = new AtomicReference<>();
        //阻塞队列 -> 用于缓冲生产者和消费者
        ArrayBlockingQueue<List<List<String>>> queue = new ArrayBlockingQueue<>(TERMS_LIMIT_QUEUE);
        AtomicBoolean isStop = new AtomicBoolean(false);
        //生成csv
        String fileName = index + DateUtil.getNow() + ".csv";
        File file = resourceService.addNewFile(fileName);
        OutputStream outputStream = new FileOutputStream(file);
        filesNames.add(fileName);
        AtomicReference<Integer> totalAto = new AtomicReference<>();
        long start = System.currentTimeMillis();
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                searchServicePlus._searchScrollToList(index, scroll, body, true, (size, total) -> {
                    if (null == totalAto.get()) {
                        totalAto.set(total);
                    }
                    if (size % PROCESS_LIMIT == 0) {
                        log.info("读取进度:{}/{}->{}", size, total, percent(size, total));
                    }
                }, new Function<List<List<String>>, Boolean>() {
                    @SneakyThrows
                    @Override
                    public Boolean apply(List<List<String>> lists) {
                        if (result.get() == null) {
                            result.set(lists);
                        }
                        //加入队列
                        if (lists.size() > LIMIT_ES) {
                            queue.put((new ArrayList<>(lists)));
                            lists.clear();
                        }
                        sum.set(sum.get() + lists.size());

                        //限制导出大小
                        if (outPutSize != -1) {
                            if (sum.get() >= outPutSize) {
                                //如果大于导出数据 -> 停止
                                return false;
                            }
                        }
                        sum.set(sum.get() + 1);//每次接收一条记录 只需要+1
                        return true;
                    }
                });
                //补全最后的数据
                queue.put((new ArrayList<>(result.get())));
                isStop.set(true);//代表是否结束
            }
        }).start();
        //消费者
        AtomicInteger write = new AtomicInteger();
        while (isStop.get() == false || queue.size() > 0) {
            //没有结束 或者 队列的size >0 就一只循环
            List<List<String>> poll = queue.poll();
            if (null != poll) {
                ExcelUtil.writeListCSV(poll, outputStream, (line, size) -> {
                    write.getAndIncrement();
                    if (write.get() % PROCESS_LIMIT == 0) {
                        long use = System.currentTimeMillis() - start;//耗时 毫秒
                        BigDecimal numPeerSecond = new BigDecimal(write.get()).divide(new BigDecimal(use), 3, RoundingMode.HALF_DOWN).multiply(new BigDecimal(1000));//每秒处理的数量
                        BigDecimal timeSecond = new BigDecimal(totalAto.get()).divide(numPeerSecond, 3, RoundingMode.HALF_DOWN);//剩余时间(Second)
                        BigDecimal timeMinutes = timeSecond.divide(new BigDecimal(60), 3, RoundingMode.HALF_DOWN);//剩余时间(Minutes)
                        BigDecimal timeHour = timeMinutes.divide(new BigDecimal(60), 3, RoundingMode.HALF_DOWN);//剩余时间(Hour)
                        log.info("写入进度:{}/{}->{},以使用耗时(Second):{} ,每秒处理的数量:{},预计总耗时时间(Second):{}, Minutes:{} ,Hour:{},", write.get(), totalAto.get(), percent(write.get(), totalAto.get()), use / 1000, numPeerSecond, timeSecond, timeMinutes, timeHour);
                    }
                });
            }
        }

        //关闭流
        outputStream.close();
        List<String> urls = new ArrayList<>();
        filesNames.forEach(path -> {
            String url = resourceController.getDownloadByFileName(host, path);
            urls.add(url);
        });
        return Response.Ok(urls);
    }


    @ApiOperation(value = "导出全部的查询到DB(收编入ES体系)")
    @PostMapping(value = "/_search/outputToDB/{index}")
    public Response _searchOutputToDB(
            @ApiParam(defaultValue = "tb_object")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = true) String scroll,
            @ApiParam(value = "导出的size(-1代表全部)") @RequestParam(value = "outPutSize", required = false) Integer outPutSize,
            @RequestBody String body
    ) throws Exception {
        String tableName = null;
        tableName = index;
        if (index.matches("comstore_(.*)")) {
            tableName = index.replaceAll("comstore_(.*)", "$1");
        }
        if (index.matches("comstore_(.*)_v\\d+")) {
            tableName = index.replaceAll("comstore_(.*)_v\\d+", "$1");
        }
        if (index.matches("(.*)_v\\d+")) {
            tableName = index.replaceAll("(.*)_v\\d+", "$1");
        }
        if (index.matches("(.*)?_ext")) {
            tableName = index.replaceAll("(.*)?_ext", "$1");
        }
        if (index.matches("comstore_(.*)?_ext")) {
            tableName = index.replaceAll("comstore_(.*)?_ext", "$1");
        }

        String targetTable = tableName + "_" + DateUtil.getNow();
        dbService.cloneTableStruct(tableName, targetTable);//创建新的表
        List<String> fieldNames = new ArrayList<>();
        SearchSourceBuilder tmpS = JSON.parseObject(JSON.toJSON(body).toString(), SearchSourceBuilder.class);
        if (tmpS.get_source().contains("*")) {
            fieldNames.addAll(mappingServicePlus.getFieldNamesList(index));//获取
        } else {
            fieldNames.addAll(tmpS.get_source());
        }
        fieldNames.remove("row_feature");
        fieldNames.remove("ES_MOD_TIME");
        List<List<String>> lists = new ArrayList<>();
        AtomicReference<Integer> i = new AtomicReference<>(0);
        AtomicReference<Integer> sum = new AtomicReference<>(0);

        lists = searchServicePlus._searchScrollToList(index, scroll, body, true, (size, total) -> {
            log.info("读取进度:{}/{}->{}", size + 1, total, percent(size + 1, total));
        }, new Function<List<List<String>>, Boolean>() {
            @SneakyThrows
            @Override
            public Boolean apply(List<List<String>> lists) {
                if (lists.size() >= LIMIT_DB) {
                    List<List<String>> tmp = new ArrayList<>(lists);
                    threadPoolExecutorService.addWork(new Runnable() {
                        @Override
                        public void run() {
                            dbService.batchInsert(targetTable, tmp, fieldNames);
                        }
                    });
                    lists.clear();
                }
                //限制导出大小
                if (outPutSize != -1) {
                    if (sum.get() >= outPutSize) {
                        //如果大于导出数据 -> 停止
                        return false;
                    }
                }
                sum.set(sum.get() + 1);//每次接收一条记录 只需要+1
                return true;
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

        threadPoolExecutorService.waitComplete();
        log.info("提取完成,return");
        return Response.Ok(Arrays.asList(targetTable));
    }

    /**
     * SELECT * FROM tb_object WHERE
     * F1 IS NOT NULL AND F2 IS NULL
     * AND F1 = '1' AND F1 <> '1'
     * AND F1 IN ('1','2') AND F1 NOT IN ('1','2')
     * AND F3 BETWEEN 30 AND 40
     * AND F3 >= 30 AND F3 <= 30
     * AND REGEXP_LIKE(F4,'\d*') AND  NOT REGEXP_LIKE(F4,'\d*')
     * AND F4 LIKE 'St%'  AND F4 NOT LIKE 'St%'
     * <p>
     * <p>
     * SELECT * FROM tb_object WHERE
     * F1 IS NOT NULL AND F2 IS NULL
     * AND F1 = '1' AND F1 <> '1'
     * AND F1 IN ('1') AND F1 NOT IN ('1','2')
     * AND F3 BETWEEN 30 AND 40
     * AND F3 >= '30' AND F3 <= '40'
     * AND REGEXP_LIKE(F4,'\d*') AND  NOT REGEXP_LIKE(F4,'\d*')
     * AND F4 LIKE 'St%'  AND F4 NOT LIKE 'St%'
     *
     * <pre>
     *  测试用例
     *  :
     * SELECT * FROM "tb_object" WHERE
     * "F1" IS NOT NULL AND "F2" IS NULL
     * AND "F1" = '1' AND "F1" <> '1'
     * AND "F1" IN ('1','2') AND "F1" NOT IN ('1','2')
     * AND "F3" BETWEEN 30 AND 40
     * AND "F3" >= 30 AND F3 <= 30
     * AND REGEXP_LIKE("F2",'[\u4e00-\u9fa5]+') AND  NOT REGEXP_LIKE("F1",'[0-9]*')
     * AND "F4" LIKE 'St%'  AND "F4" NOT LIKE 'St%'
     *
     *
     * SELECT * FROM tb_object WHERE F1 = '1'
     *
     * SELECT * FROM tb_object WHERE F1 <> '1'
     *
     * SELECT * FROM tb_object WHERE F1 LIKE '1*'
     *
     * SELECT * FROM tb_object WHERE F1 NOT LIKE '1*'
     *
     * SELECT * FROM tb_object WHERE F1 IS NULL
     *
     * SELECT * FROM tb_object WHERE F1 IS NOT NULL
     *
     * SELECT * FROM tb_object WHERE F1 IN ('1','2')
     *
     * SELECT * FROM tb_object WHERE F1 NOT IN ('1','2')
     *
     * SELECT * FROM tb_object WHERE F10 <= 1
     *
     * SELECT * FROM tb_object WHERE F10 >= 50
     *
     * SELECT * FROM tb_object WHERE F10 BETWEEN 1 AND 49
     *
     * SELECT * FROM tb_object WHERE  REGEXP_LIKE(F1,'1.*')
     *
     * SELECT * FROM tb_object WHERE NOT REGEXP_LIKE(F1,'1.*')
     *
     * </pre>
     *
     * @param sql
     * @return
     * @throws JsonProcessingException
     * @throws SqlParseException
     */
    @ApiOperation(value = "SQL转换成ES结构体")
    @PostMapping(value = "/SQLToEsHelper")
    public Response SQLToEsHelper(@RequestBody String sql) throws JsonProcessingException, SqlParseException, JSQLParserException {
        DSLHelperPlus dslHelperPlus = DSLHelperPlus.sqlToDSLHelperPlus(sql);
        return Response.Ok(dslHelperPlus);

    }


    @ApiOperation(value = "批量terms导出的查询到DB/CSV/EXCEL/JSON(收编入ES体系)")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE),
            @ApiImplicitParam(name = "listFile", value = "listFile", dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "field", value = "field", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "values", value = "values", dataTypeClass = String.class, paramType = "form", allowMultiple = true)
    })
    @RequestMapping(value = "/_searchTermsToDb/{index}", method = {RequestMethod.POST})
    public Response _searchTermsToDb(
            @ApiParam(defaultValue = "F6", value = "下面的文件中需要匹配的值") @RequestParam(value = "field", required = false) String field,
            @ApiParam(value = "检索语法 ORACLE语法") @RequestParam(value = "request") String sql,
            @RequestParam(value = "values", required = false) List<String> values,
            @RequestParam(name = "listFile", required = false) MultipartFile listFile,
            @RequestParam(name = "outType", required = true) EnumOutType enumOutType,
            @ApiParam(hidden = true) @RequestHeader(value = "host") String host,
            HttpServletRequest httpServletRequest

    ) throws Exception {
        DSLHelperPlus dslHelperPlus = DSLHelperPlus.sqlToDSLHelperPlus(sql);
        String index = dslHelperPlus.getIndex();
        List<String> filesNames = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();

        /**
         * 队列数量,这个是生产者和消费者的缓冲地带,越大,生产者和消费者越不受限制！
         */
        ArrayBlockingQueue<List<String>> queue = new ArrayBlockingQueue<>(TERMS_LIMIT_QUEUE);
        List<String> lines = new ArrayList<>(1024);
        List<Runnable> runnables = new ArrayList<>(100000);//执行的任务
        /**
         * 获取 title
         */
        List<String> fieldNames = new ArrayList<>();
        if (dslHelperPlus.getFields().contains("*")) {
            fieldNames.addAll(mappingServicePlus.getFieldNamesList(index));//获取
        } else {
            fieldNames.addAll(dslHelperPlus.getFields());
        }
        /**
         * 开始操作 读取行数较大的文本
         */
        FileUtil.readBigFileByLine(listFile.getInputStream(), new Consumer<String>() {
            @Override
            public void accept(String line) {
                lines.add(line);
                int process = atomicInteger.getAndIncrement() % PROCESS_LIMIT;
                if (process == 0) {
                    log.info("处理进度 :{}", atomicInteger.get());
                }

                if (lines.size() > TERMS_ES_LIMIT) {
                    SearchSourceBuilder<BoolQuery, VoidAggs> request = new SearchSourceBuilder<>();
                    BoolQuery boolQuery = DSLHelper.DSLHelperToBoolQuery(dslHelperPlus.getDslHelper());
                    request.from(0).size(3000).source(dslHelperPlus.getFields()).query(boolQuery);
                    boolQuery.must(TermsQuery.builderQuery(field, lines));
                    String requestBody = request.getRequestBody();
                    log.info("lines.size:{}", lines.size());
                    lines.clear();
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            List<List<String>> lists = searchServicePlus._searchScrollToList(index, "1m", requestBody, false);
                            lists.forEach(vo -> {
                                try {
                                    queue.put(vo);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    };
                    runnables.add(runnable);
                }
            }
        });
        if (lines.size() > 0) {
            //补全空缺
            SearchSourceBuilder<BoolQuery, VoidAggs> request = new SearchSourceBuilder<>();
            BoolQuery boolQuery = DSLHelper.DSLHelperToBoolQuery(dslHelperPlus.getDslHelper());
            request.from(0).size(3000).source(dslHelperPlus.getFields()).query(boolQuery);
            boolQuery.must(TermsQuery.builderQuery(field, lines));
            String requestBody = request.getRequestBody();
            log.info("lines.size:{}", lines.size());
            lines.clear();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    List<List<String>> lists = searchServicePlus._searchScrollToList(index, "1m", requestBody, false);
                    lists.forEach(vo -> {
                        try {
                            queue.put(vo);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    });
                }
            };
            runnables.add(runnable);
        }
        /**
         * 开始下一步任务
         */
        log.info("生产者任务数量:{}", runnables.size());
        //批量提交任务
        for (Runnable runnable : runnables) {
            esThreadPoolExecutorService.addWork(runnable);
        }

        if (enumOutType.equals(EnumOutType.DB)) {
            //准备入库
            String targetTable = index + "_" + DateUtil.getNow();
            dbService.cloneTableStruct(index, targetTable);//创建新的表

            List<List<String>> tmp = new ArrayList<>(1024);
            while (esThreadPoolExecutorService.isComplete() != true || queue.size() > 0) {
                List<String> poll = queue.poll();
                if (null != poll) {
                    tmp.add(poll);
                    if (tmp.size() > TERMS_DB_LIMIT) {
                        esThreadPoolExecutorService.isCompleteLog();
                        List<List<String>> tmp2 = tmp;
                        threadPoolExecutorService.addWork(new Runnable() {
                            @Override
                            public void run() {
                                dbService.batchInsert(targetTable, tmp2, fieldNames);
                            }
                        });
                        tmp = new ArrayList<>(1024);
                    }
                }
            }
            //补全最后的数据
            List<List<String>> tmp2 = tmp;
            threadPoolExecutorService.addWork(new Runnable() {
                @Override
                public void run() {
                    dbService.batchInsert(targetTable, tmp2, fieldNames);
                }
            });
            threadPoolExecutorService.waitComplete();
            filesNames.add(targetTable);//统一处理
        } else if (enumOutType.equals(EnumOutType.EXCEL)) {
            List<List<String>> tmp = new ArrayList<>(LIMIT_EXCEL);
            while (esThreadPoolExecutorService.isComplete() != true || queue.size() > 0) {
                List<String> poll = queue.poll();
                if (null != poll) {
                    tmp.add(poll);
                    if (tmp.size() > LIMIT_EXCEL) {
                        String fileName = index + DateUtil.getNow() + ".xlsx";
                        filesNames.add(fileName);
                        File file = resourceService.addNewFile(fileName);
                        OutputStream outputStream = new FileOutputStream(file);
                        //补全头
                        tmp.add(0, fieldNames);
                        //具体写入
                        ExcelUtil.writeListSXSS(tmp, outputStream, (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                        tmp.clear();
                    }
                }
            }
            if (tmp.size() > 0) {
                //补全最后的数据
                String fileName = index + DateUtil.getNow() + ".xlsx";
                filesNames.add(fileName);
                File file = resourceService.addNewFile(fileName);
                OutputStream outputStream = new FileOutputStream(file);
                //补全头
                tmp.add(0, fieldNames);
                ExcelUtil.writeListSXSS(tmp, outputStream, (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                tmp.clear();
            }

        } else if (enumOutType.equals(EnumOutType.CSV)) {
            List<List<String>> tmp = new ArrayList<>(LIMIT_CSV);
            //补全最后的数据
            String fileName = index + DateUtil.getNow() + ".csv";
            filesNames.add(fileName);
            File file = resourceService.addNewFile(fileName);
            OutputStream outputStream = new FileOutputStream(file);
            tmp.add(0, fieldNames);
            while (esThreadPoolExecutorService.isComplete() != true || queue.size() > 0) {
                List<String> poll = queue.poll();
                if (null != poll) {
                    tmp.add(poll);
                    if (tmp.size() > LIMIT_CSV) {
                        //具体写入
                        ExcelUtil.writeListCSV(tmp, outputStream, (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                        tmp.clear();
                    }
                }
            }
            if (tmp.size() > 0) {
                //补全最后的数据
                ExcelUtil.writeListCSV(tmp, outputStream, (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                tmp.clear();
            }
        } else {
            return Response.Ok("暂未支持");
        }

        List<String> urls = new ArrayList<>();
        filesNames.forEach(path -> {
            if (!enumOutType.equals(EnumOutType.DB)) {
                String url = resourceController.getDownloadByFileName(host, path);
                urls.add(url);
            } else {
                urls.add(path);
            }
        });
        return Response.Ok(urls);
    }

    /**
     * SELECT F2,F1 FROM  tb_object  WHERE  F1  IS NOT NULL AND F21 = '公司' ORDER BY  F3 ASC ,F1 DESC
     *
     * @param sql
     * @return
     * @throws JsonProcessingException
     * @throws SqlParseException
     * @throws JSQLParserException
     */
    @ApiOperation(value = "SQL转换成ES结构体Beta版本")
    @PostMapping(value = "/SQLToEsHelper_Beta")
    public Response SQLToEsHelper_Beta(@RequestBody String sql) throws JsonProcessingException, SqlParseException, JSQLParserException {

        DSLHelper dslHelper = new DSLHelper();
        SqlKind kind = SQLMysqlCalciteParseUtils.getKind(sql);
        if (!kind.equals(SqlKind.SELECT) && !kind.equals(SqlKind.ORDER_BY)) {
            throw new RuntimeException("只支持 select/order by 类型,当前类型是:" + kind);
        }
        List<String> simpleSelectList = SQLMysqlCalciteParseUtils.getSimpleSelectList(sql);
        List<SqlBasicCall> sqlBasicCalls = SQLMysqlCalciteParseUtils.getWhereSimpleSqlBasicCall(sql);
        for (SqlBasicCall sqlBasicCall : sqlBasicCalls) {
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IS_NOT_NULL)) {
                //处理null
                DSLHelper.Exists exists = new DSLHelper.Exists();
                exists.setField(sqlBasicCall.getOperands()[0].toString());
                dslHelper.must(exists);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.EQUALS)) {
                //处理 =
                DSLHelper.Term term = new DSLHelper.Term();
                term.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                term.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(term);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IN)) {
                //处理 in
                DSLHelper.Terms terms = new DSLHelper.Terms();
                terms.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                if (sqlBasicCall.getOperands()[1] instanceof SqlNodeList) {
                    SqlNodeList sqlNodeList = (SqlNodeList) sqlBasicCall.getOperands()[1];
                    List<String> values = new ArrayList<>();
                    sqlNodeList.getList().forEach(sqlNode -> {
                        values.add(SQLMysqlCalciteParseUtils.getValue(sqlNode));
                    });
                    terms.setValue(values);
                } else {
                    throw new RuntimeException("In 后不是数组");
                }
                dslHelper.must(terms);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.BETWEEN)) {
                //处理 between
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGte(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                range.setLte(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[2]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.GREATER_THAN_OR_EQUAL)) {
                //处理 >=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGte(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.GREATER_THAN)) {
                //处理 >=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setGt(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LESS_THAN_OR_EQUAL)) {
                //处理 <=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setLte(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LESS_THAN)) {
                //处理 <=
                DSLHelper.Range range = new DSLHelper.Range();
                range.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                range.setLt(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must(range);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LIKE)) {
                //处理 like -> 注意类型都是 LIKE
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("LIKE")) {
                    DSLHelper.Wildcard wildcard = new DSLHelper.Wildcard();
                    wildcard.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    wildcard.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must(wildcard);
                }

            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.OTHER_FUNCTION)) {
                //处理 regex_like 这里当做函数来处理
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("REGEXP_LIKE")) {
                    DSLHelper.Regexp regexp = new DSLHelper.Regexp();
                    regexp.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    regexp.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must(regexp);
                } else {
                    throw new RuntimeException("出现不支持函数:" + sqlBasicCall.getOperator().getName());
                }
            }
            //处理 not

            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.IS_NULL)) {
                //处理 not null
                //处理null
                DSLHelper.Exists exists = new DSLHelper.Exists();
                exists.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                dslHelper.must_not(exists);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT_EQUALS)) {
                //处理 <>
                DSLHelper.Term term = new DSLHelper.Term();
                term.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                term.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                dslHelper.must_not(term);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT_IN)) {
                //处理 not in
                DSLHelper.Terms terms = new DSLHelper.Terms();
                terms.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                if (sqlBasicCall.getOperands()[1] instanceof SqlNodeList) {
                    SqlNodeList sqlNodeList = (SqlNodeList) sqlBasicCall.getOperands()[1];
                    List<String> values = new ArrayList<>();
                    sqlNodeList.getList().forEach(sqlNode -> {
                        values.add(SQLMysqlCalciteParseUtils.getValue(sqlNode));
                    });
                    terms.setValue(values);
                } else {
                    throw new RuntimeException("In 后不是数组");
                }
                dslHelper.must_not(terms);
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.LIKE)) {
                //处理 not like -> 注意类型都是 LIKE
                if (sqlBasicCall.getOperator().getName().equalsIgnoreCase("NOT LIKE")) {
                    DSLHelper.Wildcard wildcard = new DSLHelper.Wildcard();
                    wildcard.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[0]));
                    wildcard.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCall.getOperands()[1]));
                    dslHelper.must_not(wildcard);
                }
            }
            if (sqlBasicCall.getOperator().getKind().equals(SqlKind.NOT)) {
                //处理 NOT 先判断是NOT类型。
                //处理 regex_like 这里当做函数来处理
                Arrays.stream(sqlBasicCall.getOperands()).forEach(sqlNode -> {
                    if (sqlNode instanceof SqlBasicCall) {
                        SqlBasicCall sqlBasicCallTmp = (SqlBasicCall) sqlNode;
                        if (sqlBasicCallTmp.getOperator().getName().equalsIgnoreCase("REGEXP_LIKE")) {
                            DSLHelper.Regexp regexp = new DSLHelper.Regexp();
                            regexp.setField(SQLMysqlCalciteParseUtils.getValue(sqlBasicCallTmp.getOperands()[0]));
                            regexp.setValue(SQLMysqlCalciteParseUtils.getValue(sqlBasicCallTmp.getOperands()[1]));
                            dslHelper.must_not(regexp);
                        } else {
                            throw new RuntimeException("出现不支持函数:" + sqlBasicCall.getOperator().getName());
                        }
                    }
                });
            }

        }

        SqlNode from = SQLMysqlCalciteParseUtils.getFrom(sql);
        DSLHelperPlus dslHelperPlus = new DSLHelperPlus();
        dslHelperPlus.setDslHelper(dslHelper);
        dslHelperPlus.setIndex(from.toString());
        dslHelperPlus.setFields(simpleSelectList);

        Map<String, String> sqlOrderMap = SQLMysqlCalciteParseUtils.getSqlOrderMap(sql);
        dslHelperPlus.getSort().add(sqlOrderMap);
        return Response.Ok(dslHelperPlus);

    }

}
