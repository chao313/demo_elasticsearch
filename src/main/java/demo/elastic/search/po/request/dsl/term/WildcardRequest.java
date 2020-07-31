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
 * 返回包含与通配符模式匹配的术语的文档
 *
 * <pre>
 * {
 *   "query": {
 *     "wildcard": {
 *       "user": {
 *         "value": "ki*y",
 *         "boost": 1.0,
 *         "rewrite": "constant_score"
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-wildcard-query.html"></a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WildcardRequest extends ToRequestBody {


    private WildcardQuery query = new WildcardQuery();

    @Data
    public static class WildcardQuery implements DSLQuery {
        private Map<String, WildcardParam> wildcard = new HashMap<>();
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class WildcardParam {
        @ApiModelProperty(example = " ")
        private String value;
        @ApiModelProperty(example = "1.0")
        private Double boost;
        @ApiModelProperty(example = "constant_score")
        private String rewrite;
    }


    public static WildcardRequest builderRequest(String key, String value, Double boost, String rewrite) {
        WildcardRequest request = new WildcardRequest();
        request.getQuery().getWildcard().put(key, new WildcardParam(value, boost, rewrite));
        return request;
    }

    public static WildcardRequest builderRequest(String key, String value) {
        return builderRequest(key, value, 1.0, "constant_score");
    }

    public static WildcardQuery builderQuery(String key, String value) {
        return builderRequest(key, value, 1.0, "constant_score").getQuery();
    }


    public static WildcardQuery builderQuery(String key, String value, Double boost, String rewrite) {

        return builderRequest(key, value, boost, rewrite).getQuery();
    }

}
