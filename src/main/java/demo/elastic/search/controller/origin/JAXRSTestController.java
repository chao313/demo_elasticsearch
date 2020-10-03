package demo.elastic.search.controller.origin;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.feign.JAXRSContract;
import demo.elastic.search.config.feign.SpringDecoder;
import demo.elastic.search.config.feign.SpringEncoder;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.jaxrs.JAXRSService;
import demo.elastic.search.framework.Response;
import feign.Feign;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用于 测试使用
 */
@RequestMapping(value = "/JAXRSTestController")
@RestController
public class JAXRSTestController {

    @Autowired
    private SpringDecoder springDecoder;
    @Autowired
    private SpringEncoder springEncoder;

    @ApiOperation(value = "列出Cat的全部接口", notes = "列出CAT的全部接口")
    @GetMapping(value = "/_cat")
    public String cat() {
        CatService catService = Feign.builder()
                .contract(new JAXRSContract())
                .target(CatService.class,
                        "http://localhost:80");
        String result = catService._cat();

        return result;
    }


    @ApiOperation(value = "查询一个index的type", notes =
            "comstore_tb_object_0088<br>" +
                    "<pre>" +
                    " curl -X GET  \"http://39.107.236.187:9200/bank/_doc/_search?pretty\" -H 'Content-Type: application/json' -d'<br>" +
                    " {<br>" +
                    " &nbsp;\"from\": 0,<br>" +
                    " &nbsp;\"size\": 1,<br>" +
                    " &nbsp;\"query\": {<br>" +
                    " &nbsp;&nbsp;\"bool\": {<br>" +
                    " &nbsp;&nbsp;&nbsp;\"must\":[<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"exists\": {\"field\": \"age\"}},<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"exists\": {\"field\": \"address\"}},<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"term\": {\"age\": {<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp &nbsp;\"boost\": 0,<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp &nbsp;\"value\": 32<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp }}<br>" +
                    " &nbsp;&nbsp;&nbsp; }<br>" +
                    " &nbsp;&nbsp;&nbsp;]<br>" +
                    " &nbsp;&nbsp;}<br>" +
                    " &nbsp;}<br>" +
                    "}'<br>" +
                    "</pre>")
    @PostMapping(value = "/{index}/_search")
    public Response _search(
            @ApiParam(defaultValue = "tb_object_0088") @PathVariable(value = "index") String index,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestBody String body) {
        JAXRSService JAXRSService = Feign.builder()
                .contract(new JAXRSContract())
                .target(JAXRSService.class,
                        "http://localhost:80");
        String result = JAXRSService._search(index, body);

        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "返回集群的运行状况")
    @GetMapping(value = "/_cat/health")
    public Response _cat_health(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        JAXRSService JAXRSService = Feign.builder()
                .contract(new JAXRSContract())
                .encoder(springEncoder)
                .decoder(springDecoder)
                .target(JAXRSService.class,
                        "http://localhost:80");
        String result = JAXRSService._cat_health(v);

        return Response.Ok(result);
    }


}
















