/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po;

import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.po.compound.Bool;
import lombok.Data;

/**
 * Auto-generated: 2020-06-29 10:56:41
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Query {

    @JSONField(name = "bool")
    private Bool bool;

}