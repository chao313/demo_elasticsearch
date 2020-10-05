package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchLuceneService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.lucene.LuceneQueryStringRequest;
import demo.elastic.search.po.request.lucene.LuceneSimpleQueryStringRequest;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(Lucene语法)
 */
@RequestMapping(value = "/Search_LuceneController")
@RestController
public class Search_LuceneController {

    @ApiOperation(value = "lucene语法检索")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index,
                            @RequestBody LuceneQueryStringRequest luceneQueryStringRequest) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        String result = searchLuceneService._search(index, luceneQueryStringRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(简单的只有query_String)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/query_String")
    public Response _search_query_String(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                         @PathVariable(value = "index") String index,
                                         @RequestBody String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        LuceneQueryStringRequest luceneQueryStringRequest = LuceneQueryStringRequest.builderRequest(query_String);
        String result = searchLuceneService._search(index, luceneQueryStringRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(简单的只有simple_query_String)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/simple_query_String")
    public Response _search_simple_query_String(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                                @PathVariable(value = "index") String index,
                                                @RequestBody String simple_query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        LuceneSimpleQueryStringRequest luceneSimpleQueryStringRequest = LuceneSimpleQueryStringRequest.builderRequest(simple_query_String);
        String result = searchLuceneService._search(index, luceneSimpleQueryStringRequest);
        return Response.Ok(JSONObject.parse(result));
    }
}
















