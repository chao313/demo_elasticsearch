package demo.elastic.search.po.request.aggs.bucket;


import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Aggs;
import demo.elastic.search.po.request.Bucket;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 聚合
 * <pre>
 *  "genres" : {
 *             "terms" : {
 *               "field" : "F26_0088"
 *                }
 *         }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BucketTermsAggs implements Bucket, Aggs {

    private Map<String, TermsBucketAggsMiddle> aggs = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class TermsBucketAggsMiddle {
        private AggsParam terms;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AggsParam {
        private String field;
    }


}