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
 * "max_price" : { "max" : { "field" : "age" } }
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MetricsMaxAggs implements Metrics, Aggs {

    private Map<String, MaxAggsMiddle> aggs = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MaxAggsMiddle {
        private AggsParam max;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AggsParam {
        private String field;
    }


}