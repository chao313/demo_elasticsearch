package demo.elastic.search.out.es.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.engine.script.impl.JavaScriptExecuteScript;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.feign.MappingService;
import demo.elastic.search.feign.ScrollService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.feign.plus.MappingServicePlus;
import demo.elastic.search.feign.plus.ScrollServicePlus;
import demo.elastic.search.feign.plus.SearchServicePlus;
import demo.elastic.search.framework.Code;
import demo.elastic.search.framework.Response;
import demo.elastic.search.out.comm.OutType;
import demo.elastic.search.out.db.mysql.service.DBService;
import demo.elastic.search.out.kafka.KafkaMsg;
import demo.elastic.search.out.kafka.KafkaOutService;
import demo.elastic.search.out.remove.compound.Body;
import demo.elastic.search.out.remove.compound.level.base.Terms;
import demo.elastic.search.out.resource.service.ResourceService;
import demo.elastic.search.po.response.ESResponse;
import demo.elastic.search.po.response.InnerHits;
import demo.elastic.search.po.response.buckets.BucketsRoot;
import demo.elastic.search.thread.ThreadPoolExecutorService;
import demo.elastic.search.util.DateUtil;
import demo.elastic.search.util.ExcelUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;

import static demo.elastic.search.util.ExcelUtil.percent;


/**
 * 进行自定义操作
 */
@RequestMapping(value = "/CustomController")
@RestController
@Slf4j
public class CustomController {

    @Resource
    private DocumentService documentService;

    @Resource
    private SearchService searchService;

    @Resource
    private ScrollService scrollService;

    @Resource
    private MappingService mappingService;

    @Autowired
    private MappingServicePlus mappingServicePlus;

    @Autowired
    private SearchServicePlus searchServicePlus;

    @Autowired
    private ScrollServicePlus scrollServicePlus;

    @Autowired
    private KafkaOutService kafkaOutService;

    @Autowired
    private JavaScriptExecuteScript javaScriptExecuteScript;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private DBService dbService;

    @Autowired
    private ThreadPoolExecutorService threadPoolExecutorService;

    //    private static final Integer LIMIT = 1000000;
    private static final Integer LIMIT = 500000;
    private static final Integer LIMIT_DB = 50000;


    @ApiOperation(value = "accounts.json 数据批量插入")
    @PostMapping(value = "/{index}/_bulk")
    public Response _bulk_accounts(@ApiParam(defaultValue = "index_bulk", value = "指定导入的index")
                                   @PathVariable(value = "index") String index) throws IOException {
        File file = AwareUtil.resourceLoader.getResource("classpath:data/accounts.json").getFile();
        String body = FileUtils.readFileToString(file, "UTF-8");
        String s = documentService._bulk(index, body);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "导出全部的查询结果")
    @PostMapping(value = "/{index}/_search/outputToExcel")
    public Response _searchOutputToExcel(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll,
            @RequestBody String body,
            @ApiParam(hidden = true)
            @RequestHeader(value = "host") String host,
            HttpServletRequest httpServletRequest
    ) throws IOException, IllegalAccessException, ScriptException, NoSuchMethodException {
        List<String> filesNames = new ArrayList<>();//文件名称
        List<List<String>> lists = new ArrayList<>();
        AtomicReference<Integer> i = new AtomicReference<>(0);
        if (StringUtils.isBlank(scroll)) {
            lists = searchServicePlus._searchToList(index, body, true, (size, total) -> log.info("读取进度:{}/{}->{}", size, total, percent(size, total)));
        } else {
            lists = searchServicePlus._searchScrollToList(index, scroll, body, true, (size, total) -> {
                log.info("读取进度:{}/{}->{}", size, total, percent(size, total));
            }, new Function<List<List<String>>, Boolean>() {
                @SneakyThrows
                @Override
                public Boolean apply(List<List<String>> lists) {
                    if (lists.size() >= LIMIT) {
                        String fileName = "AggTerms" + DateUtil.getNow() + ".xlsx";
                        filesNames.add(fileName);
                        File file = resourceService.addNewFile(fileName);
                        ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                        lists.clear();
                    }
                    return true;
                }
            });
        }
        if (lists.size() > 0) {
            String fileName = "AggTerms" + DateUtil.getNow() + ".xlsx";
            filesNames.add(fileName);
            File file = resourceService.addNewFile(fileName);
            ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
            lists.clear();
        }

        List<String> urls = new ArrayList<>();
        filesNames.forEach(path -> {
            String url = "http://" + host + resourceService.getContextPath() + "/ResourceController/downloadByFileName?fileName=" + path;
            urls.add(url);
        });
        return Response.Ok(urls);
    }

