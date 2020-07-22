package demo.elastic.search.po.request.level.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.po.Parse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 *  {
 *     "query": {
 *         "range" : {
 *             "age" : {
 *                 "gte" : 10,
 *                 "lte" : 20,
 *                 "boost" : 2.0
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Range implements Parse {

    @ApiModelProperty(example = " ")
    @JSONField(name = "field")
    String field;

    @ApiModelProperty(example = " ")
    @JSONField(name = "gte")
    private String gte;

    @ApiModelProperty(example = " ")
    @JSONField(name = "lte")
    private String lte;

    @ApiModelProperty(example = "2.0")
    @JSONField(name = "boost")
    private String boost;


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
        if (StringUtils.isNotBlank(this.getGte())) {
            content.put("gte", this.getGte());
        }
        if (StringUtils.isNotBlank(this.getLte())) {
            content.put("lte", this.getLte());
        }
        if (StringUtils.isNotBlank(this.getBoost())) {
            content.put("boost", this.getBoost());
        }


        key.put(this.getField(), content);
        range.put(_range, key);
        return range.toJSONString();
    }

    public static String _range = "range";
}
