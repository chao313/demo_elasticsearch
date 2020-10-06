package demo.elastic.search.po.request.dsl.full;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * {
 *   "query": {
 *     "match_bool_prefix": {
 *       "message": {
 *         "query": "quick brown f",
 *         "analyzer": "keyword"
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-match-bool-prefix-query.html"></a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MatchBoolPrefixQuery extends ToRequestBody implements DSLQuery {

    private Map<String, MatchBoolPrefixParam> match_bool_prefix = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchBoolPrefixParam {
        private String query;
        private String analyzer;
    }

    public static MatchBoolPrefixQuery builderQuery(String field, String query, String analyzer) {
        MatchBoolPrefixQuery request = new MatchBoolPrefixQuery();
        request.getMatch_bool_prefix().put(field, new MatchBoolPrefixParam(query, analyzer));
        return request;
    }

    public static MatchBoolPrefixQuery builderQuery(String field, String query) {
        return builderQuery(field, query, null);
    }

}
