package demo.elastic.search.out.remove.compound.level.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.out.remove.compound.Parse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * {
 *     "query": {
 *         "prefix": {
 *             "user": {
 *                 "value": "ki"
 *             }
 *         }
 *     }
 * }
 * </pre>
 */

@Data
public class Prefix implements Parse {

    @ApiModelProperty(example = " ")
    @JSONField(name = "field")
    String field;

    @ApiModelProperty(example = " ")
    @JSONField(name = "value")
    private String value;

    @Override
    public String parse() {
        if (StringUtils.isBlank(value) || StringUtils.isBlank(value.trim())) {
            /**
             * 关键字段为空->返回空字符串
             */
            return "";
        }

        JSONObject prefix = new JSONObject();
        JSONObject key = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("value", this.getValue());
        key.put(this.getField(), content);
        prefix.put(_prefix, key);
        return prefix.toJSONString();
    }

    public static String _prefix = "prefix";
}
