package demo.elastic.search.po.request.level.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.po.Parse;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = " ")
    @JSONField(name = "field")
    String field;

    @ApiModelProperty(example = " ")
    @JSONField(name = "value")
    String value;

    @ApiModelProperty(example = "ALL")
    @JSONField(name = "flags")
    String flags;

    @ApiModelProperty(example = "10000")
    @JSONField(name = "max_determinized_states")
    Integer maxDeterminizedStates;

    @ApiModelProperty(example = "constant_score")
    @JSONField(name = "rewrite")
    String rewrite;

    @Override
    public String parse() {
        if (StringUtils.isBlank(value) || StringUtils.isBlank(value.trim())) {
            /**
             * 关键字段为空->返回空字符串
             */
            return "";
        }
        JSONObject regexp = new JSONObject();
        JSONObject key = new JSONObject();
        JSONObject content = new JSONObject();
        if (StringUtils.isNotBlank(this.getValue())) {
            content.put(_value, this.getValue());
        }
        if (StringUtils.isNotBlank(this.getFlags())) {
            content.put(_flags, this.getFlags());
        }
        if (null != this.getMaxDeterminizedStates()) {
            content.put(_max_determinized_states, this.getMaxDeterminizedStates());
        }
        if (StringUtils.isNotBlank(this.getRewrite())) {
            content.put(_rewrite, this.getRewrite());
        }
        key.put(this.getField(), content);
        regexp.put(_regexp, key);
        return regexp.toJSONString();
    }

    public static String _regexp = "regexp";
    public static String _value = "value";
    public static String _flags = "flags";
    public static String _max_determinized_states = "max_determinized_states";
    public static String _rewrite = "rewrite";
}

