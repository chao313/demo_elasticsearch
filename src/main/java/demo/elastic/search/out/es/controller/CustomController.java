package demo.elastic.search.out.es.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import demo.elastic.search.out.etl.service.PEVCService;
import demo.elastic.search.out.kafka.KafkaMsg;
import demo.elastic.search.out.kafka.KafkaOutService;
import demo.elastic.search.out.resource.service.ResourceService;
import demo.elastic.search.po.Body;
import demo.elastic.search.po.response.ESResponse;
import demo.elastic.search.po.response.InnerHits;
import demo.elastic.search.po.response.buckets.BucketsRoot;
import demo.elastic.search.po.term.level.base.Terms;
import demo.elastic.search.util.DateUtil;
import demo.elastic.search.util.ExcelUtil;
import demo.elastic.search.util.JSONUtil;
import demo.elastic.search.vo.SearchTermsRequest;
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
import rx.functions.Action2;

import javax.annotation.Resource;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.stream.Collectors;

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

    private static final Integer LIMIT = 500000;


    @ApiOperation(value = "accounts.json 数据批量插入")
    @PostMapping(value = "/{index}/_bulk")
    public Response _bulk_accounts(
            @PathVariable(value = "index") String index) throws IOException {
        File file = AwareUtil.resourceLoader.getResource("classpath:data/accounts.json").getFile();
        String body = FileUtils.readFileToString(file, "UTF-8");
        String s = documentService._bulk(index, body);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "导出全部的查询结果")
    @PostMapping(value = "/{index}/_search/outputToExcel")
    public Response _search(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll,
            @RequestBody String body) throws IOException, IllegalAccessException, ScriptException, NoSuchMethodException {
        List<List<String>> lists = new ArrayList<>();
        AtomicReference<Integer> i = new AtomicReference<>(0);
        if (StringUtils.isBlank(scroll)) {
            lists = searchServicePlus._searchToList(index, body, true, (size, total) -> log.info("读取进度:{}/{}->{}", size, total, percent(size, total)));
        } else {
            lists = searchServicePlus._searchScrollToList(index, scroll, body, true, (size, total) -> {
                log.info("读取进度:{}/{}->{}", size, total, percent(size, total));
            }, new Consumer<List<List<String>>>() {
                @SneakyThrows
                @Override
                public void accept(List<List<String>> lists) {
                    if (lists.size() >= LIMIT) {
                        File file = new File("result" + i.getAndSet(i.get() + 1) + ".xlsx");
                        ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                        lists.clear();
                    }
                }
            });
        }
        File file = new File("result" + i.getAndSet(i.get() + 1) + ".xlsx");
        ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
        lists.clear();
        return Response.Ok(true);
    }


    @ApiOperation(value = "函数式处理发送到kafka")
    @PostMapping(value = "/{index}/_search/outputToKafka")
    public Response _search(
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
            @ApiParam(name = "policyId", value = "指定wind的策略", defaultValue = "ESETL2")
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
            searchServicePlus._searchToConsumer(index, body, new Consumer<InnerHits>() {
                @SneakyThrows
                @Override
                public void accept(InnerHits innerHits) {
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

                }
            });

        } else {
            searchServicePlus._searchScrollToConsumer(index, scroll, body, new Consumer<InnerHits>() {
                @SneakyThrows
                @Override
                public void accept(InnerHits innerHits) {
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
            searchServicePlus._searchToConsumer(masterIndex, body.get(0).parse(), new Consumer<InnerHits>() {
                @Override
                public void accept(InnerHits innerHits) {
                    String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
                    masterFieldValues[0].add(filedValue);
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
            });
        }

        List<String> fieldNamesList = mappingServicePlus.getFieldNamesList(slaveIndex);
        result[0].add(fieldNamesList);//存放字段名称
        List<String> dealValues = new ArrayList<>();
        int i = 0;
        int total = masterFieldValues[0].size();
        for (String value : masterFieldValues[0]) {
            log.info("i : {} /total :{} -> {}", i++, total, percent(i, total));
            if (dealValues.size() < 1000) {
                dealValues.add(value);
            } else {
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
            @ApiParam(name = "policyId", value = "指定wind的策略", defaultValue = "TB6254ETL")
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
            searchServicePlus._searchToConsumer(masterIndex, body, new Consumer<InnerHits>() {
                @Override
                public void accept(InnerHits innerHits) {
                    String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
                    masterFieldValues.add(filedValue);
                }
            });

        } else {
            searchServicePlus._searchScrollToConsumer(masterIndex, scroll, body, new Consumer<InnerHits>() {
                @Override
                public void accept(InnerHits innerHits) {
                    String filedValue = innerHits.getSource().get(masterField) == null ? "" : innerHits.getSource().get(masterField).toString();
                    masterFieldValues.add(filedValue);
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
            if (dealValues.size() < 1000) {
                dealValues.add(value);
            } else {
                try {
                    /**
                     * 处理1000条
                     */
                    searchServicePlus._searchScrollToListTerms(slaveIndex, scroll, slaveField, dealValues, new Consumer<InnerHits>() {
                        @SneakyThrows
                        @Override
                        public void accept(InnerHits innerHits) {
                            JSONObject json = javaScriptExecuteScript.eval(script, innerHits.getSource());
                            kafkaOutService.load(topic, json, policyId, toTable.getTable());
                            log.info("发送成功:topic:{},policyId:{},root0088:{}", topic, policyId, json);
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
        searchServicePlus._searchScrollToListTerms(slaveIndex, scroll, slaveField, dealValues, new Consumer<InnerHits>() {
            @SneakyThrows
            @Override
            public void accept(InnerHits innerHits) {
                JSONObject json = javaScriptExecuteScript.eval(script, innerHits.getSource());
                kafkaOutService.load(topic, json, policyId, toTable.getTable());
                log.info("发送成功:topic:{},policyId:{},root0088:{}", topic, policyId, json);
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


    @ApiOperation(value = "查询agg到excel，目前只支持aggTerms", produces = "application/octet-stream", notes =
            "comstore_tb_object_0088<br>" +
                    "{<br>" +
                    "&nbsp;&nbsp;\"size\": 10,<br>" +
                    "&nbsp;&nbsp;\"query\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;\"bool\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"filter\": [],<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"must_not\": [],<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"should\": [],<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"must\": [<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; \"terms\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   \"F26_0088\": [<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     \"1223792949\",<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;     \"1359299210\"<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   ],<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   \"boost\": \"1.0\"<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; }<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;]<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
                    "&nbsp;&nbsp;},<br>" +
                    "&nbsp;&nbsp;\"from\": 0,<br>" +
                    "&nbsp;&nbsp;\"aggs\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;\"F6_0088\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"terms\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"field\": \"F6_0088\",<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"size\": 100000,<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"show_term_doc_count_error\": false,<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"min_doc_count\": 1<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
                    "&nbsp;&nbsp;}<br>" +
                    "}")
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
    public Object _search(
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
            List<List<String>> readyToExcelList = new ArrayList<>(1000000);//入excel的结果集
            int i = 0;
            int total = dealValues.size();
            List<Object> deal = new ArrayList<>();
            for (String value : dealValues) {
                log.info("处理进度:{}/{}->{}", i++, total, percent(i, total));
                if (deal.size() < 1000) {
                    if (StringUtils.isNotBlank(value)) {
                        //加入空判断
                        deal.add(value);
                    }
                } else {
                    body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
                    body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
//                    List<List<String>> lists = searchServicePlus._searchScrollToList(index, "1m", body.parse(), false);
                    List<List<String>> lists = searchServicePlus._searchToList(index, body.parse(), false);
                    readToExcelListTmp.addAll(lists);
                    deal.clear();
                }
            }
            /**
             * 追加title
             */
            body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
            body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
            List<List<String>> lists = searchServicePlus._searchScrollToList(index, "1m", body.parse(), true);
            readyToExcelList.addAll(lists);//先添加带header的
            readyToExcelList.addAll(readToExcelListTmp);
            log.info("查询完成，开始写入");

            String fileName = "AggTerms" + DateUtil.getNow() + ".xlsx";
            switch (outType) {
                case URL:
                    File file = resourceService.addNewFile(fileName);
                    FileOutputStream fileOutputStream = FileUtils.openOutputStream(file);
                    ExcelUtil.writeListSXSS(readyToExcelList, fileOutputStream, (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                    log.info("写入完成,准备return");
                    String url = "http://" + host + resourceService.getContextPath() + "ResourceController/downloadByFileName?fileName=" + fileName;
                    return Response.Ok(url);
                case EXCEL:
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ExcelUtil.writeListSXSS(readyToExcelList, outputStream, (line, size) -> log.info("写入进度:{}/{}->{}", line, size, percent(line, size)));
                    log.info("写入完成,准备return");
                    //返回
                    HttpHeaders headers = new HttpHeaders();//设置响应
                    headers.add("Content-Disposition", "attachment;filename=" + fileName);//下载的文件名称
                    HttpStatus statusCode = HttpStatus.OK;//设置响应吗
                    ResponseEntity<byte[]> response = new ResponseEntity<>(outputStream.toByteArray(), headers, statusCode);
                    log.info("正常返回");
                    return response;
            }
        } catch (Exception e) {
            Response response = new Response<>();
            response.setCode(Code.System.FAIL);
            response.setMsg(e.toString());
            response.addException(e);
            log.error("发生异常:{}", e.getMessage(), e);
            return response;
        }
        return Response.fail("操作异常");
    }


    @ApiOperation(value = "查询agg到kafka，目前只支持aggTerms(values的优先级高于listFile)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "listFile", value = "listFile", dataType = "__file", paramType = "form"),
            @ApiImplicitParam(name = "field", value = "field", dataType = "string", paramType = "form"),
            @ApiImplicitParam(name = "values", value = "values", dataTypeClass = String.class, paramType = "form", allowMultiple = true)
    })
    @RequestMapping(value = "/{index}/_searchTermsToKafka", method = {RequestMethod.POST})
    public Response _search(
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
            @ApiParam(name = "policyId", value = "指定wind的策略", defaultValue = "ESETL2")
            @RequestParam(value = "policyId")
                    String policyId,
            @ApiParam(name = "toTable", value = "发送的table", defaultValue = "toTable")
            @RequestParam(value = "toTable")
                    KafkaMsg.ToTable toTable,
            @ApiParam(name = "script", value = "_source 处理的脚本", defaultValue = "dataMap[\"F23_0088\"]=\"18040100000000\";return dataMap;")
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
        Consumer<InnerHits> consumer = new Consumer<InnerHits>() {
            @SneakyThrows
            @Override
            public void accept(InnerHits innerHits) {
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

            }
        };
        List<Object> deal = new ArrayList<>();
        for (String value : dealValues) {
            log.info("处理进度:{}/{}->{}", i++, total, percent(i, total));
            if (deal.size() < 1000) {
                if (StringUtils.isNotBlank(value)) {
                    //加入空判断
                    deal.add(value);
                }
            } else {
                body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
                body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
                if (StringUtils.isBlank(scroll)) {
                    searchServicePlus._searchToConsumer(index, body.parse(), consumer);
                } else {
                    searchServicePlus._searchScrollToConsumer(index, scroll, body.parse(), consumer);
                }
                deal.clear();
            }

        }
        //处理最后一份
        body = JSONObject.parseObject(bodyStr.toString(), Body.class);//需要重新生成 -> 不然会持续叠加
        body.getQuery().getBool().getMust().getTerms().add((new Terms(field, deal)));
        if (StringUtils.isBlank(scroll)) {
            searchServicePlus._searchToConsumer(index, body.parse(), consumer);
        } else {
            searchServicePlus._searchScrollToConsumer(index, scroll, body.parse(), consumer);
        }
        return Response.Ok(true);
    }

}
















