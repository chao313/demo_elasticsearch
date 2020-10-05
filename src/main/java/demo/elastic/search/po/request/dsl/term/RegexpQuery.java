package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
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
public class RegexpQuery extends ToRequestBody implements DSLQuery {

    private Map<String, RegexpParam> regexp = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
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

    public static RegexpQuery builderQuery(String key, String value, String flags, Integer max_determinized_states, String rewrite) {
        RegexpQuery query = new RegexpQuery();
        query.getRegexp().put(key, new RegexpParam(value, flags, max_determinized_states, rewrite));
        return query;
    }

    public static RegexpQuery builderQuery(String key, String value) {
        return builderQuery(key, value, "ALL", 10000, "constant_score");
    }


}
