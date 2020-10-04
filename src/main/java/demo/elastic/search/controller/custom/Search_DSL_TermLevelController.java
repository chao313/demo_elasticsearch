package demo.elastic.search.controller.custom;

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
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(DSL 术语级查询 语法)
 */
@RequestMapping(value = "/Search_DSLController")
@RestController
public class Search_DSL_TermLevelController {
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
















