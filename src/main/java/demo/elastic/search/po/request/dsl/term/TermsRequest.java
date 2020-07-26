package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * {
 *   "query": {
 *     "terms": {
 *       "user": [ "kimchy", "elasticsearch" ],
 *       "boost": 1.0
 *     }
 *   }
 * }
 * </pre>
 * 注意:这里暂时直接移除了 boost -> 不同的类型存放在同一个
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TermsRequest {

    private TermsQuery query = new TermsQuery();

    @Data
    public static class TermsQuery {
        private Map<String, List<String>> terms = new HashMap<>();
    }


//    @Data
//    public static class TermsQuery {
//        private TermsMap<String, List<String>> terms = new TermsMap<>();
//
//    }
//
//    public static class TermsMap<K, V> extends LinkedHashMap<K, V> {
//        @ApiModelProperty(example = "1.0")
//        String boost;
//    }

}
