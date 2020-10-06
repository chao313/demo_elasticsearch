package demo.elastic.search.po.request.dsl.full;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Query;
import demo.elastic.search.po.request.ToRequestBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * {
 *   "query": {
 *     "match": {
 *       "message": {
 *         "query": "this is a test",
 *         "operator": "and"
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-match-query.html"></a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MatchQuery extends ToRequestBody implements Query {

    private Map<String, MatchParam> match = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchParam {
        private String query;
        private String analyzer;//分词器
        private Boolean auto_generate_synonyms_phrase_query;//（可选，布尔值）如果为true， 则会自动为多个术语同义词创建匹配词组查询。默认为true
        private String fuzziness;//（可选，字符串）匹配允许的最大编辑距离
        private Integer max_expansions;//（可选，整数）查询将扩展到的最大术语数。默认为50
        private Integer prefix_length;//（可选，整数）保持模糊匹配的起始字符数。默认为0
        private Boolean fuzzy_transpositions;//（可选，布尔值）如果true为，则模糊匹配的编辑内容包括两个相邻字符的转置（ab→ba）。默认为true
        private String fuzzy_rewrite;//（可选，字符串）用于重写查询的方法
        private Boolean lenient;//（可选，布尔值）如果为true， 则会自动为多个术语同义词创建匹配词组查询。默认为true
        private String operator;//（可选，字符串）布尔逻辑，用于解释query值中的文本 默认OR
        private String minimum_should_match;//（可选，字符串）要返回的文档必须匹配的最小子句数
    }

    public static MatchQuery builderQuery(String field, String query, String analyzer, Boolean auto_generate_synonyms_phrase_query, String fuzziness, Integer max_expansions, Integer prefix_length, Boolean fuzzy_transpositions, String fuzzy_rewrite, Boolean lenient, String operator, String minimum_should_match) {
        MatchQuery request = new MatchQuery();
        request.getMatch().put(field, new MatchParam(query, analyzer, auto_generate_synonyms_phrase_query, fuzziness, max_expansions, prefix_length, fuzzy_transpositions, fuzzy_rewrite, lenient, operator, minimum_should_match));
        return request;
    }

    public static MatchQuery builderQuery(String field, String query) {
        return builderQuery(field, query, null, true, null, 50, 0, true, null, true, "OR", null);
    }

}
