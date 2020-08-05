package demo.elastic.search.out.hbase;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.engine.script.impl.JavaScriptExecuteScript;
import demo.elastic.search.feign.plus.SearchServicePlus;
import demo.elastic.search.framework.Response;
import demo.elastic.search.out.kafka.KafkaMsg;
import demo.elastic.search.out.kafka.KafkaOutService;
import demo.elastic.search.po.response.InnerHits;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.function.Consumer;

/**
 * 以Hbase为主的操作
 */
@RequestMapping(value = "/HbaseController")
@RestController
@Slf4j
public class HbaseController {

    @Autowired
    private KafkaOutService kafkaOutService;

    @Autowired
    private SearchServicePlus searchServicePlus;

    @Autowired
    private JavaScriptExecuteScript javaScriptExecuteScript;

    @Autowired
    private HbaseService hbaseService;

    @ApiOperation(value = "函数式处理发送到kafka")
    @PostMapping(value = "/{index}/_search/outputToKafka")
    public Response _search(
            @ApiParam(name = "index", defaultValue = "origin_raw_pevc_k1comp")
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
            @ApiParam(name = "hbaseTable", value = "需要提取的Hbase的表名")
            @RequestParam(value = "hbaseTable")
                    String hbaseTable,
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
                        String oriId = json.get("OriId").toString();
                        Map hbsearch = hbaseService.hbsearch(hbaseTable, oriId);
//                        kafkaOutService.load(topic, json, policyId, toTable.getTable());
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
//                        kafkaOutService.load0088(topic, root0088, policyId);
                        log.info("发送成功:topic:{},policyId:{},json:{}", topic, policyId, root0088);
                    } else if (false == scriptDeal) {
                        log.info("返回false->不处理：topic:{},policyId:{},json:{}", topic, policyId, innerHits.getSource());
                    }
                }
            });
        }

        return Response.Ok(true);
    }
}
