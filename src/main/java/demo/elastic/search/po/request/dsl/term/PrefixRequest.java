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
public class PrefixRequest extends ToRequestBody implements DSLRequest {

    private PrefixQuery query = new PrefixQuery();

    @Data
    public static class PrefixQuery implements DSLQuery {
        private Map<String, PrefixParam> prefix = new HashMap<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PrefixParam {
        private String value;
    }

    public static PrefixRequest builderRequest(String key, String value) {
        PrefixRequest request = new PrefixRequest();
        request.getQuery().getPrefix().put(key, new PrefixParam(value));
        return request;
    }

    public static PrefixQuery builderQuery(String key, String value) {
        return builderRequest(key, value).getQuery();
    }
}
