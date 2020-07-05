package demo.elastic.search.po.term.level.base;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.po.Parse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * <pre>
 * {
 *     "query": {
 *         "ids" : {
 *             "values" : ["1", "4", "100"]
 *         }
 *     }
 * }
 * </pre>
 */

@Data
public class IDs implements Parse {

    @ApiModelProperty(example = "")
    private List<Object> values = new ArrayList<>();


    @Override
    public String parse() {
        if (null == values || values.size() == 0 || (values.size() == 1 && values.get(0) instanceof LinkedHashMap)) {
            /**
             * 关键字段为空->返回空字符串
             *
             */
            return "";
        }
        JSONObject exists = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("values", this.getValues());
        exists.put(_ids, content);
        return exists.toJSONString();
    }

    public static String _ids = "ids";

}