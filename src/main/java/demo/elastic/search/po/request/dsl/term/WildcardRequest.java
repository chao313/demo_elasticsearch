package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
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
public class WildcardRequest {


    private WildcardQuery query = new WildcardQuery();

    @Data
    public static class WildcardQuery {
        private Map<String, WildcardParam> wildcard = new HashMap<>();
    }

    @Data
    public static class WildcardParam {
        @ApiModelProperty(example = " ")
        private String value;

        @ApiModelProperty(example = "1.0")
        private String boost;

        @ApiModelProperty(example = "constant_score")
        private String rewrite;
    }

}
