package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  {
 *    "query": {
 *      "prefix": {
 *        "user": {
 *          "value": "ki"
 *        }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PrefixQuery extends ToRequestBody implements Query {

    private Map<String, PrefixParam> prefix = new HashMap<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrefixParam {
        private String value;
    }

    public static PrefixQuery builderQuery(String key, String value) {
        PrefixQuery query = new PrefixQuery();
        query.getPrefix().put(key, new PrefixParam(value));
        return query;
    }

}
