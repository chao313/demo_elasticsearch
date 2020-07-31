package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.compound.DSLQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * {
 *   "query": {
 *     "term": {
 *       "user": {
 *         "value": "Kimchy",
 *         "boost": 1.0
 *       }
 *     }
 *   }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TermRequest extends ToRequestBody {

    private TermQuery query = new TermQuery();

    @Data
    public static class TermQuery extends ToRequestBody implements DSLQuery {
        private Map<String, TermParam> term = new HashMap<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class TermParam {
        @ApiModelProperty(example = " ")
        String value;
        @ApiModelProperty(example = "1.0")
        String boost;
    }

    public static TermRequest builderRequest(String key, String value, String boost) {
        TermRequest termRequest = new TermRequest();
        termRequest.getQuery().getTerm().put(key, new TermParam(value, boost));
        return termRequest;
    }

    public static TermRequest builderRequest(String key, String value) {
        return builderRequest(key, value, "1.0");
    }

    public static TermQuery builderQuery(String key, String value, String boost) {
        return builderRequest(key, value, boost).getQuery();
    }

    public static TermQuery builderQuery(String key, String value) {
        return builderRequest(key, value, "1.0").getQuery();
    }


}
