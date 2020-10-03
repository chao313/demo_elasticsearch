package demo.elastic.search.controller.origin;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.ScriptService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch script 级别的使用
 */
@RequestMapping(value = "/origin/ScriptController")
@RestController
public class ScriptController {

    @Resource
    private ScriptService scriptService;

    @Resource
    private SearchService searchService;

    @ApiOperation(value = "创建一个Script")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_scripts/{id}")
    public Response add(
            @PathVariable(value = "id") String id,
            @RequestBody String body) {
        ScriptService scriptService = ThreadLocalFeign.getFeignService(ScriptService.class);
        String s = scriptService.add(id, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看一个Script")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_scripts/{id}")
    public Response get(@PathVariable(value = "id") String id) {
        ScriptService scriptService = ThreadLocalFeign.getFeignService(ScriptService.class);
        String s = scriptService.get(id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除一个Script")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @DeleteMapping(value = "/_scripts/{id}")
    public Response delete(@PathVariable(value = "id") String id) {
        ScriptService scriptService = ThreadLocalFeign.getFeignService(ScriptService.class);
        String s = scriptService.del(id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查询一个index的type", notes =
            "<pre>" + "{<br>" +
                    "&nbsp;\"script_fields\": {<br>" +
                    "&nbsp;&nbsp;\"my_doubled_field\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;\"script\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"id\": \"MyScriptsId\",<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"params\": {<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"multiplier\": 20<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
                    "&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
                    "&nbsp;&nbsp;&nbsp;}<br>" +
                    "&nbsp;}<br>" +
                    "}"
                    +
                    "</pre>")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search")
    public Response _search(
//            @PathVariable(value = "index") String index,
            @RequestParam(defaultValue = "comstore_tb_object_0088") String indexTmp,
            @RequestBody String body) {
        ScriptService scriptService = ThreadLocalFeign.getFeignService(ScriptService.class);
        String s = searchService._search(indexTmp, body);
        return Response.Ok(JSONObject.parse(s));
    }


}
















