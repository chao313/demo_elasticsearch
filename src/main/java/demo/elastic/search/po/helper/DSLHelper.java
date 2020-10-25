package demo.elastic.search.po.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DSLHelper {

    private Filter filter = new Filter();
    private Must must = new Must();
    private Must_not must_not = new Must_not();
    private Should should = new Should();

    public DSLHelper filter(Exists exists) {
        this.filter.exists.add(exists);
        return this;
    }

    public DSLHelper filter(Term term) {
        this.filter.term.add(term);
        return this;
    }

    public DSLHelper filter(Terms terms) {
        this.filter.terms.add(terms);
        return this;
    }

    public DSLHelper filter(Range range) {
        this.filter.range.add(range);
        return this;
    }

    public DSLHelper filter(Regexp regexp) {
        this.filter.regexp.add(regexp);
        return this;
    }

    public DSLHelper filter(Prefix prefix) {
        this.filter.prefix.add(prefix);
        return this;
    }

    public DSLHelper filter(Wildcard wildcard) {
        this.filter.wildcard.add(wildcard);
        return this;
    }

    public DSLHelper filter(Ids ids) {
        this.filter.ids = ids;
        return this;
    }

    public DSLHelper filter(Fuzzy fuzzy) {
        this.filter.fuzzy.add(fuzzy);
        return this;
    }

    //must

    public DSLHelper must(Exists exists) {
        this.must.exists.add(exists);
        return this;
    }

    public DSLHelper must(Term term) {
        this.must.term.add(term);
        return this;
    }

    public DSLHelper must(Terms terms) {
        this.must.terms.add(terms);
        return this;
    }

    public DSLHelper must(Range range) {
        this.must.range.add(range);
        return this;
    }

    public DSLHelper must(Regexp regexp) {
        this.must.regexp.add(regexp);
        return this;
    }

    public DSLHelper must(Prefix prefix) {
        this.must.prefix.add(prefix);
        return this;
    }

    public DSLHelper must(Wildcard wildcard) {
        this.must.wildcard.add(wildcard);
        return this;
    }

    public DSLHelper must(Ids ids) {
        this.must.ids = ids;
        return this;
    }

    public DSLHelper must(Fuzzy fuzzy) {
        this.must.fuzzy.add(fuzzy);
        return this;
    }

    //must_not

    public DSLHelper must_not(Exists exists) {
        this.must_not.exists.add(exists);
        return this;
    }

    public DSLHelper must_not(Term term) {
        this.must_not.term.add(term);
        return this;
    }

    public DSLHelper must_not(Terms terms) {
        this.must_not.terms.add(terms);
        return this;
    }

    public DSLHelper must_not(Range range) {
        this.must_not.range.add(range);
        return this;
    }

    public DSLHelper must_not(Regexp regexp) {
        this.must_not.regexp.add(regexp);
        return this;
    }

    public DSLHelper must_not(Prefix prefix) {
        this.must_not.prefix.add(prefix);
        return this;
    }

    public DSLHelper must_not(Wildcard wildcard) {
        this.must_not.wildcard.add(wildcard);
        return this;
    }

    public DSLHelper must_not(Ids ids) {
        this.must_not.ids = ids;
        return this;
    }

    public DSLHelper must_not(Fuzzy fuzzy) {
        this.must_not.fuzzy.add(fuzzy);
        return this;
    }

    //should

    public DSLHelper should(Exists exists) {
        this.should.exists.add(exists);
        return this;
    }

    public DSLHelper should(Term term) {
        this.should.term.add(term);
        return this;
    }

    public DSLHelper should(Terms terms) {
        this.should.terms.add(terms);
        return this;
    }

    public DSLHelper should(Range range) {
        this.should.range.add(range);
        return this;
    }

    public DSLHelper should(Regexp regexp) {
        this.should.regexp.add(regexp);
        return this;
    }

    public DSLHelper should(Prefix prefix) {
        this.should.prefix.add(prefix);
        return this;
    }

    public DSLHelper should(Wildcard wildcard) {
        this.should.wildcard.add(wildcard);
        return this;
    }

    public DSLHelper should(Ids ids) {
        this.should.ids = ids;
        return this;
    }

    public DSLHelper should(Fuzzy fuzzy) {
        this.should.fuzzy.add(fuzzy);
        return this;
    }

    @Data
    public static class Filter {
        private List<Exists> exists = new ArrayList<>();
        private List<Term> term = new ArrayList<>();
        private List<Terms> terms = new ArrayList<>();
        private List<Range> range = new ArrayList<>();
        private List<Regexp> regexp = new ArrayList<>();
        private List<Prefix> prefix = new ArrayList<>();
        private List<Wildcard> wildcard = new ArrayList<>();
        private Ids ids;
        private List<Fuzzy> fuzzy = new ArrayList<>();


    }


    @Data
    public static class Must {
        private List<Exists> exists = new ArrayList<>();
        private List<Term> term = new ArrayList<>();
        private List<Terms> terms = new ArrayList<>();
        private List<Range> range = new ArrayList<>();
        private List<Regexp> regexp = new ArrayList<>();
        private List<Prefix> prefix = new ArrayList<>();
        private List<Wildcard> wildcard = new ArrayList<>();
        private Ids ids;
        private List<Fuzzy> fuzzy = new ArrayList<>();
    }


    @Data
    public static class Must_not {
        private List<Exists> exists = new ArrayList<>();
        private List<Term> term = new ArrayList<>();
        private List<Terms> terms = new ArrayList<>();
        private List<Range> range = new ArrayList<>();
        private List<Regexp> regexp = new ArrayList<>();
        private List<Prefix> prefix = new ArrayList<>();
        private List<Wildcard> wildcard = new ArrayList<>();
        private Ids ids;
        private List<Fuzzy> fuzzy = new ArrayList<>();
    }


    @Data
    public static class Should {
        private List<Exists> exists = new ArrayList<>();
        private List<Term> term = new ArrayList<>();
        private List<Terms> terms = new ArrayList<>();
        private List<Range> range = new ArrayList<>();
        private List<Regexp> regexp = new ArrayList<>();
        private List<Prefix> prefix = new ArrayList<>();
        private List<Wildcard> wildcard = new ArrayList<>();
        private Ids ids;
        private List<Fuzzy> fuzzy = new ArrayList<>();
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
