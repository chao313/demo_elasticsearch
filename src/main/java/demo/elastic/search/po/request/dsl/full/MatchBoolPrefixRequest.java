package demo.elastic.search.po.request.dsl.full;

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
public class MatchBoolPrefixRequest extends ToRequestBody {

    private MatchBoolPrefixQuery query = new MatchBoolPrefixQuery();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchBoolPrefixQuery implements DSLQuery {
        private Map<String, MatchBoolPrefixParam> match_bool_prefix = new HashMap<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchBoolPrefixParam {
        private String query;
        private String analyzer;
    }

    public static MatchBoolPrefixRequest builderRequest(String field, String query, String analyzer) {
        MatchBoolPrefixRequest request = new MatchBoolPrefixRequest();
        request.getQuery().getMatch_bool_prefix().put(field, new MatchBoolPrefixParam(query, analyzer));
        return request;
    }

    public static MatchBoolPrefixRequest builderRequest(String field, String query) {
        return builderRequest(field, query, null);
    }

    public static MatchBoolPrefixQuery builderQuery(String field, String query, String analyzer) {
        return builderRequest(field, query, analyzer).getQuery();
    }

    public static MatchBoolPrefixQuery builderQuery(String field, String query) {
        return builderRequest(field, query).getQuery();
    }
}
