package demo.elastic.search.out.remove.compound;

import demo.elastic.search.out.remove.compound.base.*;
import lombok.Data;

@Data
public class Compound {
    private Bool bool;
    private Boosting boosting;
    private ConstantScore constantScore;
    private DisMax disMax;
    private FunctionScore functionScore;
}
