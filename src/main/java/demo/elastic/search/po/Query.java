/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po;

import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.po.compound.*;
import lombok.Data;


@Data
public class Query {

    @JSONField(name = "bool")
    private Bool bool;

//    private Boosting boosting;
//    private ConstantScore constantScore;
//    private DisMax disMax;
//    private FunctionScore functionScore;

}