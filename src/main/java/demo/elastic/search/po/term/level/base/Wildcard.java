package demo.elastic.search.po.term.level.base;

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
 *         "wildcard": {
 *             "user": {
 *                 "value": "ki*y",
 *                 "boost": 1.0,
 *                 "rewrite": "constant_score"
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Wildcard implements Parse {

    @ApiModelProperty(example = " ")
    @JSONField(name = "field")
    private String field;

    @ApiModelProperty(example = " ")
    @JSONField(name = "value")
    private String value;


    @ApiModelProperty(example = "1.0")
    @JSONField(name = "boost")
    private String boost;

    @ApiModelProperty(example = "constant_score")
    @JSONField(name = "rewrite")
    private String rewrite;


    @Override
    public String parse() {
        if (StringUtils.isBlank(field) || StringUtils.isBlank(field.trim())) {
            /**
             * 关键字段为空->返回空字符串
             */
            return "";
        }

        JSONObject range = new JSONObject();
        JSONObject key = new JSONObject();
        JSONObject content = new JSONObject();
        if (StringUtils.isNotBlank(this.getValue())) {
            content.put("value", this.getValue());
        }
        if (StringUtils.isNotBlank(this.getRewrite())) {
            content.put("rewrite", this.getRewrite());
        }
        if (StringUtils.isNotBlank(this.getBoost())) {
            content.put("boost", this.getBoost());
        }

        key.put(this.getField(), content);
        range.put(_wildcard, key);
        return range.toJSONString();
    }

    public static String _wildcard = "wildcard";
}

