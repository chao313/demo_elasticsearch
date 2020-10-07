package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.bucket.BucketHistogramAggs;
import demo.elastic.search.po.request.aggs.bucket.BucketRangesAggs;
import demo.elastic.search.po.request.aggs.bucket.BucketTermsAggs;
import demo.elastic.search.po.request.dsl.matchall.MatchAllQuery;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 桶聚合 相关
 */
@RequestMapping(value = "/Aggregation_MetricsController")
@RestController
public class Aggregation_BucketController {


    @ApiOperation(value = "terms")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/bucket/terms")
    public Response aggs_bucket_terms(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, BucketTermsAggs> aggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, aggs);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "range", notes = "```" +
            "\n" +
            "{\n" +
            "  \"_source\": [\n" +
            "    \"string\"\n" +
            "  ],\n" +
            "  \"aggs\": \n" +
            "    {\n" +
            "      \"key\": {\n" +
            "        \"range\": {\n" +
            "          \"field\": \"age\",\n" +
            "          \"ranges\": [\n" +
            "            {\n" +
            "              \"from\": 0,\n" +
            "              \"to\": 20\n" +
            "            },\n" +
            "            {\n" +
            "              \"from\": 20,\n" +
            "              \"to\": 40\n" +
            "            }\n" +
            "          ]\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ,\n" +
            "  \"from\": 0,\n" +
            "  \"query\": {\n" +
            "    \"match_all\": {\n" +
            "      \"boost\": 1\n" +
            "    }\n" +
            "  },\n" +
            "  \"size\": 10\n" +
            "}"
            + "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/bucket/range")
    public Response aggs_bucket_range(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, BucketRangesAggs> aggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, aggs);
        return Response.Ok(JSONObject.parse(result));
    }


    @ApiOperation(value = "range", notes = "```" +
            "\n" +
            "{\n" +
            "  \"_source\": [\n" +
            "    \"string\"\n" +
            "  ],\n" +
            "  \"aggs\": \n" +
            "    {\n" +
            "      \"key\": {\n" +
            "        \"histogram\": {\n" +
            "          \"extended_bounds\": {\n" +
            "            \"max\": 400,\n" +
            "            \"min\": 0 \n" +
            "          },\n" +
            "          \"field\": \"age\",\n" +
            "          \"interval\": 5,\n" +
            "          \"keyed\": true,\n" +
            "          \"min_doc_count\": 0,\n" +
            "          \"missing\": 0\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "  \"from\": 0,\n" +
            "  \"query\": {\n" +
            "    \"match_all\": {\n" +
            "      \"boost\": 1\n" +
            "    }\n" +
            "  },\n" +
            "  \"size\": 10\n" +
            "}"
            + "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/bucket/histogram")
    public Response aggs_bucket_histogram(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, BucketHistogramAggs> aggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, aggs);
        return Response.Ok(JSONObject.parse(result));
    }

}
















