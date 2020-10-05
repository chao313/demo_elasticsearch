package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.dsl.compound.BoolQuery;
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
    public Response _search_exists(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<ExistsQuery> existsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result;
        result = searchService.DSL_search_exists(index, existsRequest);
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
    public Response _search_fuzzy(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<FuzzyQuery> fuzzyRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result;
        result = searchService.DSL_search_fuzzy(index, fuzzyRequest);
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
    public Response _search_ids(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<IDsQuery> iDsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_ids(index, iDsRequest);
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
    public Response _search_prefix(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<PrefixQuery> prefixRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_prefix(index, prefixRequest);
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
    public Response _search_range(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<RangeQuery> rangeRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_range(index, rangeRequest);
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
    public Response _search_regexp(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<RegexpQuery> regexpRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_regexp(index, regexpRequest);
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
    public Response _search_term(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<TermQuery> termRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_term(index, termRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(terms)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/terms/{index}/_search")
    public Response _search_terms(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<TermsQuery> termsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_terms(index, termsRequest);
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
    public Response _search_wildcard(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<WildcardQuery> wildcardRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_wildcard(index, wildcardRequest);
        return Response.Ok(JSONObject.parse(result));
    }



}
















