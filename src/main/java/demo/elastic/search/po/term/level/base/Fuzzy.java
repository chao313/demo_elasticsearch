package demo.elastic.search.po.term.level.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.po.Parse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 *  "fuzzy":
 *  {
 *   "user": {
 *       "value": "ki",
 *       "fuzziness": "AUTO",
 *       "max_expansions": 50,
 *       "prefix_length": 0,
 *       "transpositions": true,
 *       "rewrite": "constant_score"
 *   }
 *  }
 * </pre>
 */
@Data
public class Fuzzy implements Parse {

    @ApiModelProperty(example = " ")
    @JSONField(name = "field")
    String field;

    @ApiModelProperty(example = " ")
    @JSONField(name = "value")
    String value;

    @ApiModelProperty(example = "AUTO")
    @JSONField(name = "fuzziness")
    String fuzziness;

    @ApiModelProperty(example = "50")
    @JSONField(name = "max_expansions")
    Integer max_expansions;

    @ApiModelProperty(example = "0")
    @JSONField(name = "prefix_length")
    Integer prefixLength;

    @ApiModelProperty(example = "true")
    @JSONField(name = "transpositions")
    Boolean transpositions;

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
        if (StringUtils.isNotBlank(this.getFuzziness())) {
            content.put(_fuzziness, this.getFuzziness());
        }
        if (null != this.getMax_expansions()) {
            content.put(_max_expansions, this.getMax_expansions());
        }
        if (null != this.getPrefixLength()) {
            content.put(_prefix_length, this.getPrefixLength());
        }
        if (null != this.getTranspositions()) {
            content.put(_transpositions, this.getTranspositions());
        }
        if (StringUtils.isNotBlank(this.getRewrite())) {
            content.put(_rewrite, this.getRewrite());
        }

        key.put(this.getField(), content);
        regexp.put(_fuzzy, key);
        return regexp.toJSONString();
    }

    public static String _value = "value";
    public static String _fuzziness = "fuzziness";
    public static String _max_expansions = "max_expansions";
    public static String _prefix_length = "prefix_length";
    public static String _transpositions = "transpositions";
    public static String _rewrite = "rewrite";

    public static String _fuzzy = "fuzzy";

}
