package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import demo.elastic.search.po.request.dsl.DSLRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * {
 *   "query": {
 *     "range": {
 *       "age": {
 *         "gte": 10,
 *         "lte": 20,
 *         "boost": 2.0
 *       }
 *     }
 *   }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RangeRequest extends ToRequestBody implements DSLRequest {

    private RangeQuery query = new RangeQuery();

    @Data
    public static class RangeQuery implements DSLQuery {
        private Map<String, RangeParam> range = new HashMap<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class RangeParam {
        private String gte;
        private String gt;
        private String lte;
        private String lt;
        private Double boost;
    }

    public static RangeRequest builderRequest(String key, String gte, String gt, String lte, String lt, Double boost) {
        RangeRequest request = new RangeRequest();
        request.getQuery().getRange().put(key, new RangeParam(gte, gt, lte, lt, boost));
        return request;
    }

    public static RangeRequest builderRequest(String key, String gte, String gt, String lte, String lt) {
        return builderRequest(key, gte, gt, lte, lt, 1.0);
    }

    public static RangeRequest builderRequest(String key, String gte, String lte) {
        return builderRequest(key, gte, null, lte, null, 1.0);
    }

    public static RangeRequest builderGteRequest(String key, String gte) {
        return builderRequest(key, gte, null, null, null, 1.0);
    }

    public static RangeRequest builderLteRequest(String key, String lte) {
        return builderRequest(key, null, null, lte, null, 1.0);
    }

    public static RangeQuery builderQuery(String key, String gte, String gt, String lte, String lt, Double boost) {
        return builderRequest(key, gte, gt, lte, lt, boost).getQuery();
    }

    public static RangeQuery builderQuery(String key, String gte, String gt, String lte, String lt) {
        return builderRequest(key, gte, gt, lte, lt, 1.0).getQuery();
    }

    public static RangeQuery builderQuery(String key, String gte, String lte) {
        return builderRequest(key, gte, null, lte, null, 1.0).getQuery();
    }

    public static RangeQuery builderGteQuery(String key, String gte) {
        return builderRequest(key, gte, null, null, null, 1.0).getQuery();
    }

    public static RangeQuery builderLteQuery(String key, String lte) {
        return builderRequest(key, null, null, lte, null, 1.0).getQuery();
    }

}
