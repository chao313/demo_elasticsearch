package demo.elastic.search.controller.origin;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.index.doc.reindex.ReIndexRequest;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch Document级别的使用
 */
@RequestMapping(value = "/origin/DocumentController")
@RestController
public class DocumentController {

    @Resource
    private DocumentService documentService;

    @ApiOperation(value = "创建一个Document")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PutMapping(value = "/{index}/{type}/{id}")
    public Response add(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "type") String type,
            @PathVariable(value = "id") String id,
            @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService.add(index, type, id, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看一个Document")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/{index}/{type}/{id}")
    public Response get(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "type") String type,
            @PathVariable(value = "id") String id) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService.get(index, type, id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除一个Document")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @DeleteMapping(value = "/{index}/{type}/{id}")
    public Response delete(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "type") String type,
            @PathVariable(value = "id") String id) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService.del(index, type, id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "批量操作", notes = "(提供执行多种方式index，create，delete，和update在一个请求的动作) 注意最后一行要单独换行")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_bulk")
    public Response _bulk(
            @ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
            @ApiParam(value = "{ \"index\" : { \"_index\" : \"test\", \"_id\" : \"1\" } }<br>" +
                    "{ \"field1\" : \"value1\" }<br>" +
                    "{ \"delete\" : { \"_index\" : \"test\", \"_id\" : \"2\" } }<br>" +
                    "{ \"create\" : { \"_index\" : \"test\", \"_id\" : \"3\" } }<br>" +
                    "{ \"field1\" : \"value3\" }<br>" +
                    "{ \"update\" : {\"_id\" : \"1\", \"_index\" : \"test\"} }<br>" +
                    "{ \"doc\" : {\"field2\" : \"value2\"} }")
            @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService._bulk(index, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "将JSON文档添加到指定索引并使其可搜索。如果文档已存在，请更新文档并增加其版本")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PutMapping(value = "/{index}/_doc/{_id}")
    public Response put_doc(
            @ApiParam(value = "（必需，字符串）目标索引的名称。默认情况下，如果索引不存在，则会自动创建")
            @PathVariable(value = "index") String index,
            @PathVariable(value = "_id") String _id,
            @ApiParam(value = "{<br>" +
                    "  \"user\" : \"kimchy\",<br>" +
                    "  \"post_date\" : \"2009-11-15T14:12:12\",<br>" +
                    "  \"message\" : \"trying out Elasticsearch\"<br>" +
                    "}")
            @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService.put_doc(index, _id, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "将JSON文档添加到指定索引并使其可搜索。如果文档已存在，请更新文档并增加其版本")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_doc")
    public Response post_doc(
            @ApiParam(value = "（必需，字符串）目标索引的名称。默认情况下，如果索引不存在，则会自动创建")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "{<br>" +
                    "  \"user\" : \"kimchy\",<br>" +
                    "  \"post_date\" : \"2009-11-15T14:12:12\",<br>" +
                    "  \"message\" : \"trying out Elasticsearch\"<br>" +
                    "}")
            @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService.post_doc(index, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "从索引检索指定的JSON文档")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/{index}/_doc/{_id}")
    public Response get(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                        @ApiParam(value = "（必需，字符串）文档的唯一标识符") @PathVariable(value = "_id") String _id) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService.get(index, _id);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "从指定的索引中删除JSON文档")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @DeleteMapping(value = "/{index}/_doc/{_id}")
    public Response delete(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                           @ApiParam(value = "（必需，字符串）文档的唯一标识符") @PathVariable(value = "_id") String _id) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService.delete(index, _id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除与指定查询匹配的文档")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_delete_by_query")
    public Response _delete_by_query(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                                     @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService._delete_by_query(index, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "使用指定的脚本更新文档")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @DeleteMapping(value = "/{index}/_update/{_id}")
    public Response _update(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                            @ApiParam(value = "（必需，字符串）文档的唯一标识符") @PathVariable(value = "_id") String _id,
                            @ApiParam(value = "{<br>" +
                                    "  \"script\" : {<br>" +
                                    "    \"source\": \"ctx._source.age += params.count\",<br>" +
                                    "    \"lang\": \"painless\",<br>" +
                                    "    \"params\" : {<br>" +
                                    "      \"count\" : 4<br>" +
                                    "    }<br>" +
                                    "  }<br>" +
                                    "}")
                            @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService._update(index, _id, body);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "将文档从一个索引复制到另一索引", notes = "<pre>{<br>" +
            "&nbsp;\"conflicts\": \"\",<br>" +
            "&nbsp;\"dest\": {<br>" +
            "&nbsp;&nbsp;\"index\": \"\",<br>" +
            "&nbsp;&nbsp;\"op_type\": \"\",<br>" +
            "&nbsp;&nbsp;\"version_type\": \"\"<br>" +
            "&nbsp;},<br>" +
            "&nbsp;\"script\": {<br>" +
            "&nbsp;&nbsp;\"lang\": \"\",<br>" +
            "&nbsp;&nbsp;\"source\": \"\"<br>" +
            "&nbsp;},<br>" +
            "&nbsp;\"source\": {<br>" +
            "&nbsp;&nbsp;\"_source\": [],<br>" +
            "&nbsp;&nbsp;\"index\": \"\",<br>" +
            "&nbsp;&nbsp;\"max_docs\": \"\",<br>" +
            "&nbsp;&nbsp;\"query\": \"\",<br>" +
            "&nbsp;&nbsp;\"remote\": {<br>" +
            "&nbsp;&nbsp;&nbsp;\"connect_timeout\": \"\",<br>" +
            "&nbsp;&nbsp;&nbsp;\"host\": \"\",<br>" +
            "&nbsp;&nbsp;&nbsp;\"password\": \"\",<br>" +
            "&nbsp;&nbsp;&nbsp;\"socket_timeout\": \"\",<br>" +
            "&nbsp;&nbsp;&nbsp;\"username\": \"\"<br>" +
            "&nbsp;&nbsp;},<br>" +
            "&nbsp;&nbsp;\"size\":\"\",<br>" +
            "&nbsp;&nbsp;\"slice\": {<br>" +
            "&nbsp;&nbsp;&nbsp;\"id\": \"\",<br>" +
            "&nbsp;&nbsp;&nbsp;\"max\": \"\"<br>" +
            "&nbsp;&nbsp;}<br>" +
            "&nbsp;}<br>" +
            "}</pre>")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_reindex")
    public Response _reindex(@RequestBody ReIndexRequest reIndexRequest) throws JsonProcessingException {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        JsonMapper mapper = new JsonMapper();
        String body = mapper.writeValueAsString(reIndexRequest);
        String s = documentService._reindex(reIndexRequest);
        return Response.Ok(JSONObject.parse(s));
    }

}
















