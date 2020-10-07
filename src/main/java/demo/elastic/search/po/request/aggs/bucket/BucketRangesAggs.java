package demo.elastic.search.po.request.aggs.bucket;


import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Aggs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 范围聚合(聚合指定访问的文档数量)
 *
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/search-aggregations-bucket-range-aggregation.html¬"></a>
 *
 * <pre>
 *  {
 *   "aggs": {
 *     "price_ranges": {
 *       "range": {
 *         "field": "price",
 *         "ranges": [
 *           { "to": 100.0 },
 *           { "from": 100.0, "to": 200.0 },
 *           { "from": 200.0 }
 *         ]
 *       }
 *     }
 *   }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BucketRangesAggs implements Aggs {

    private Map<String, RangeBucketAggsMiddleOne> aggs = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class RangeBucketAggsMiddleOne {
        private RangeBucketAggsMiddle range;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class RangeBucketAggsMiddle {
        private String field;
        private List<RangeBucketAggsParam> ranges;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class RangeBucketAggsParam {
        private Integer from;
        private Integer to;
    }


}