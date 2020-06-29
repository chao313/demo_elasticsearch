package demo.elastic.search.po.compound;

import lombok.Data;

@Data
public class Compound {
    private Bool bool;
    private Boosting boosting;
    private ConstantScore constantScore;
    private DisMax disMax;
    private FunctionScore functionScore;
}
