package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.feign.IndexService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.index.doc.reindex.ReIndexRequest;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 索引 新索引相关
 */
@RequestMapping(value = "/Index_NewIndexController")
@RestController
public class Index_NewIndexController {

    @ApiOperation(value = "克隆index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{source_index}/_clone/{target_index}", method = RequestMethod.POST)
    public Response _clone(
            @ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
            @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._clone(source_index, target_index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "使用更少的主碎片将现有索引缩减为新索引")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{source_index}/_shrink/{target_index}", method = RequestMethod.POST)
    public Response _shrink(
            @ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
            @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._shrink(source_index, target_index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "将现有索引拆分为具有更多主碎片的新索引")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{source_index}/_split/{target_index}", method = RequestMethod.POST)
    public Response _split(
            @ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
            @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._split(source_index, target_index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "将文档从一个索引复制到另一索引", notes = "```" +
            "\n" +
            "{\n" +
            "    \"conflicts\":\"\",\n" +
            "    \"dest\":{\n" +
            "        \"index\":\"\",\n" +
            "        \"op_type\":\"\",\n" +
            "        \"version_type\":\"\"\n" +
            "    },\n" +
            "    \"script\":{\n" +
            "        \"lang\":\"\",\n" +
            "        \"source\":\"\"\n" +
            "    },\n" +
            "    \"source\":{\n" +
            "        \"_source\":[],\n" +
            "        \"index\":\"\",\n" +
            "        \"max_docs\":\"\",\n" +
            "        \"query\":\"\",\n" +
            "        \"remote\":{\n" +
            "            \"connect_timeout\":\"\",\n" +
            "            \"host\":\"\",\n" +
            "            \"password\":\"\",\n" +
            "            \"socket_timeout\":\"\",\n" +
            "            \"username\":\"\"\n" +
            "        },\n" +
            "        \"size\":\"\",\n" +
            "        \"slice\":{\n" +
            "            \"id\":\"\",\n" +
            "            \"max\":\"\"\n" +
            "        }\n" +
            "    }\n" +
            "}" +
            "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
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
















