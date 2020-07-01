/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.term.level;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import demo.elastic.search.po.Parse;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Example;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.bind.DefaultValue;

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