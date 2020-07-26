package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class PrefixRequest {

    private PrefixQuery query = new PrefixQuery();

    @Data
    public static class PrefixQuery {
        private Map<String, PrefixParam> prefix = new HashMap<>();
    }

    @Data
    public static class PrefixParam {
        private String value;
    }

}
