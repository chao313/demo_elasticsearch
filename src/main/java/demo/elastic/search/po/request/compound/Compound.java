package demo.elastic.search.po.request.compound;

import demo.elastic.search.po.request.compound.base.*;
import lombok.Data;

@Data
public class Compound {
    private Bool bool;
    private Boosting boosting;
    private ConstantScore constantScore;
    private DisMax disMax;
    private FunctionScore functionScore;
}
