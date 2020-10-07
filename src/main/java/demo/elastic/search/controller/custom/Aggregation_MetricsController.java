package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.metrics.*;
import demo.elastic.search.po.request.dsl.matchall.MatchAllQuery;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 指标聚合相关
 */
@RequestMapping(value = "/Aggregation_MetricsController")
@RestController
public class Aggregation_MetricsController {


    @ApiOperation(value = "匹配最大值")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/metrics/max")
    public Response aggs_metrics_max(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, MetricsMaxAggs> maxAggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, maxAggs);
        return Response.Ok(JSONObject.parse(result));
    }


    @ApiOperation(value = "匹配最大值")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/metrics/min")
    public Response aggs_metrics_min(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, MetricsMinAggs> aggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, aggs);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "匹配sum")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/metrics/sum")
    public Response aggs_metrics_sum(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, MetricsSumAggs> sumAggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, sumAggs);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "avg")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/metrics/avg")
    public Response aggs_metrics_avg(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, MetricsAvgAggs> aggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, aggs);
        return Response.Ok(JSONObject.parse(result));
    }


    @ApiOperation(value = "stats(min max sum count avg)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/metrics/stats")
    public Response aggs_metrics_stats(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, MetricsStatsAggs> aggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, aggs);
        return Response.Ok(JSONObject.parse(result));
    }


    @ApiOperation(value = "stats(min max sum count avg) 增加了sum_of_squares 平方和 , variance 方差, std_deviation 标准差 , std_deviation_bonds 标准差范围")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/metrics/extended_stats")
    public Response aggs_metrics_extended_stats(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, MetricsExtendedStatsAggs> aggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, aggs);
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = " min_length max_length sum_length avg_length entropy")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search/aggs/metrics/String_stats")
    public Response aggs_metrics_String_stats(
            @PathVariable(value = "index") String index,
            @RequestBody SearchSourceBuilder<MatchAllQuery, MetricsStringStatsAggs> aggs) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, aggs);
        return Response.Ok(JSONObject.parse(result));
    }

}
















