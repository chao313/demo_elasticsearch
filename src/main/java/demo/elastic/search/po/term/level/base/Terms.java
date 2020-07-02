package demo.elastic.search.po.term.level.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.po.Parse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.LongStream;

/**
 * <pre>
 * {
 *     "query" : {
 *         "terms" : {
 *             "user" : ["kimchy", "elasticsearch"],
 *             "boost" : 1.0
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Terms implements Parse {

    @ApiModelProperty(example = " ")
    @JSONField(name = "field")
    String field;

    @ApiModelProperty(example = "")
    @JSONField(name = "value")
    List<Object> value;

    @ApiModelProperty(example = "1.0")
    @JSONField(name = "boost")
    String boost;

    @Override
    public String parse() {
        if (null == value || value.size() == 0 || (value.size() == 1 && value.get(0) instanceof LinkedHashMap)) {
            /**
             * 关键字段为空->返回空字符串
             */
            return "";
        }
        JSONObject term = new JSONObject();
        JSONObject key = new JSONObject();
        key.put(this.getField(), this.getValue());
        key.put(_boost, this.getBoost());
        term.put(_terms, key);
        return term.toJSONString();
    }

    public static String _terms = "terms";
    public static String _boost = "boost";
}