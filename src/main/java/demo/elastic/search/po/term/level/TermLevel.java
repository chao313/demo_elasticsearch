package demo.elastic.search.po.term.level;


import lombok.Data;

import java.util.List;

@Data
public class TermLevel {

    private List<Term> term;
    private List<Exists> exists;
    private List<Regexp> regexp;
    private List<Prefix> prefix;
//    private List<Fuzzy> fuzzy;
//    private List<IDs> ids;

//    private List<Range> range;

//    private List<Terms> terms;
//    private List<Type> type;
//    private List<Wildcard> wildcard;
}