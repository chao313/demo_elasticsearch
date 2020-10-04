package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchLuceneService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.lucene.LuceneRequest;
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
                            @RequestBody LuceneRequest luceneRequest) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        String result = searchLuceneService._search(index, luceneRequest);
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
    @PostMapping(value = "/{index}/_search/example/")
    public Response _search_example(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                    @PathVariable(value = "index") String index,
                                    @RequestBody String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        LuceneRequest luceneRequest = LuceneRequest.builderRequest(query_String);
        String result = searchLuceneService._search(index, luceneRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(单独的word)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/field/SingleWord")
    public Response _search_example_field_SingleWord(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                                     @PathVariable(value = "index") String index,
                                                     @ApiParam(defaultValue = "city:Brogan", value = "请求体")
                                                     @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        LuceneRequest luceneRequest = LuceneRequest.builderRequest(query_String);
        String result = searchLuceneService._search(index, luceneRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(field存在空格) 注意需要转义,注意字段不需要引号")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/field/fieldWithBlank")
    public Response _search_example_field_fieldWithBlank(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                                         @PathVariable(value = "index") String index,
                                                         @ApiParam(defaultValue = "account\\ number:1", value = "请求体")
                                                         @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        LuceneRequest luceneRequest = LuceneRequest.builderRequest(query_String);
        String result = searchLuceneService._search(index, luceneRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(多个的word)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/field/MoreWord")
    public Response _search_example_field_MoreWord(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                                   @PathVariable(value = "index") String index,
                                                   @ApiParam(defaultValue = "city:(Brogan OR Dante)", value = "请求体")
                                                   @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        LuceneRequest luceneRequest = LuceneRequest.builderRequest(query_String);
        String result = searchLuceneService._search(index, luceneRequest);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(短语)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/field/Phrase")
    public Response _search_example_field_Phrase(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                                 @PathVariable(value = "index") String index,
                                                 @ApiParam(defaultValue = "address:\"880 Holmes Lane\"", value = "请求体")
                                                 @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        LuceneRequest luceneRequest = LuceneRequest.builderRequest(query_String);
        String result = searchLuceneService._search(index, luceneRequest);
        return Response.Ok(JSONObject.parse(result));
    }

}
















