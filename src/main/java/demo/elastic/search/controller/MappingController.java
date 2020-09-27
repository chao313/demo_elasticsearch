package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.MappingService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch mapping 级别的使用
 */
@RequestMapping(value = "/MappingController")
@RestController
public class MappingController {

    @Resource
    private MappingService mappingService;

    @ApiOperation(value = "查看指定index的全部的mapping")
    @PostMapping(value = "/{index}/_mapping")
    public Response get(
            @PathVariable(value = "index") String index) {
        String s = mappingService.get(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看指定index的全部的mapping")
    @PostMapping(value = "/{index}/_mapping/field/{fieldName}")
    public Response get(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "fieldName") String fieldName) {
        String s = mappingService.get(index, fieldName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "添加新的字段 v6", notes = "" +
            "{" +
            "<br>" +
            "  \"properties\": {<br>" +
            "    \"email\": {<br>" +
            "      \"type\": \"string\"<br>" +
            "    }<br>" +
            "  }<br>" +
            "}")
    @PutMapping(value = "/{index}/_mapping")
    public Response put(@PathVariable(value = "index") String index,
                        @RequestBody String body) {
        String s = mappingService.put(index, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "添加新的字段 v2.3", notes = "" +
            "{" +
            "<br>" +
            "  \"properties\": {<br>" +
            "    \"email\": {<br>" +
            "      \"type\": \"string\"<br>" +
            "    }<br>" +
            "  }<br>" +
            "}")
    @PutMapping(value = "/{index}/_mapping/{type}")
    public Response put(@PathVariable(value = "index") String index,
                        @PathVariable(value = "type") String type,
                        @RequestBody String body) {
        String s = mappingService.put(index, type, body);
        return Response.Ok(JSONObject.parse(s));
    }

}
















