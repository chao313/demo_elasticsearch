package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchFullTextService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.dsl.full.*;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(全文检索语法)
 */
@RequestMapping(value = "/Search_DSL_FullTextController")
@RestController
public class Search_DSL_FullTextController {

    @ApiOperation(value = "全文检索(单字段)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_search/match_search", method = RequestMethod.POST)
    public Response match_search(@PathVariable(value = "index") String index,
                                 @RequestBody MatchRequest matchRequest) {
        SearchFullTextService searchFullTextService = ThreadLocalFeign.getFeignService(SearchFullTextService.class);
        String result = searchFullTextService.match_search(index, matchRequest);
        return Response.Ok(JSONObject.parse(result));
    }


    @ApiOperation(value = "全文检索 bool 前缀")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_search/match_bool_prefix_search", method = RequestMethod.POST)
    public Response match_bool_prefix_search(@PathVariable(value = "index") String index,
                                             @RequestBody MatchBoolPrefixRequest matchBoolPrefixRequest) {
        SearchFullTextService searchFullTextService = ThreadLocalFeign.getFeignService(SearchFullTextService.class);
        String result = searchFullTextService.match_bool_prefix_search(index, matchBoolPrefixRequest);
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
    @RequestMapping(value = "/{index}/_search/match_phrase_search", method = RequestMethod.POST)
    public Response match_phrase_search(@PathVariable(value = "index") String index,
                                        @RequestBody MatchPhraseRequest matchPhraseRequest) {
        SearchFullTextService searchFullTextService = ThreadLocalFeign.getFeignService(SearchFullTextService.class);
        String result = searchFullTextService.match_phrase_search(index, matchPhraseRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    /**
     * FULL 搜索(匹配全部的文档)
     */
    @ApiOperation(value = "bool查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_search/match_phrase_prefix_search", method = RequestMethod.POST)
    public Response match_phrase_prefix_search(@PathVariable(value = "index") String index,
                                               @RequestBody MatchPhrasePrefixRequest matchPhrasePrefixRequest) {
        SearchFullTextService searchFullTextService = ThreadLocalFeign.getFeignService(SearchFullTextService.class);
        String result = searchFullTextService.match_phrase_prefix_search(index, matchPhrasePrefixRequest);
        return Response.Ok(JSONObject.parse(result));
    }


    /**
     * FULL multi match 搜索
     */
    @ApiOperation(value = "多字段,全文检索")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_search/match_multi_match_search", method = RequestMethod.POST)
    public Response match_multi_match_search(@PathVariable(value = "index") String index,
                                             @RequestBody MultiMatchRequest multiMatchRequest) {
        SearchFullTextService searchFullTextService = ThreadLocalFeign.getFeignService(SearchFullTextService.class);
        String result = searchFullTextService.match_multi_match_search(index, multiMatchRequest);
        return Response.Ok(JSONObject.parse(result));
    }


}
















