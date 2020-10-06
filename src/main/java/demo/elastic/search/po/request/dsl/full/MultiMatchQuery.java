package demo.elastic.search.po.request.dsl.full;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.Query;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <pre>
 * {
 *   "query": {
 *     "multi_match" : {
 *       "query":      "brown fox",
 *       "type":       "best_fields",
 *       "fields":     [ "subject", "message" ],
 *       "tie_breaker": 0.3
 *     }
 *   }
 * }
 * </pre>
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-multi-match-query.html"></a>
 * multi_match查询基于该match查询， 以允许多字段查询
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MultiMatchQuery extends ToRequestBody implements Query {

    private MultiMatchParam multi_match = new MultiMatchParam();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MultiMatchParam {
        private String query;
        private MultiMatchType type;//类型
        /**
         * 1.字段可以用通配符指定 *_name
         * 2.使用^进行增强
         */
        private List<String> fields;//要匹配的字段
        private Double tie_breaker;//修改词条的默认行为
    }

    public static MultiMatchQuery builderQuery(List<String> fields, String query, MultiMatchType type, Double tie_breaker) {
        MultiMatchQuery request = new MultiMatchQuery();
        request.setMulti_match(new MultiMatchParam(query, type, fields, tie_breaker));
        return request;
    }

    public static MultiMatchQuery builderQuery(List<String> fields, String query) {
        return builderQuery(fields, query, MultiMatchType.best_fields, null);
    }

    public enum MultiMatchType {
        best_fields("best_fields"),//查找匹配任何字段但使用_score最佳字段中的文档
        most_fields("most_fields"),//查找与任何字段匹配的文档，并将_score每个字段中的合并
        cross_fields("cross_fields"),//像对待analyzer一个大字段一样对待字段。在任何 字段中查找每个单词
        phrase("phrase"),//match_phrase在每个字段上 运行查询，并使用_score 最佳字段中的
        phrase_prefix("phrase_prefix"),//match_phrase_prefix在每个字段上 运行查询，并使用_score最佳字段中的
        bool_prefix("bool_prefix"),//match_bool_prefix在每个字段上 创建查询，并将_score每个字段中的合并
        ;

        public String getType() {
            return type;
        }

        private String type;

        MultiMatchType(String type) {
            this.type = type;
        }
    }
}