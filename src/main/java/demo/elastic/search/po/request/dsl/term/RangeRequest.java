package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

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
public class RangeRequest {

    private RangeQuery query = new RangeQuery();

    @Data
    public static class RangeQuery {
        private Map<String, RangeParam> range = new HashMap<>();
    }

    @Data
    public static class RangeParam {
        private Integer gte;
        private Integer gt;
        private Integer lte;
        private Integer lt;
        private Double boost;
    }

}
