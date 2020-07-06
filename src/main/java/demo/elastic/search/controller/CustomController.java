package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.engine.script.ExecuteScript;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.feign.MappingService;
import demo.elastic.search.feign.ScrollService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.feign.plus.MappingServicePlus;
import demo.elastic.search.feign.plus.ScrollServicePlus;
import demo.elastic.search.feign.plus.SearchServicePlus;
import demo.elastic.search.framework.Response;
import demo.elastic.search.out.kafka.KafkaOutService;
import demo.elastic.search.po.response.InnerHits;
import demo.elastic.search.util.ExcelUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rx.functions.Func2;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


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
            @PathVariable(value = "index") String index,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll,
            @RequestBody String body) throws IOException, IllegalAccessException {
        List<List<String>> lists = new ArrayList<>();
        if (StringUtils.isBlank(scroll)) {
            lists = searchServicePlus._search(index, body, 10000, new Func2<Integer, Integer, Void>() {
                @Override
                public Void call(Integer size, Integer total) {
                    log.info("读取进度:{}/{}->{}", size, total, ExcelUtil.percent(size, total));
                    return null;
                }
            });

        } else {
            lists = searchServicePlus._search(index, scroll, body, 10000, new Func2<Integer, Integer, Void>() {
                @Override
                public Void call(Integer size, Integer total) {
                    log.info("读取进度:{}/{}->{}", size, total, ExcelUtil.percent(size, total));
                    return null;
                }
            });
        }

        File file = new File("result.xlsx");
        ExcelUtil.writeListSXSS(lists, new FileOutputStream(file), new Func2<Integer, Integer, Void>() {
            @Override
            public Void call(Integer line, Integer size) {
                log.info("写入进度:{}/{}->{}", line, size, ExcelUtil.percent(line, size));
                return null;
            }
        });
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
            @PathVariable(value = "topic")
                    String topic,
            @ApiParam(name = "policyId", value = "指定wind的策略", defaultValue = "ESETL2")
            @RequestParam(value = "policyId")
                    String policyId,
            @ApiParam(name = "script", value = "_source 处理的脚本", defaultValue = "dataMap[\"F23_0088\"] = \"11\"")
            @RequestParam(value = "script", required = false)
                    String script
    ) {

        if (StringUtils.isBlank(scroll)) {
            searchServicePlus._search(index, body, new Consumer<InnerHits>() {
                @SneakyThrows
                @Override
                public void accept(InnerHits innerHits) {
                    ExecuteScript.eval(script, innerHits.getSource());
                    JSONObject root0088 = JSONObject.parseObject(JSONObject.toJSON(innerHits.getSource()).toString());
                    kafkaOutService.load0088(topic, root0088, policyId);
                    log.info("发送成功:topic:{},policyId:{},root0088:{}", topic, policyId, root0088);
                }
            });

        } else {
            searchServicePlus._search(index, scroll, body, new Consumer<InnerHits>() {
                @SneakyThrows
                @Override
                public void accept(InnerHits innerHits) {
                    ExecuteScript.eval(script, innerHits.getSource());
                    JSONObject root0088 = JSONObject.parseObject(JSONObject.toJSON(innerHits.getSource()).toString());
                    kafkaOutService.load0088(topic, root0088, policyId);
                    log.info("发送成功:topic:{},policyId:{},root0088:{}", topic, policyId, root0088);
                }
            });
        }

        return Response.Ok(true);
    }
}
















