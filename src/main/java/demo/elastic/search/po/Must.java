package demo.elastic.search.po;


import demo.elastic.search.po.term.level.*;
import lombok.Data;

@Data
public class Must {
    private Exists exists;
    private Fuzzy fuzzy;
    private IDs ids;
    private Prefix prefix;
    private Range range;
    private Regexp regexp;
    private Term term;
    private Terms terms;
    private Type type;
    private Wildcard wildcard;
}