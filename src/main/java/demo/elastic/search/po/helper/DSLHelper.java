package demo.elastic.search.po.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DSLHelper {

    private Filter filter;
    private Must must;
    private Must_not must_not;
    private Should should;


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Filter {
        private List<Exists> exists;
        private List<Term> term;
        private List<Terms> terms;
        private List<Range> range;
        private List<Regexp> regexp;
        private List<Prefix> prefix;
        private List<Wildcard> wildcard;
        private Ids ids;
        private List<Fuzzy> fuzzy;


    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Must {
        private List<Exists> exists;
        private List<Term> term;
        private List<Terms> terms;
        private List<Range> range;
        private List<Regexp> regexp;
        private List<Prefix> prefix;
        private List<Wildcard> wildcard;
        private Ids ids;
        private List<Fuzzy> fuzzy;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Must_not {
        private List<Exists> exists;
        private List<Term> term;
        private List<Terms> terms;
        private List<Range> range;
        private List<Regexp> regexp;
        private List<Prefix> prefix;
        private List<Wildcard> wildcard;
        private Ids ids;
        private List<Fuzzy> fuzzy;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Should {
        private List<Exists> exists;
        private List<Term> term;
        private List<Terms> terms;
        private List<Range> range;
        private List<Regexp> regexp;
        private List<Prefix> prefix;
        private List<Wildcard> wildcard;
        private Ids ids;
        private List<Fuzzy> fuzzy;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Term {
        private String field;
        private String value;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Terms {
        private String field;
        private List<String> value;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Wildcard {

        private String field;
        private String value;

    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Exists {
        private String field;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Range {
        private String field;
        private String gte;
        private String lte;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Regexp {
        private String field;
        private String value;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Prefix {
        private String field;
        private String value;
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Fuzzy {
        private String field;
        private String value;
    }


    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Ids {
        private List<String> value;
    }

}
