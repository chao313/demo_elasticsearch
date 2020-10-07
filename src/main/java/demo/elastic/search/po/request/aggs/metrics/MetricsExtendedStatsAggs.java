package demo.elastic.search.po.request.aggs.metrics;


import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Aggs;
import demo.elastic.search.po.request.Metrics;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 聚合 求和
 * "grades_stats" : { "extended_stats" : { "field" : "age" } }
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MetricsExtendedStatsAggs implements Metrics, Aggs {

    private Map<String, ExtendedStatsAggsMiddle> aggs = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ExtendedStatsAggsMiddle {
        private AggsParam extended_stats;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AggsParam {
        private String field;
    }


}