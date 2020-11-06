package demo.elastic.search.po.request.script;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Query;
import demo.elastic.search.po.request.ToRequestBody;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 专门用于 Script查询的语句
 *
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-scripting.html"></a>
 *
 * <a href="https://www.elastic.co/guide/en/elasticsearch/painless/7.9/painless-walkthrough.html"></a>
 *
 *
 * <pre>
 *  {
 *   "query": {
 *     "bool": {
 *       "must": {
 *         "script": {
 *           "script": {
 *             "source": "doc['balance'].value >= doc['age'].value"
 *           }
 *         }
 *       }
 *     }
 *   }
 * }
 * </pre>
 * <a href="https://elasticsearch.cn/question/3587"></a>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@AllArgsConstructor
@NoArgsConstructor
public class ScriptQuery extends ToRequestBody implements Query {


    private ScriptParam script = new ScriptParam();


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ScriptParam {
        private Lang lang = Lang.painless;
        private String source;
        private Map<String, String> params;
    }

    /**
     * 构建 request 请求
     */
    public static ScriptQuery builderQuery(Lang lang, String source, Map<String, String> params) {
        ScriptQuery scriptQuery = new ScriptQuery();
        ScriptParam scriptParam = new ScriptQuery.ScriptParam(lang, source, params);
        scriptQuery.setScript(scriptParam);
        return scriptQuery;
    }

    /**
     * 构建 request 请求
     */
    public static ScriptQuery builderQuery(String source, Map<String, String> params) {
        ScriptQuery scriptQuery = new ScriptQuery();
        ScriptParam scriptParam = new ScriptQuery.ScriptParam(Lang.painless, source, params);
        scriptQuery.setScript(scriptParam);
        return scriptQuery;
    }

    /**
     * 构建 request 请求
     */
    public static ScriptQuery builderQuery(String source) {
        ScriptQuery scriptQuery = new ScriptQuery();
        ScriptParam scriptParam = new ScriptQuery.ScriptParam(Lang.painless, source, null);
        scriptQuery.setScript(scriptParam);
        return scriptQuery;
    }

    public enum Lang {
        painless("painless"),
        expression("expression"),
        mustache("mustache"),
        java("java");
        private String lang;

        Lang(String lang) {
            this.lang = lang;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public static Lang getLangByLang(String lang) {
            for (Lang vo : Lang.values()) {
                if (vo.getLang().equals(lang)) {
                    return vo;
                }
            }
            return null;
        }
    }


}