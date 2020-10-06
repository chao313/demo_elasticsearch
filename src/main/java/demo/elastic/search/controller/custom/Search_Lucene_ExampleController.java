package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchLuceneService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.lucene.LuceneQueryStringQuery;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(Lucene example)
 */
@RequestMapping(value = "/Search_Lucene_ExampleController")
@RestController
public class Search_Lucene_ExampleController {

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
                                                     @ApiParam(defaultValue = "city:Bendon", value = "请求体")
                                                     @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
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
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(查询子json) 注意需要转义,注意字段不需要引号")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/field/valueChildJson")
    public Response _search_example_field_valueChildJson(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                                         @PathVariable(value = "index") String index,
                                                         @ApiParam(defaultValue = "name.\\*:(Hattie OR Ayala)", value = "请求体")
                                                         @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(字段存在) ")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/field/_exists_")
    public Response _search_example_field__exists_(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                                   @PathVariable(value = "index") String index,
                                                   @ApiParam(defaultValue = "_exists_:age", value = "请求体")
                                                   @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
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
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
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
                                                 @ApiParam(defaultValue = "name.firstname:\"hai chao\"", value = "请求体")
                                                 @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(通配)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/wildcard")
    public Response _search_example_wildcard(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                             @PathVariable(value = "index") String index,
                                             @ApiParam(defaultValue = "city:Dant?", value = "请求体")
                                             @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(正则)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/regexp")
    public Response _search_example_regexp(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                           @PathVariable(value = "index") String index,
                                           @ApiParam(defaultValue = "city:/dant.*/", value = "请求体,这里索引都存储了小写")
                                           @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(模糊)", notes = "" +
            "短语:address:\"685 School Lana\"~2" +
            "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/Fuzziness")
    public Response _search_example_Fuzziness(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                              @PathVariable(value = "index") String index,
                                              @ApiParam(defaultValue = "city:danta~1", value = "请求体")
                                              @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(模糊)", notes = "<pre>" +
            "<br>1.包含 age:[1 TO 5]" +
            "<br>2.不包含 age:[1 TO 5}" +
            "<br>3.无穷 age:[1 TO *]" +
            "<br>------" +
            "<br>1.大于 age:>1" +
            "<br>2.大于等于 age:>=1" +
            "<br>3.小于 age:&lt;50" +
            "<br>4.小于等于 age:&lt;=50" +
            "<br>5.大于小于 (age:>=1 AND age:&lt;=50)" +
            "</pre>")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/range")
    public Response _search_example_range(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                          @PathVariable(value = "index") String index,
                                          @ApiParam(defaultValue = "age:[1 TO 50]", value = "请求体")
                                          @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "lucene语法检索(加权)", notes = "" +
            "单词 age:35^4" +
            "短语 address:\"685 School Lane\"^4" +
            "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/boosting")
    public Response _search_example_boosting(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                             @PathVariable(value = "index") String index,
                                             @ApiParam(defaultValue = "age:35^4", value = "请求体")
                                             @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

    /**
     * quick brown +fox -news
     * ====
     * <pre>
     * {
     *     "bool": {
     *         "must":     { "match": "fox"         },
     *         "should":   { "match": "quick brown" },
     *         "must_not": { "match": "news"        }
     *     }
     * }
     * </pre>
     */
    @ApiOperation(value = "lucene语法检索(bool)", notes = "" +
            "<br>+age:35 +state:NH == age:35 AND state:NH" +
            "<br>+age:35 -state:NH == age:35 NOT state:NH" +
            "<br>+age:35 -state:NH -gender:M == age:35 NOT state:NH NOT gender:M" +
            "<br>+age:35 -state:NH -gender:M +employer:Zaj == age:35 NOT state:NH NOT gender:M AND employer:Zaj" +
            "<br>------------------" +
            "<br>age:(+1)" +
            "<br>age:(+1 -2) -> 检索age为1 不为2" +
            "<br>age:(1 OR 2 OR 3)  -> 检索age 为1 或者 2 或者3" +
            "<br>age:(1 2 3) xxxx 错误示例" +
            "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/bool")
    public Response _search_example_bool(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                         @PathVariable(value = "index") String index,
                                         @ApiParam(defaultValue = "+age:35 -state:NH -gender:M +employer:Zaj", value = "请求体")
                                         @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }


    @ApiOperation(value = "lucene语法检索(group)", notes = "" +
            "( age:35 OR age:40 ) AND gender:M" +
            "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/example/group")
    public Response _search_example_group(@ApiParam(defaultValue = "index_bulk", value = "指定检索的index")
                                          @PathVariable(value = "index") String index,
                                          @ApiParam(defaultValue = "( age:35 OR age:40 ) AND state:MO", value = "请求体")
                                          @RequestParam String query_String) {
        SearchLuceneService searchLuceneService = ThreadLocalFeign.getFeignService(SearchLuceneService.class);
        SearchSourceBuilder<LuceneQueryStringQuery> request = new SearchSourceBuilder<>();
        request.query(QueryBuilders.queryStringQuery(query_String));
        String result = searchLuceneService._search_query_String(index, request);
        return Response.Ok(JSONObject.parse(result));
    }

}
















