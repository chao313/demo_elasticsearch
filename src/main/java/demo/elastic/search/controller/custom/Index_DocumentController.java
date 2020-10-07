package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 索引 文档相关
 */
@RequestMapping(value = "/Index_DocumentController")
@RestController
public class Index_DocumentController {

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

    @ApiOperation(value = "批量操作", notes = "(提供执行多种方式index，create，delete，和update在一个请求的动作) 注意最后一行要单独换行"
            + "\n"
            + "```"
            + "\n"
            + "{ \"index\" : { \"_index\" : \"test\", \"_id\" : \"1\" } }\n" +
            "{ \"field1\" : \"value1\" }\n" +
            "{ \"delete\" : { \"_index\" : \"test\", \"_id\" : \"2\" } }\n" +
            "{ \"create\" : { \"_index\" : \"test\", \"_id\" : \"3\" } }\n" +
            "{ \"field1\" : \"value3\" }\n" +
            "{ \"update\" : {\"_id\" : \"1\", \"_index\" : \"test\"} }\n" +
            "{ \"doc\" : {\"field2\" : \"value2\"} }"
            + "```")
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
            @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService._bulk(index, body);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "将JSON文档添加到指定索引并使其可搜索。如果文档已存在，请更新文档并增加其版本", notes = "```\n"
            + "{\n" +
            "    \"user\":\"kimchy\",\n" +
            "    \"post_date\":\"2009-11-15T14:12:12\",\n" +
            "    \"message\":\"trying out Elasticsearch\"\n" +
            "}" +
            "```")
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
            @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService.put_doc(index, _id, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "将JSON文档添加到指定索引并使其可搜索。如果文档已存在，请更新文档并增加其版本", notes = "```\n"
            + "{\n" +
            "    \"user\":\"kimchy\",\n" +
            "    \"post_date\":\"2009-11-15T14:12:12\",\n" +
            "    \"message\":\"trying out Elasticsearch\"\n" +
            "}" +
            "```")
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

    @ApiOperation(value = "使用指定的脚本更新文档", notes = "```\n"
            + "{\n" +
            "    \"user\":\"kimchy\",\n" +
            "    \"post_date\":\"2009-11-15T14:12:12\",\n" +
            "    \"message\":\"trying out Elasticsearch\"\n" +
            "}" +
            "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_update/{_id}")
    public Response _update(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                            @ApiParam(value = "（必需，字符串）文档的唯一标识符") @PathVariable(value = "_id") String _id,
                            @RequestBody String body) {
        DocumentService documentService = ThreadLocalFeign.getFeignService(DocumentService.class);
        String s = documentService._update(index, _id, body);
        return Response.Ok(JSONObject.parse(s));
    }
}
















