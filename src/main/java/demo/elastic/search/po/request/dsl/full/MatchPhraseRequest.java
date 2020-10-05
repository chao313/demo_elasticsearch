package demo.elastic.search.po.request.dsl.full;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import demo.elastic.search.po.request.dsl.DSLRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * {
 *   "query": {
 *     "match_phrase": {
 *       "message": {
 *         "query": "this is a test",
 *         "analyzer": "my_analyzer"
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-match-query-phrase.html"></a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MatchPhraseRequest extends ToRequestBody implements DSLRequest {

    private MatchPhraseQuery query = new MatchPhraseQuery();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchPhraseQuery implements DSLQuery {
        private Map<String, MatchPhraseParam> match_phrase = new HashMap<>();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchPhraseParam {
        private String query;
        private String analyzer;//分词器
    }

    public static MatchPhraseRequest builderRequest(String field, String query, String analyzer) {
        MatchPhraseRequest request = new MatchPhraseRequest();
        request.getQuery().getMatch_phrase().put(field, new MatchPhraseParam(query, analyzer));
        return request;
    }

    public static MatchPhraseRequest builderRequest(String field, String query) {
        return builderRequest(field, query, null);
    }

    public static MatchPhraseQuery builderQuery(String field, String query, String analyzer) {
        return builderRequest(field, query, analyzer).getQuery();
    }

    public static MatchPhraseQuery builderQuery(String field, String query) {
        return builderRequest(field, query).getQuery();
    }
}
