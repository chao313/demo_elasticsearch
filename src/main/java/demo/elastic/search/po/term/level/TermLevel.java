package demo.elastic.search.po.term.level;


import demo.elastic.search.po.term.level.base.*;
import lombok.Data;

import java.util.List;

@Data
public class TermLevel {

    private List<Term> term;
    private List<Exists> exists;
    private List<Regexp> regexp;
    private List<Prefix> prefix;
    private List<Range> range;
    private List<Wildcard> wildcard;
    private List<Terms> terms;
    private List<IDs> ids;
    private List<Fuzzy> fuzzy;
//  private List<Type> type;//已经弃用
}