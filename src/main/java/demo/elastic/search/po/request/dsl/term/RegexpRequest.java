package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeName;
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
public class RegexpRequest extends ToRequestBody {

    private RegexpQuery query = new RegexpQuery();

    @Data
    @JsonTypeName("regexp")
    public static class RegexpQuery implements DSLQuery {
        private Map<String, RegexpParam> regexp = new HashMap<>();
    }


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

    public static RegexpRequest builderRequest(String key, String value, String flags, Integer max_determinized_states, String rewrite) {
        RegexpRequest request = new RegexpRequest();
        request.getQuery().getRegexp().put(key, new RegexpParam(value, flags, max_determinized_states, rewrite));
        return request;
    }

    public static RegexpRequest builderRequest(String key, String value) {
        return builderRequest(key, value, "ALL", 10000, "constant_score");
    }

    public static RegexpQuery builderQuery(String key, String value, String flags, Integer max_determinized_states, String rewrite) {
        return builderRequest(key, value, flags, max_determinized_states, rewrite).getQuery();
    }

    public static RegexpQuery builderQuery(String key, String value) {
        return builderRequest(key, value, "ALL", 10000, "constant_score").getQuery();
    }

}
