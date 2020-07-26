package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class FuzzyRequest {

    private FuzzyQuery query = new FuzzyQuery();

    @Data
    public static class FuzzyQuery {
        private Map<String, FuzzyParam> fuzzy = new HashMap<>();
    }

    @Data
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
}
