package demo.elastic.search.po.request.dsl;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * {
 *   "query": {
 *     "regexp": {
 *       "user": {
 *         "value": "k.*y",
 *         "flags": "ALL",
 *         "max_determinized_states": 10000,
 *         "rewrite": "constant_score"
 *       }
 *     }
 *   }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RegexpRequest {

    private RegexpQuery query = new RegexpQuery();

    @Data
    public static class RegexpQuery {
        private Map<String, RegexpParam> regexp = new HashMap<>();
    }

    @Data
    public static class RegexpParam {
        @ApiModelProperty(example = " ")
        String value;
        @ApiModelProperty(example = "ALL")
        String flags;
        @ApiModelProperty(example = "10000")
        Integer max_determinized_states;
        @ApiModelProperty(example = "constant_score")
        String rewrite;
    }

}
