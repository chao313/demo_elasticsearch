package demo.elastic.search.po.term.level;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.po.Parse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * {
 *     "query": {
 *         "regexp": {
 *             "user": {
 *                 "value": "k.*y",
 *                   "flags" : "ALL",
 *                   "max_determinized_states": 10000,
 *                   "rewrite": "constant_score"
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Regexp implements Parse {

    @JSONField(name = "field")
    String field;
    @JSONField(name = "value")
    String value;
    @JSONField(name = "flags")
    String flags;
    @JSONField(name = "max_determinized_states")
    String maxDeterminizedStates;
    @JSONField(name = "rewrite")
    String rewrite;

    @Override
    public String parse() {
        if (StringUtils.isBlank(value)) {
            /**
             * 关键字段为空->返回空字符串
             */
            return "";
        }
        JSONObject term = new JSONObject();
        JSONObject key = new JSONObject();
        JSONObject content = new JSONObject();
        content.put(_value, this.getValue());
        content.put(_flags, this.getFlags());
        content.put(_max_determinized_states, this.getMaxDeterminizedStates());
        content.put(_rewrite, this.getRewrite());
        key.put(this.getField(), content);
        term.put(_regexp, key);
        return term.toJSONString();
    }

    public static String _regexp = "regexp";
    public static String _value = "value";
    public static String _flags = "flags";
    public static String _max_determinized_states = "max_determinized_states";
    public static String _rewrite = "rewrite";
}

