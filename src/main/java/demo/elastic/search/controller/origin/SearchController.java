package demo.elastic.search.controller.origin;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.dsl.compound.BoolRequest;
import demo.elastic.search.po.request.dsl.term.*;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 查询使用
 */
@RequestMapping(value = "/origin/SearchController")
@RestController
public class SearchController {

    @Resource
    private SearchService searchService;

//    @ApiOperation(value = "查询一个index的type")
//    @PostMapping(value = "/{index}/{type}/_search")
//    public Response _search(
//            @PathVariable(value = "index") String index,
//            @PathVariable(value = "type") String type,
//            @RequestBody String body) {
//        String s = searchService._search(index, type, body);
//        return Response.Ok(JSONObject.parse(s));
//    }


    @ApiOperation(value = "查询一个index的type", notes =
            "comstore_tb_object_0088<br>" +
                    "<pre>" +
                    " curl -X GET  \"http://39.107.236.187:9200/bank/_doc/_search?pretty\" -H 'Content-Type: application/json' -d'<br>" +
                    " {<br>" +
                    " &nbsp;\"from\": 0,<br>" +
                    " &nbsp;\"size\": 1,<br>" +
                    " &nbsp;\"query\": {<br>" +
                    " &nbsp;&nbsp;\"bool\": {<br>" +
                    " &nbsp;&nbsp;&nbsp;\"must\":[<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"exists\": {\"field\": \"age\"}},<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"exists\": {\"field\": \"address\"}},<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"term\": {\"age\": {<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp &nbsp;\"boost\": 0,<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp &nbsp;\"value\": 32<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp }}<br>" +
                    " &nbsp;&nbsp;&nbsp; }<br>" +
                    " &nbsp;&nbsp;&nbsp;]<br>" +
                    " &nbsp;&nbsp;}<br>" +
                    " &nbsp;}<br>" +
                    "}'<br>" +
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
            @ApiParam(defaultValue = "tb_object_0088") @PathVariable(value = "index") String index,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll,
            @RequestBody String body) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result;
        if (StringUtils.isBlank(scroll)) {
            result = searchService._search(index, body);
        } else {
            result = searchService._search(index, scroll, body);
        }

        return Response.Ok(JSONObject.parse(result));
    }


    @ApiOperation(value = "DSL检索(存在)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/exists/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody ExistsRequest existsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result;
        result = searchService.DSL_search(index, existsRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(相似)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/fuzzy/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody FuzzyRequest fuzzyRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result;
        result = searchService.DSL_search(index, fuzzyRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(ids)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/ids/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody IDsRequest iDsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search(index, iDsRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(prefix)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/prefix/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody PrefixRequest prefixRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search(index, prefixRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(range)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/range/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody RangeRequest rangeRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search(index, rangeRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(regexp)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/regexp/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody RegexpRequest regexpRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search(index, regexpRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(term)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/term/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody TermRequest termRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search(index, termRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(term)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/terms/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody TermsRequest termsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search(index, termsRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(wildcard)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/wildcard/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody WildcardRequest wildcardRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search(index, wildcardRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "bool查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/bool/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody BoolRequest boolRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_bool_search(index, boolRequest);
        return Response.Ok(JSONObject.parse(result));
    }

}
