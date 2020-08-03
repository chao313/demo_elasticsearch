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
 *     "match_phrase_prefix": {
 *       "message": {
 *         "query": "quick brown f"
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-match-query-phrase-prefix.html#match-phrase-prefix-query-ex-request"></a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MatchPhrasePrefixRequest extends ToRequestBody {

    private MatchPhrasePrefixQuery query = new MatchPhrasePrefixQuery();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchPhrasePrefixQuery implements DSLQuery {
        private Map<String, MatchPhrasePrefixParam> match_phrase_prefix = new HashMap<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchPhrasePrefixParam {
        private String query;
        private String analyzer;//分词器
        private Integer max_expansions;//（可选，整数）该query值的最后提供的项将扩展到的最大项数。默认为50
        private Integer slop;//（可选，整数）匹配令牌之间允许的最大位置数。默认为0。转置字词的斜率为2
    }

    public static MatchPhrasePrefixRequest builderRequest(String field, String query, String analyzer, Integer max_expansions, Integer slop) {
        MatchPhrasePrefixRequest request = new MatchPhrasePrefixRequest();
        request.getQuery().getMatch_phrase_prefix().put(field, new MatchPhrasePrefixParam(query, analyzer, max_expansions, slop));
        return request;
    }

    public static MatchPhrasePrefixRequest builderRequest(String field, String query) {
        return builderRequest(field, query, null, 50, 0);
    }

    public static MatchPhrasePrefixQuery builderQuery(String field, String query, String analyzer, Integer max_expansions, Integer slop) {
        return builderRequest(field, query, analyzer, max_expansions, slop).getQuery();
    }

    public static MatchPhrasePrefixQuery builderQuery(String field, String query) {
        return builderRequest(field, query).getQuery();
    }
}
