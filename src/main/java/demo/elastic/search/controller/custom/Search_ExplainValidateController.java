package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.ExplainValidateService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(Explain相关+验证相关)
 */
@RequestMapping(value = "/Search_ExplainValidateController")
@RestController
@Slf4j
public class Search_ExplainValidateController {


    @ApiOperation(value = "解释API(解释指定文档为什么匹配到/匹配不到)", notes = "```\n" +
            "{\n" +
            " \"query\": {\n" +
            "  \"bool\": {\n" +
            "   \"must\":[\n" +
            "    {\"exists\": {\"field\": \"age\"}},\n" +
            "    {\"exists\": {\"field\": \"address\"}},\n" +
            "    {\"term\": {\"age\": {\n" +
            "       \"boost\": 0,\n" +
            "       \"value\": 32\n" +
            "      }}\n" +
            "    }\n" +
            "   ]\n" +
            "  }\n" +
            " }\n" +
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
    @PostMapping(value = "/{index}/_explain/{id}")
    public Response _search(
            @ApiParam(defaultValue = "tb_object_0088") @PathVariable(value = "index") String index,
            @ApiParam(defaultValue = "0") @PathVariable(value = "id") String id,
            @RequestBody String body) {
        ExplainValidateService explainValidateService = ThreadLocalFeign.getFeignService(ExplainValidateService.class);
        String result = explainValidateService._explain(index, id, body);
        return Response.Ok(JSONObject.parse(result));
    }


    @ApiOperation(value = "验证搜索的代价", notes = "```\n" +
            "{\n" +
            " \"from\": 0,\n" +
            " \"size\": 1,\n" +
            " \"query\": {\n" +
            "  \"bool\": {\n" +
            "   \"must\":[\n" +
            "    {\"exists\": {\"field\": \"age\"}},\n" +
            "    {\"exists\": {\"field\": \"address\"}},\n" +
            "    {\"term\": {\"age\": {\n" +
            "       \"boost\": 0,\n" +
            "       \"value\": 32\n" +
            "      }}\n" +
            "    }\n" +
            "   ]\n" +
            "  }\n" +
            " }\n" +
            "}"
            + "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_validate/query")
    public Response _validate(
            @ApiParam(defaultValue = "index_bulk")
            @PathVariable(value = "index") String index,
            @ApiParam(defaultValue = "false", value = "如果为true，则对所有分片执行验证，而不是对每个索引随机分配一个分片。默认为false")
            @RequestParam(value = "all_shards", required = false) Boolean all_shards,
            @ApiParam(defaultValue = "false", value = "如果为true，则在发生错误时，响应将返回详细信息。默认为false")
            @RequestParam(value = "explain") Boolean explain,
            @ApiParam(defaultValue = "false", value = "如果为true，则返回更详细的说明，显示将执行的实际Lucene查询。默认为false")
            @RequestParam(value = "rewrite") Boolean rewrite,
            @RequestBody String body) {
        ExplainValidateService explainValidateService = ThreadLocalFeign.getFeignService(ExplainValidateService.class);
        String result = explainValidateService._validate(index, all_shards, explain, rewrite, body);
        return Response.Ok(JSONObject.parse(result));
    }
}
















