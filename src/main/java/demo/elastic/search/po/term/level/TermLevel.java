package demo.elastic.search.po.term.level;


import lombok.Data;

import java.util.List;

@Data
public class TermLevel {
    private Exists exists;
    private Term term;
//    private List<Fuzzy> fuzzy;
//    private List<IDs> ids;
//    private List<Prefix> prefix;
//    private List<Range> range;
//    private List<Regexp> regexp;
//    private List<Terms> terms;
//    private List<Type> type;
//    private List<Wildcard> wildcard;
}