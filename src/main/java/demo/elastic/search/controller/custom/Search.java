package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关
 */
@RequestMapping(value = "/Search")
@RestController
public class Search {

    @ApiOperation(value = "查询(普通)", notes = "```\n" +
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
    @PostMapping(value = "/{index}/_search")
    public Response _search(
            @ApiParam(defaultValue = "tb_object_0088") @PathVariable(value = "index") String index,
            @RequestBody String body) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, body);
        return Response.Ok(JSONObject.parse(result));
    }
}
















