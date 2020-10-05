/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.out.remove.compound;

import com.alibaba.fastjson.annotation.JSONField;
import demo.elastic.search.out.remove.compound.base.Bool;
import lombok.Data;


@Data
public class Query {

    public static String _bool = "bool";
    public static String _boosting = "boosting";
    public static String _constant_score = "constant_score";
    public static String _dis_max = "dis_max";
    public static String _function_score = "function_score";

    @JSONField(name = "bool")
    private Bool bool;
//    @JSONField(name = "boosting")
//    private Boosting boosting;
//    @JSONField(name = "constant_score")
//    private ConstantScore constantScore;
//    @JSONField(name = "dis_max")
//    private DisMax disMax;
//    @JSONField(name = "function_score")
//    private FunctionScore functionScore;

}