package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
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
public class TermsQuery extends ToRequestBody implements DSLQuery {

    private Map<String, List<String>> terms = new HashMap<>();

    /**
     * 构建 request 请求
     */
    public static TermsQuery builderQuery(String key, List<String> values) {
        TermsQuery query = new TermsQuery();
        query.getTerms().put(key, values);
        return query;
    }

}
