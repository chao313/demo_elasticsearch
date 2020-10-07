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
 * 聚合
 * "max_price" : { "min" : { "field" : "age" } }
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MetricsMinAggs implements Metrics, Aggs {

    private Map<String, MinAggsMiddle> aggs = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MinAggsMiddle {
        private AggsParam min;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AggsParam {
        private String field;
    }


}