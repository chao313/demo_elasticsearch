package demo.elastic.search.po.term.level;


import lombok.Data;

import java.util.List;

@Data
public class TermLevel {

    private List<Term> terms;

    private List<Exists> exists;

//    private Node node;

    //    private List<Fuzzy> fuzzy;
//    private List<IDs> ids;
//    private List<Prefix> prefix;
//    private List<Range> range;
//    private List<Regexp> regexp;
//    private List<Terms> terms;
//    private List<Type> type;
//    private List<Wildcard> wildcard;
}