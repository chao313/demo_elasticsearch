package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
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
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/exists")
    public Response _search_exists(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<ExistsQuery, VoidAggs> existsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result;
        result = searchService.DSL_search_exists(index, existsRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(相似)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/fuzzy")
    public Response _search_fuzzy(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<FuzzyQuery, VoidAggs> fuzzyRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result;
        result = searchService.DSL_search_fuzzy(index, fuzzyRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(ids)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/ids")
    public Response _search_ids(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<IDsQuery, VoidAggs> iDsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_ids(index, iDsRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(prefix)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/prefix")
    public Response _search_prefix(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<PrefixQuery, VoidAggs> prefixRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_prefix(index, prefixRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(range)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/range")
    public Response _search_range(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<RangeQuery, VoidAggs> rangeRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_range(index, rangeRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(regexp)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/regexp")
    public Response _search_regexp(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<RegexpQuery, VoidAggs> regexpRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_regexp(index, regexpRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(term)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/term")
    public Response _search_term(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<TermQuery, VoidAggs> termRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_term(index, termRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(terms)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/terms")
    public Response _search_terms(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<TermsQuery, VoidAggs> termsRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_terms(index, termsRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "DSL检索(wildcard)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/{index}/_search/wildcard")
    public Response _search_wildcard(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<WildcardQuery, VoidAggs> wildcardRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_wildcard(index, wildcardRequest);
        return Response.Ok(JSONObject.parse(result));
    }


}
















