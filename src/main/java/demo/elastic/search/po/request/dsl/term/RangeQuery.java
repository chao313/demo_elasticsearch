package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Query;
import demo.elastic.search.po.request.ToRequestBody;
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
public class RangeQuery extends ToRequestBody implements Query {

    private Map<String, RangeParam> range = new HashMap<>();

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

    public static RangeQuery builderQuery(String key, String gte, String gt, String lte, String lt, Double boost) {
        RangeQuery query = new RangeQuery();
        query.getRange().put(key, new RangeParam(gte, gt, lte, lt, boost));
        return query;
    }

    public static RangeQuery builderQuery(String key, String gte, String gt, String lte, String lt) {
        return builderQuery(key, gte, gt, lte, lt, 1.0);
    }

    public static RangeQuery builderQuery(String key, String gte, String lte) {
        return builderQuery(key, gte, null, lte, null, 1.0);
    }

    public static RangeQuery builderGteQuery(String key, String gte) {
        return builderQuery(key, gte, null, null, null, 1.0);
    }

    public static RangeQuery builderLteQuery(String key, String lte) {
        return builderQuery(key, null, null, lte, null, 1.0);
    }


}
