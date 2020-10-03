package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.MappingService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 索引 mapping相关
 */
@RequestMapping(value = "/Index_MappingController")
@RestController
public class Index_MappingController {

    @ApiOperation(value = "查看指定index的全部的mapping")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_mapping")
    public Response get(@PathVariable(value = "index") String index) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        String s = mappingService.get(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看指定index的全部的mapping")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_mapping/field/{fieldName}")
    public Response get(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "fieldName") String fieldName) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
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
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PutMapping(value = "/{index}/_mapping")
    public Response put(@PathVariable(value = "index") String index,
                        @RequestBody String body) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
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
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PutMapping(value = "/{index}/_mapping/{type}")
    public Response put(@PathVariable(value = "index") String index,
                        @PathVariable(value = "type") String type,
                        @RequestBody String body) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        String s = mappingService.put(index, type, body);
        return Response.Ok(JSONObject.parse(s));
    }

}
















