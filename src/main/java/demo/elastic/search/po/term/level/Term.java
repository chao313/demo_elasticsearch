package demo.elastic.search.po.term.level;

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
 *         "term": {
 *             "user": {
 *                 "value": "Kimchy",
 *                 "boost": 1.0
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Term implements Parse {

    @ApiModelProperty(example = " ")
    @JSONField(name = "field")
    String field;

    @ApiModelProperty(example = " ")
    @JSONField(name = "value")
    String value;

    @ApiModelProperty(example = "1.0")
    @JSONField(name = "boost")
    String boost;

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
        content.put(_boost, this.getBoost());
        key.put(this.getField(), content);
        term.put(_term, key);
        return term.toJSONString();
    }

    public static String _term = "term";
    public static String _value = "value";
    public static String _boost = "boost";
}
