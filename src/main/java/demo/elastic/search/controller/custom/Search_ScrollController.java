package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.ScrollService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(滚动语法)
 */
@RequestMapping(value = "/Search_ScrollController")
@RestController
public class Search_ScrollController {


    @ApiOperation(value = "使用滚动查询", notes = "```" +
            "\n" +
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
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search")
    public Response _search(
            @ApiParam(defaultValue = "tb_object_0088") @PathVariable(value = "index") String index,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll") String scroll,
            @RequestBody String body) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, scroll, body);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "使用滚动查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_search/scroll")
    public Response _search(
            @ApiParam(name = "scroll_id", value = "有效的scroll_id")
            @RequestParam(value = "scroll_id") String scroll_id,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll) {
        ScrollService scrollService = ThreadLocalFeign.getFeignService(ScrollService.class);
        String result = scrollService._search(scroll, scroll_id);

        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "清除滚动查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @DeleteMapping(value = "/_search/scroll")
    public Response _search(
            @ApiParam(name = "scroll_id", value = "有效的scroll_id")
            @RequestParam(value = "scroll_id") String scroll_id) {
        ScrollService scrollService = ThreadLocalFeign.getFeignService(ScrollService.class);
        String result = scrollService._search(scroll_id);
        return Response.Ok(JSONObject.parse(result));
    }
}
















