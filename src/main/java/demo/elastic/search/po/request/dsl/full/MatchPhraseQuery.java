package demo.elastic.search.po.request.dsl.full;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.Query;
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
public class MatchPhraseQuery extends ToRequestBody implements Query {

    private Map<String, MatchPhraseParam> match_phrase = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchPhraseParam {
        private String query;
        private String analyzer;//分词器
    }

    public static MatchPhraseQuery builderQuery(String field, String query, String analyzer) {
        MatchPhraseQuery request = new MatchPhraseQuery();
        request.getMatch_phrase().put(field, new MatchPhraseParam(query, analyzer));
        return request;
    }

    public static MatchPhraseQuery builderQuery(String field, String query) {
        return builderQuery(field, query, null);
    }

}
