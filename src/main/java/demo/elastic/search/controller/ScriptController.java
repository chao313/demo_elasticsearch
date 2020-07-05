package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.ScriptService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch script 级别的使用
 */
@RequestMapping(value = "/ScriptController")
@RestController
public class ScriptController {

    @Resource
    private ScriptService scriptService;

    @Resource
    private SearchService searchService;

    @ApiOperation(value = "创建一个Script")
    @PostMapping(value = "/_scripts/{id}")
    public Response add(
            @PathVariable(value = "id") String id,
            @RequestBody String body) {
        String s = scriptService.add(id, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看一个Script")
    @GetMapping(value = "/_scripts/{id}")
    public Response get(
            @PathVariable(value = "id") String id) {
        String s = scriptService.get(id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除一个Script")
    @DeleteMapping(value = "/_scripts/{id}")
    public Response delete(
            @PathVariable(value = "id") String id) {
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
    @PostMapping(value = "/{index}/_search")
    public Response _search(
//            @PathVariable(value = "index") String index,
            @RequestParam(defaultValue = "comstore_tb_object_0088") String indexTmp,
            @RequestBody String body) {
        String s = searchService._search(indexTmp, body);
        return Response.Ok(JSONObject.parse(s));
    }


}
















