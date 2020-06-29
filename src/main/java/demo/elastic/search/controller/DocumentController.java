package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 用于 ElasticSearch Document级别的使用
 */
@RequestMapping(value = "/DocumentController")
@RestController
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @ApiOperation(value = "创建一个Document")
    @PutMapping(value = "/{index}/{type}/{id}")
    public Response add(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "type") String type,
            @PathVariable(value = "id") String id,
            @RequestBody String body) {
        String s = documentService.add(index, type, id, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看一个Document")
    @GetMapping(value = "/{index}/{type}/{id}")
    public Response get(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "type") String type,
            @PathVariable(value = "id") String id) {
        String s = documentService.get(index, type, id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除一个Document")
    @DeleteMapping(value = "/{index}/{type}/{id}")
    public Response delete(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "type") String type,
            @PathVariable(value = "id") String id) {
        String s = documentService.del(index, type, id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "批量插入")
    @PostMapping(value = "/{index}/_bulk")
    public Response _bulk(
            @PathVariable(value = "index") String index,
            @RequestBody String body) {
        String s = documentService._bulk(index, body);
        return Response.Ok(JSONObject.parse(s));
    }


}
















