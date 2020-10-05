package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import demo.elastic.search.po.request.dsl.DSLRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 *  "fuzzy":
 *  {
 *   "user": {
 *       "value": "ki",
 *       "fuzziness": "AUTO",
 *       "max_expansions": 50,
 *       "prefix_length": 0,
 *       "transpositions": true,
 *       "rewrite": "constant_score"
 *   }
 *  }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FuzzyRequest extends ToRequestBody implements DSLRequest {

    private FuzzyQuery query = new FuzzyQuery();

    @Data
    public static class FuzzyQuery implements DSLQuery {
        private Map<String, FuzzyParam> fuzzy = new HashMap<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class FuzzyParam {
        @ApiModelProperty(example = " ")
        String value;
        @ApiModelProperty(example = "AUTO")
        String fuzziness;
        @ApiModelProperty(example = "50")
        Integer max_expansions;
        @ApiModelProperty(example = "0")
        Integer prefixLength;
        @ApiModelProperty(example = "true")
        Boolean transpositions;
        @ApiModelProperty(example = "constant_score")
        String rewrite;
    }

    public static FuzzyRequest builderRequest(String key, String value) {
        return builderRequest(key, value, "AUTO", 50, 0, true, "constant_score");
    }

    public static FuzzyRequest builderRequest(String key, String value, String fuzziness) {
        return builderRequest(key, value, fuzziness, 50, 0, true, "constant_score");
    }


    public static FuzzyRequest builderRequest(String key, String value, String fuzziness, Integer max_expansions, Integer prefixLength, Boolean transpositions, String rewrite) {
        FuzzyRequest request = new FuzzyRequest();
        request.getQuery().getFuzzy().put(key, new FuzzyParam(value, fuzziness, max_expansions, prefixLength, transpositions, rewrite));
        return request;
    }

    public static FuzzyQuery builderQuery(String key, String value) {
        return builderRequest(key, value, "AUTO", 50, 0, true, "constant_score").getQuery();
    }

    public static FuzzyQuery builderQuery(String key, String value, String fuzziness) {
        return builderRequest(key, value, fuzziness, 50, 0, true, "constant_score").getQuery();
    }


    public static FuzzyQuery builderQuery(String key, String value, String fuzziness, Integer max_expansions, Integer prefixLength, Boolean transpositions, String rewrite) {
        return builderRequest(key, value, fuzziness, max_expansions, prefixLength, transpositions, rewrite).getQuery();
    }
}
