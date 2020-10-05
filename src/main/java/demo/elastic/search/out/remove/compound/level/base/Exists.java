/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.out.remove.compound.level.base;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.out.remove.compound.Parse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * <pre>
 * {
 *     "query": {
 *         "exists": {
 *             "field": "user"
 *         }
 *     }
 * }
 * </pre>
 */

@Data
public class Exists implements Parse {

    @ApiModelProperty(example = " ")
    private String field;


    @Override
    public String parse() {
        if (StringUtils.isBlank(field) || StringUtils.isBlank(field.trim())) {
            /**
             * 关键字段为空->返回空字符串
             */
            return "";
        }
        JSONObject exists = new JSONObject();
        JSONObject content = new JSONObject();
        content.put("field", this.getField());
        exists.put(_exists, content);
        return exists.toJSONString();
    }

    public static String _exists = "exists";
}