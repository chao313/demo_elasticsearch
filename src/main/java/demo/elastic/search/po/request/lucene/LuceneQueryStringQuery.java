package demo.elastic.search.po.request.lucene;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.analyze.AnalyzeRequest;
import demo.elastic.search.po.request.dsl.DSLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 专门用于Analyze查询的语句
 *
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/analysis-tokenizers.html"></a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
public class LuceneQueryStringQuery extends ToRequestBody implements DSLQuery {


    private LuceneParam query_string = new LuceneParam();

    /**
     * 还有许多其他的，这里只是部分
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class LuceneParam {
        String query;
        List<String> fields;
        Integer minimum_should_match;
        AnalyzeRequest.Analyzer analyzer;//分词器
        Integer tie_breaker;
        String default_field;//默认字段，如果没有指定字段的默认字段，默认是* -> 全部
        Boolean auto_generate_synonyms_phrase_query;
        String type;
        Boolean analyze_wildcard = true;
    }

    /**
     * 构建 request 请求(这里默认解析通配)
     */
    private static LuceneQueryStringQuery builderQuery(String query, List<String> fields, Integer minimum_should_match, AnalyzeRequest.Analyzer analyzer) {
        LuceneQueryStringQuery luceneQueryStringQuery = new LuceneQueryStringQuery();
        LuceneParam luceneParam = new LuceneParam(query, fields, minimum_should_match, analyzer, null, null, null, null, true);
        luceneQueryStringQuery.setQuery_string(luceneParam);
        return luceneQueryStringQuery;
    }

    public static LuceneQueryStringQuery builderQuery(String query, List<String> fields, Integer minimum_should_match) {
        LuceneQueryStringQuery request = LuceneQueryStringQuery.builderQuery(query, fields, minimum_should_match, null);
        return request;
    }


    public static LuceneQueryStringQuery builderQuery(String query, List<String> fields) {
        LuceneQueryStringQuery request = LuceneQueryStringQuery.builderQuery(query, fields, null, null);
        return request;
    }

    public static LuceneQueryStringQuery builderQuery(String query) {
        LuceneQueryStringQuery request = LuceneQueryStringQuery.builderQuery(query, null, null, null);
        return request;
    }

}