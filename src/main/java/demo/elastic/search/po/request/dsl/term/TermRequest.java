package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
public class TermRequest {

    private TermQuery query = new TermQuery();

    @Data
    public static class TermQuery {
        private Map<String, TermParam> term = new HashMap<>();
    }

    @Data
    public static class TermParam {
        @ApiModelProperty(example = " ")
        String value;
        @ApiModelProperty(example = "1.0")
        String boost;
    }

}