    @ApiOperation(value = "导出全部的查询结果")
    @PostMapping(value = "/{index}/_search/outputToDB")
    public Response _searchOutputToDB(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll,
            @RequestBody String body
    ) throws Exception {
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
        if (StringUtils.isBlank(scroll)) {
            lists = searchServicePlus._searchToList(index, body, false, (size, total) -> log.info("读取进度:{}/{}->{}", size, total, percent(size, total)));
        } else {
            lists = searchServicePlus._searchScrollToList(index, scroll, body, false, (size, total) -> {
                log.info("读取进度:{}/{}->{}", size, total, percent(size, total));
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
                    return true;
                }
            });
        }
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
        return Response.Ok(targetTable);
    }


    @ApiOperation(value = "函数式处理发送到kafka")
    @PostMapping(value = "/{index}/_search/outputToKafka")
    public Response _searchOutputToKafka(
            @ApiParam(name = "index", defaultValue = "tb_object_0088")
            @PathVariable(value = "index")
                    String index,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)", defaultValue = "1m")
            @RequestParam(value = "scroll", required = false)
                    String scroll,
            @ApiParam(name = "body", value = "真实的查询body", allowMultiple = true)
            @RequestBody String body,
            @ApiParam(name = "topic", value = "输出为topic -> 提供topic", defaultValue = "TP_01009406")
            @RequestParam(value = "topic")
                    String topic,
            @ApiParam(name = "policyId", value = "指定的策略", defaultValue = "ESETL2")
            @RequestParam(value = "policyId")
                    String policyId,
            @ApiParam(name = "toTable", value = "发送的table", defaultValue = "toTable")
            @RequestParam(value = "toTable")
                    KafkaMsg.ToTable toTable,
            @ApiParam(name = "script", value = "_source 处理的脚本", defaultValue = "dataMap[\"F23_0088\"]=\"18040100000000\";return dataMap;")
            @RequestParam(value = "script", required = false)
                    String script
    ) {

        if (StringUtils.isBlank(scroll)) {
            searchServicePlus._searchToConsumer(index, body, new Function<InnerHits, Boolean>() {
                @SneakyThrows
                @Override
                public Boolean apply(InnerHits innerHits) {
                    Boolean scriptDeal = null;
                    if (StringUtils.isNotBlank(script)) {
                        //执行脚本
                        scriptDeal = javaScriptExecuteScript.evalAndFilter(script, innerHits.getSource());
                    }
                    if (null == scriptDeal || true == scriptDeal) {
                        JSONObject json = JSONObject.parseObject(JSONObject.toJSON(innerHits.getSource()).toString());
                        kafkaOutService.load(topic, json, policyId, toTable.getTable());
                        log.info("发送成功:topic:{},policyId:{},:json{}", topic, policyId, json);
                    } else if (false == scriptDeal) {
                        log.info("返回false->不处理：topic:{},policyId:{},json:{}", topic, policyId, innerHits.getSource());
                    }
                    return true;
                }
            });

        } else {
            searchServicePlus._searchScrollToConsumer(index, scroll, body, new Function<InnerHits, Boolean>() {
                @SneakyThrows
                @Override
                public Boolean apply(InnerHits innerHits) {
                    Boolean scriptDeal = null;
                    if (StringUtils.isNotBlank(script)) {
                        //执行脚本
                        scriptDeal = javaScriptExecuteScript.evalAndFilter(script, innerHits.getSource());
                    }
                    if (null == scriptDeal || true == scriptDeal) {
                        JSONObject root0088 = JSONObject.parseObject(JSONObject.toJSON(innerHits.getSource()).toString());
                        kafkaOutService.load0088(topic, root0088, policyId);
                        log.info("发送成功:topic:{},policyId:{},json:{}", topic, policyId, root0088);
                    } else if (false == scriptDeal) {
                        log.info("返回false->不处理：topic:{},policyId:{},json:{}", topic, policyId, innerHits.getSource());
                    }
                    return true;
                }
            });
        }

        return Response.Ok(true);
    }

    /**
     * 提供主表的index,关联字段
     * 提供从表表的index,关联字段
     *
     * @param masterIndex
     * @param masterField
     * @param slaveIndex
     * @param slaveField
     * @param scroll
     * @param body
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    @ApiOperation(value = "查询主从的附属表")
    @PostMapping(value = "/_search/masterSlave/outputToExcel")
    public Response _searchMasterSlave(
            @ApiParam(name = "masterIndex", defaultValue = "tb_object_0088")
            @RequestParam(value = "masterIndex")
                    String masterIndex,
            @ApiParam(name = "masterField", defaultValue = "F1_0088")
            @RequestParam(value = "masterField")
                    String masterField,
            @ApiParam(name = "slaveIndex", defaultValue = "tb_object_6254")
            @RequestParam(value = "slaveIndex")
                    String slaveIndex,
            @ApiParam(name = "slaveField", defaultValue = "F1_6254")
            @RequestParam(value = "slaveField")
                    String slaveField,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false)
                    String scroll,
            @RequestBody List<Body> body,
            @ApiParam(name = "script", value = "_source 处理的脚本（这里是从脚本）", defaultValue = "dataMap[\"F23_0088\"] = \"11\";return dataMap;")
            @RequestParam(value = "script", required = false)
                    String script
    ) throws IOException, IllegalAccessException {
        final List<List<String>>[] result = new List[]{new ArrayList<>()};
        final Set<String>[] masterFieldValues = new Set[]{new HashSet<>()};
        if (StringUtils.isBlank(scroll)) {
            searchServicePlus._searchToConsumer(masterIndex, body.get(0).parse(), new Function<InnerHits, Boolean>() {
                @Override
                public Boolean apply(InnerHits innerHits) {
                    String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
                    masterFieldValues[0].add(filedValue);
                    return true;
                }
            }, total -> {
                /**
                 * 初始化结果集
                 */
                masterFieldValues[0] = new HashSet<>(total);//初始化数量
                if (total < 100100) {
                    /**
                     * 如何小于，直接初始化
                     */
                    result[0] = new ArrayList<>(total);
                } else {
                    result[0] = new ArrayList<>(100100);
                }
            });

        } else {
            searchServicePlus._searchScrollToConsumer(masterIndex, scroll, body.get(0).parse(), total -> {
                /**
                 * 初始化结果集
                 */
                masterFieldValues[0] = new HashSet<>(total);//初始化数量
                if (total < 100100) {
                    /**
                     * 如何小于，直接初始化
                     */
                    result[0] = new ArrayList<>(total);
                } else {
                    result[0] = new ArrayList<>(100100);
                }
            }, innerHits -> {
                String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
                masterFieldValues[0].add(filedValue);
                return true;
            });
        }

        List<String> fieldNamesList = mappingServicePlus.getFieldNamesList(slaveIndex);
        result[0].add(fieldNamesList);//存放字段名称
        List<String> dealValues = new ArrayList<>();
        int i = 0;
        int total = masterFieldValues[0].size();
        for (String value : masterFieldValues[0]) {
            log.info("i : {} /total :{} -> {}", i++, total, percent(i, total));
            dealValues.add(value);
            if (dealValues.size() >= 1000) {
                try {
                    Terms terms = new Terms();
                    terms.setField(slaveField);
                    terms.getValue().addAll(dealValues);
                    body.get(1).getQuery().getBool().getMust().setTerms(Arrays.asList(terms));
                    List<List<String>> tmp = searchServicePlus._searchScrollToList(slaveIndex, "1m", body.get(1).parse(), false, null, null, javaScriptExecuteScript, script);
                    result[0].addAll(tmp);
                    dealValues.clear();
                    /**
                     * 存储
                     */
                    log.info("result.size():{}", result[0].size());
                    if (result[0].size() > LIMIT) {
                        File file = new File("resul" + i + ".xlsx");
                        ExcelUtil.writeListSXSS(result[0], new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                        result[0].clear();
                    }
                } catch (Exception e) {
                    log.error("异常:{}", e.toString(), e);
                }
            }
        }

        List<List<String>> tmp = searchServicePlus._searchToListTerms(slaveIndex, "1m", slaveField, dealValues, javaScriptExecuteScript, script);
        result[0].addAll(tmp);


        File file = new File("resulEnd.xlsx");
        ExcelUtil.writeListSXSS(result[0], new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
        return Response.Ok(true);
    }

    /**
     * 提供主表的index,关联字段
     * 提供从表表的index,关联字段
     * <p>
     * s数据最终会今日kafka
     *
     * @param masterIndex
     * @param masterField
     * @param slaveIndex
     * @param slaveField
     * @param scroll
     * @param body
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    @ApiOperation(value = "查询主从的附属表")
    @PostMapping(value = "/_search/masterSlave/outputToKafka")
    public Response _searchMasterSlave(
            @ApiParam(name = "masterIndex", defaultValue = "tb_object_0088")
            @RequestParam(value = "masterIndex")
                    String masterIndex,
            @ApiParam(name = "masterField", defaultValue = "F1_0088")
            @RequestParam(value = "masterField")
                    String masterField,
            @ApiParam(name = "slaveIndex", defaultValue = "tb_object_6254")
            @RequestParam(value = "slaveIndex")
                    String slaveIndex,
            @ApiParam(name = "slaveField", defaultValue = "F1_6254")
            @RequestParam(value = "slaveField")
                    String slaveField,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false)
                    String scroll,
            @RequestBody String body,
            @ApiParam(name = "topic", value = "输出为topic -> 提供topic", defaultValue = "TP_01009406")
            @RequestParam(value = "topic")
                    String topic,
            @ApiParam(name = "policyId", value = "指定的策略", defaultValue = "TB6254ETL")
            @RequestParam(value = "policyId")
                    String policyId,
            @ApiParam(name = "toTable", value = "发送的table", defaultValue = "toTable")
            @RequestParam(value = "toTable")
                    KafkaMsg.ToTable toTable,
            @ApiParam(name = "script", value = "_source 处理的脚本（这里是的脚本返回 false 代表无效;如果要结束，return dataMap 即可）", defaultValue = "dataMap[\"F23_0088\"] = \"11\"")
            @RequestParam(value = "script", required = false)
                    String script
    ) throws IOException, IllegalAccessException {
        /**
         * 第一步:获取主表的指定字段
         */
        Set<String> masterFieldValues = new HashSet<>(1600000);
        if (StringUtils.isBlank(scroll)) {
            searchServicePlus._searchToConsumer(masterIndex, body, new Function<InnerHits, Boolean>() {
                @Override
                public Boolean apply(InnerHits innerHits) {
                    String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
                    masterFieldValues.add(filedValue);
                    return true;
                }
            });

        } else {
            searchServicePlus._searchScrollToConsumer(masterIndex, scroll, body, new Function<InnerHits, Boolean>() {
                @Override
                public Boolean apply(InnerHits innerHits) {
                    String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
                    masterFieldValues.add(filedValue);
                    return true;
                }

            });
        }

        /**
         * 根据指定的字段集合从表查询
         */


        List<String> dealValues = new ArrayList<>();
        int i = 0;
        int total = masterFieldValues.size();
        for (String value : masterFieldValues) {
            log.info("i : {} /total :{} -> {}", i++, total, percent(i, total));
            dealValues.add(value);
            if (dealValues.size() >= 1000) {
                try {
                    /**
                     * 处理1000条
                     */
                    searchServicePlus._searchScrollToListTerms(slaveIndex, scroll, slaveField, dealValues, new Function<InnerHits, Boolean>() {
                        @SneakyThrows
                        @Override
                        public Boolean apply(InnerHits innerHits) {
                            JSONObject json = javaScriptExecuteScript.eval(script, innerHits.getSource());
                            kafkaOutService.load(topic, json, policyId, toTable.getTable());
                            log.info("发送成功:topic:{},policyId:{},root0088:{}", topic, policyId, json);
                            return true;
                        }
                    });
                    dealValues.clear();
                } catch (Exception e) {
                    log.error("异常:{}", e.toString(), e);
                }
            }
        }
        /**
         * 处理剩余数据
         */
        searchServicePlus._searchScrollToListTerms(slaveIndex, scroll, slaveField, dealValues, new Function<InnerHits, Boolean>() {
            @SneakyThrows
            @Override
            public Boolean apply(InnerHits innerHits) {
                JSONObject json = javaScriptExecuteScript.eval(script, innerHits.getSource());
                kafkaOutService.load(topic, json, policyId, toTable.getTable());
                log.info("发送成功:topic:{},policyId:{},root0088:{}", topic, policyId, json);
                return true;
            }
        });

        return Response.Ok(true);
    }


    /**
     * 提供主表的index,关联字段
     * 提供从表表的index,关联字段
     *
     * @param scroll
     * @param body
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    @ApiOperation(value = "查询两个index相关字段的差集")
    @PostMapping(value = "/_search/_searchDiff/outputToExcel")
    public Response _searchDiff(
            @ApiParam(name = "indexOne", defaultValue = "tb_object_0088")
            @RequestParam(value = "indexOne")
                    String indexOne,
            @ApiParam(name = "indexOneField", defaultValue = "F1_0088")
            @RequestParam(value = "indexOneField")
                    String indexOneField,
            @RequestParam(value = "indexTwo")
                    String indexTwo,
            @RequestParam(value = "indexTwoField")
                    String indexTwoField,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false)
                    String scroll,
            @ApiParam(name = "initialCapacity", value = "结果集的初始化大小", defaultValue = "1000")
            @RequestParam(value = "initialCapacity", required = false)
                    int initialCapacity,
            @RequestBody String body) throws IOException, IllegalAccessException {
//        List<List<String>> result = new ArrayList<>(initialCapacity);
//        Set<String> masterFieldValues = new HashSet<>(1600000);
//        if (StringUtils.isBlank(scroll)) {
//            searchServicePlus._search(masterIndex, body, new Consumer<InnerHits>() {
//                @Override
//                public void accept(InnerHits innerHits) {
//                    String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
//                    masterFieldValues.add(filedValue);
//                }
//            });
//
//        } else {
//            searchServicePlus._search(masterIndex, scroll, body, new Consumer<InnerHits>() {
//                @Override
//                public void accept(InnerHits innerHits) {
//                    String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
//                    masterFieldValues.add(filedValue);
//                }
//            });
//        }
//
//        List<String> fieldNamesList = mappingServicePlus.getFieldNamesList(slaveIndex);
//        result.add(fieldNamesList);//存放字段名称
//        List<String> dealValues = new ArrayList<>();
//        int i = 0;
//        int total = masterFieldValues.size();
//        for (String value : masterFieldValues) {
//            log.info("i : {} /total :{} -> {}", i++, total, ExcelUtil.percent(i, total));
//            if (dealValues.size() < 1000) {
//                dealValues.add(value);
//            } else {
//                try {
//                    List<List<String>> tmp = searchServicePlus._search(slaveIndex, scroll, slaveField, dealValues);
//                    result.addAll(tmp);
//                    dealValues.clear();
//                    /**
//                     * 存储
//                     */
//                    log.info("result.size():{}", result.size());
//                    if (result.size() > LIMIT) {
//                        File file = new File("resul" + i + ".xlsx");
//                        ExcelUtil.writeListSXSS(result, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, ExcelUtil.percent(line, size)));
//                        result.clear();
//                    }
//                } catch (Exception e) {
//                    log.error("异常:{}", e.toString(), e);
//                }
//            }
//        }
//
//        List<List<String>> tmp = searchServicePlus._search(slaveIndex, scroll, slaveField, dealValues);
//        result.addAll(tmp);
//
//
//        File file = new File("resulEnd.xlsx");
//        ExcelUtil.writeListSXSS(result, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, ExcelUtil.percent(line, size)));
        return Response.Ok(true);
    }


    @ApiOperation(value = "查询agg到excel，目前只支持aggTerms", produces = "application/octet-stream", notes = "```" +
            "\n"
            + "{\n" +
            "  \"size\": 10,\n" +
            "  \"query\": {\n" +
            "    \"bool\": {\n" +
            "      \"filter\": [],\n" +
            "      \"must_not\": [],\n" +
            "      \"should\": [],\n" +
            "      \"must\": [\n" +
            "        {\n" +
            "          \"terms\": {\n" +
            "          \"F26_0088\": [\n" +
            "          \"1223792949\",\n" +
            "          \"1359299210\"\n" +
            "          ],\n" +
            "          \"boost\": \"1.0\"\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  },\n" +
            "  \"from\": 0,\n" +
            "  \"aggs\": {\n" +
            "    \"F6_0088\": {\n" +
            "      \"terms\": {\n" +
            "        \"field\": \"F6_0088\",\n" +
            "        \"size\": 100000,\n" +
            "        \"show_term_doc_count_error\": false,\n" +
            "        \"min_doc_count\": 1\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}" +
            "```")
    @PostMapping(value = "/{index}/_searchOneAggToExcel")
    public Object _search(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @RequestBody String body) {
        try {
            String result = searchService._search(index, body);
            ESResponse esResponse = ESResponse.parse(result);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            for (Map.Entry<String, BucketsRoot> entry : esResponse.getAggregations().entrySet()) {
                String key = entry.getKey();
                BucketsRoot buckets = entry.getValue();
                log.info("bucket的size:{},剩余未未统计的size:{},错误统计size:{}", buckets.getBuckets().size(), buckets.getSum_other_doc_count(), buckets.getDoc_count_error_upper_bound());
                ExcelUtil.writeVosSXSS(buckets.getBuckets(), outputStream, true, key);
            }
            HttpHeaders headers = new HttpHeaders();//设置响应
            headers.add("Content-Disposition", "attachment;filename=" + "Agg" + DateUtil.getNow() + ".xlsx");//下载的文件名称
            HttpStatus statusCode = HttpStatus.OK;//设置响应吗
            ResponseEntity<byte[]> response = new ResponseEntity<>(outputStream.toByteArray(), headers, statusCode);
            log.info("正常返回");
            return response;
        } catch (Exception e) {
            Response response = new Response<>();
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            log.error("发生异常:{}", e.getMessage(), e);
            return response;
        }
    }

    @ApiOperation(value = "查询agg到excel，目前只支持aggTerms(values的优先级高于listFile)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listFile", value = "listFile", dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "field", value = "field", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "values", value = "values", dataTypeClass = String.class, paramType = "form", allowMultiple = true)
    })
    @RequestMapping(value = "/{index}/_searchTermsToExcel", method = {RequestMethod.POST})
    public Object _searchTermsToExcel(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @ApiParam(defaultValue = "F6_0088", value = "下面的文件中需要匹配的值")
            @RequestParam(value = "field", required = false)
                    String field,
            @ApiParam(value = "请求体，需要复制")
            @RequestParam(value = "request") List<String> request,
            @RequestParam(value = "values", required = false) List<String> values,
            @RequestParam(name = "listFile", required = false) MultipartFile listFile,
            @RequestParam(name = "outType", required = false, defaultValue = "URL") OutType outType,
            @ApiParam(hidden = true)
            @RequestHeader(value = "host") String host,
            HttpServletRequest httpServletRequest

    ) {
        try {
            /**
             * 获取 title
             */
            List<String> names = mappingServicePlus.getFieldNamesList(index);//获取name

            List<String> filesNames = new ArrayList<>();//存放地址
            Body body = null;//请求体
            List<String> dealValues = new ArrayList<>();//待处理的值
            List<String> dealValuesSource;//待处理的值
            /**
             * 还原请求体
             */
            StringBuffer bodyStr = new StringBuffer();
            request.forEach(line -> {
                bodyStr.append(line.trim());
            });

            if (null != values) {
                dealValuesSource = values;
            } else {
                dealValuesSource = IOUtils.readLines(listFile.getInputStream());
            }
            /**
             * 移除空格
             */
            dealValuesSource.forEach(value -> {
                dealValues.add(value.trim());
            });


            List<List<String>> readToExcelListTmp = new ArrayList<>(1000000);//入excel的结果集
            readToExcelListTmp.add(names);//添加Title
            int i = 0;
            int total = dealValues.size();
            List<Object> deal = new ArrayList<>();
            for (String value : dealValues) {
                log.info("处理进度:{}/{}->{}", i++, total, percent(i, total));
                if (StringUtils.isNotBlank(value)) {
                    //加入空判断
                    deal.add(value);
                }
                if (deal.size() >= 1000) {
                    body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
                    body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
                    List<List<String>> lists = searchServicePlus._searchScrollToList(index, "1m", body.parse(), false);
                    readToExcelListTmp.addAll(lists);

                    /**
                     * 分段写入
                     */
                    while (readToExcelListTmp.size() > LIMIT) {
                        List<List<String>> middle = readToExcelListTmp.subList(0, LIMIT);
                        String fileName = "AggTerms" + DateUtil.getNow() + ".xlsx";
                        File file = resourceService.addNewFile(fileName);
                        filesNames.add(fileName);
                        ExcelUtil.writeListSXSS(middle, new FileOutputStream(file), (line, sz) -> log.info("写入进度:{}/{}->{}", line, sz, percent(line, sz)));
                        middle.clear();
                    }
                    deal.clear();
                }
            }
            if (deal.size() > 0) {
                body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
                body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
                List<List<String>> lists = searchServicePlus._searchScrollToList(index, "1m", body.parse(), false);
                readToExcelListTmp.addAll(lists);
            }

            /**
             * 分段写入
             */
            while (readToExcelListTmp.size() > LIMIT) {
                List<List<String>> middle = readToExcelListTmp.subList(0, LIMIT);
                String fileName = "AggTerms" + DateUtil.getNow() + ".xlsx";
                File file = resourceService.addNewFile(fileName);
                filesNames.add(fileName);
                ExcelUtil.writeListSXSS(middle, new FileOutputStream(file), (line, sz) -> log.info("写入进度:{}/{}->{}", line, sz, percent(line, sz)));
                middle.clear();
            }
            /**
             * 最后写入
             */
            if (readToExcelListTmp.size() > 0) {
                String fileName = "AggTerms" + DateUtil.getNow() + ".xlsx";
                File file = resourceService.addNewFile(fileName);
                filesNames.add(fileName);
                FileOutputStream fileOutputStream = FileUtils.openOutputStream(file);
                ExcelUtil.writeListSXSS(readToExcelListTmp, fileOutputStream, (line, sz) -> log.info("写入进度:{}/{}->{}", line, sz, percent(line, sz)));
            }
            List<String> urls = new ArrayList<>();
            filesNames.forEach(path -> {
                String url = "http://" + host + resourceService.getContextPath() + "/ResourceController/downloadByFileName?fileName=" + path;
                urls.add(url);
            });
            log.info("提取完成,return");
            return Response.Ok(urls);
        } catch (Exception e) {
            Response response = new Response<>();
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            log.error("发生异常:{}", e.getMessage(), e);
            return response;
        }
    }

    @ApiOperation(value = "查询agg到DB，目前只支持aggTerms(values的优先级高于listFile)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listFile", value = "listFile", dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "field", value = "field", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "values", value = "values", dataTypeClass = String.class, paramType = "form", allowMultiple = true)
    })
    @RequestMapping(value = "/{index}/_searchTermsToDb", method = {RequestMethod.POST})
    public Object _searchTermsToDb(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @ApiParam(defaultValue = "F6_0088", value = "下面的文件中需要匹配的值")
            @RequestParam(value = "field", required = false)
                    String field,
            @ApiParam(value = "请求体，需要复制")
            @RequestParam(value = "request") List<String> request,
            @RequestParam(value = "values", required = false) List<String> values,
            @RequestParam(name = "listFile", required = false) MultipartFile listFile,
            @RequestParam(name = "outType", required = false, defaultValue = "URL") OutType outType

    ) throws Exception {

        String targetTable = index + "_" + DateUtil.getNow();
        dbService.cloneTableStruct(index, targetTable);//创建新的表
        /**
         * 获取 title
         */
        List<String> fieldNames = mappingServicePlus.getFieldNamesList(index);//获取name

        Body body = null;//请求体
        List<String> dealValues = new ArrayList<>();//待处理的值
        List<String> dealValuesSource;//待处理的值
        /**
         * 还原请求体
         */
        StringBuffer bodyStr = new StringBuffer();
        request.forEach(line -> {
            bodyStr.append(line.trim());
        });

        if (null != values) {
            dealValuesSource = values;
        } else {
            dealValuesSource = IOUtils.readLines(listFile.getInputStream());
        }
        /**
         * 移除空格
         */
        dealValuesSource.forEach(value -> {
            dealValues.add(value.trim());
        });


        List<List<String>> readToExcelListTmp = new ArrayList<>(1000000);//入excel的结果集
        int i = 0;
        int total = dealValues.size();
        List<Object> deal = new ArrayList<>();
        for (String value : dealValues) {
            log.info("处理进度:{}/{}->{}", i++, total, percent(i, total));
            if (StringUtils.isNotBlank(value)) {
                //加入空判断
                deal.add(value);
            }
            if (deal.size() >= 1000) {
                body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
                body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
                List<List<String>> lists = searchServicePlus._searchScrollToList(index, "1m", body.parse(), false);
                readToExcelListTmp.addAll(lists);

                /**
                 * 分段写入
                 */
                while (readToExcelListTmp.size() > LIMIT_DB) {
                    List<List<String>> middle = readToExcelListTmp.subList(0, LIMIT_DB);
                    List<List<String>> tmp = new ArrayList<>(middle);
                    threadPoolExecutorService.addWork(new Runnable() {
                        @Override
                        public void run() {
                            dbService.batchInsert(targetTable, tmp, fieldNames);
                        }
                    });
                    middle.clear();
                }
                deal.clear();
            }
        }
        if (deal.size() > 0) {
            body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
            body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
            List<List<String>> lists = searchServicePlus._searchScrollToList(index, "1m", body.parse(), false);
            readToExcelListTmp.addAll(lists);
        }

        /**
         * 分段写入
         */
        while (readToExcelListTmp.size() > LIMIT_DB) {
            List<List<String>> middle = readToExcelListTmp.subList(0, LIMIT_DB);
            List<List<String>> tmp = new ArrayList<>(middle);
            threadPoolExecutorService.addWork(new Runnable() {
                @Override
                public void run() {
                    dbService.batchInsert(targetTable, tmp, fieldNames);
                }
            });
            middle.clear();
        }
        /**
         * 最后写入
         */
        if (readToExcelListTmp.size() > 0) {
            List<List<String>> tmp = new ArrayList<>(readToExcelListTmp);
            threadPoolExecutorService.addWork(new Runnable() {
                @Override
                public void run() {
                    dbService.batchInsert(targetTable, tmp, fieldNames);
                }
            });
        }

        threadPoolExecutorService.waitComplete();
        log.info("提取完成,return");
        return Response.Ok(targetTable);

    }


    @ApiOperation(value = "查询agg到kafka，目前只支持aggTerms(values的优先级高于listFile)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listFile", value = "listFile", dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "field", value = "field", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "values", value = "values", dataTypeClass = String.class, paramType = "form", allowMultiple = true)
    })
    @RequestMapping(value = "/{index}/_searchTermsToKafka", method = {RequestMethod.POST})
    public Response _searchTermsToKafka(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @ApiParam(defaultValue = "F6_0088", value = "下面的文件中需要匹配的值")
            @RequestParam(value = "field", required = false)
                    String field,
            @ApiParam(value = "请求体，需要复制")
            @RequestParam(value = "request") List<String> request,
            @RequestParam(value = "values", required = false) List<String> values,
            @RequestParam(name = "listFile", required = false) MultipartFile listFile,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll,
            @ApiParam(name = "topic", value = "输出为topic -> 提供topic", defaultValue = "TP_01009406")
            @RequestParam(value = "topic")
                    String topic,
            @ApiParam(name = "policyId", value = "指定的策略", defaultValue = "ESETL2")
            @RequestParam(value = "policyId")
                    String policyId,
            @ApiParam(name = "toTable", value = "发送的table", defaultValue = "toTable")
            @RequestParam(value = "toTable")
                    KafkaMsg.ToTable toTable,
            @ApiParam(name = "script", value = "_source 处理的脚本", defaultValue = "delete dataMap[\"RP_GEN_DATETIME2\"];delete dataMap[\"SEQID\"];dataMap[\"F23_0088\"]=\"18040100000000\";return dataMap;")
            @RequestParam(value = "script", required = false)
                    String script

    ) throws ScriptException, NoSuchMethodException, IOException {

        Body body = null;//请求体
        List<String> dealValues;//待处理的值
        /**
         * 还原请求体
         */
        StringBuffer bodyStr = new StringBuffer();
        request.forEach(line -> {
            bodyStr.append(line.trim());
        });
        /**
         * 确定处理的terms
         */
        if (null != values) {
            dealValues = values;
        } else {
            dealValues = IOUtils.readLines(listFile.getInputStream());
        }

        int i = 0;
        int total = dealValues.size();
        /**
         * 定义消费者 -> 后面经常使用
         */
        Function<InnerHits, Boolean> function = new Function<InnerHits, Boolean>() {
            @SneakyThrows
            @Override
            public Boolean apply(InnerHits innerHits) {
                Boolean scriptDeal = null;
                if (StringUtils.isNotBlank(script)) {
                    //执行脚本
                    scriptDeal = javaScriptExecuteScript.evalAndFilter(script, innerHits.getSource());
                }
                if (null == scriptDeal || true == scriptDeal) {
                    JSONObject json = JSONObject.parseObject(JSONObject.toJSON(innerHits.getSource()).toString());
                    kafkaOutService.load(topic, json, policyId, toTable.getTable());
                    log.info("发送成功:topic:{},policyId:{},:json{}", topic, policyId, json);
                } else if (false == scriptDeal) {
                    log.info("返回false->不处理：topic:{},policyId:{},json:{}", topic, policyId, innerHits.getSource());
                }
                return true;

            }
        };
        List<Object> deal = new ArrayList<>();
        for (String value : dealValues) {
            log.info("处理进度:{}/{}->{}", i++, total, percent(i, total));
            if (StringUtils.isNotBlank(value)) {
                //加入空判断
                deal.add(value);
            }
            if (deal.size() >= 1000) {
                body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
                body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
                if (StringUtils.isBlank(scroll)) {
                    searchServicePlus._searchToConsumer(index, body.parse(), function);
                } else {
                    searchServicePlus._searchScrollToConsumer(index, scroll, body.parse(), function);
                }
                deal.clear();
            }

        }
        //处理最后一份
        body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
        body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
        if (StringUtils.isBlank(scroll)) {
            searchServicePlus._searchToConsumer(index, body.parse(), function);
        } else {
            searchServicePlus._searchScrollToConsumer(index, scroll, body.parse(), function);
        }
        log.info("执行完成");
        return Response.Ok(true);
    }

}
















