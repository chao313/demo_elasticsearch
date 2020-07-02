package demo.elastic.search.po.term.level.base;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.po.Parse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * {
 *     "query": {
 *         "type" : {
 *             "value" : "_doc"
 *         }
 *     }
 * }
 * </pre>
 */
@Data
@Deprecated
public class Type implements Parse {

    @ApiModelProperty(example = " ")
    private String value;


    @Override
    public String parse() {
        if (StringUtils.isBlank(value) || StringUtils.isBlank(value.trim())) {
            /**
             * 关键字段为空->返回空字符串
             */
            return "";
        }
        JSONObject exists = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("value", this.getValue());
        exists.put(_type, content);
        return exists.toJSONString();
    }

    public static String _type = "type";
}
