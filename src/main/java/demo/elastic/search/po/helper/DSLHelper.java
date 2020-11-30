package demo.elastic.search.po.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.compound.BoolQuery;
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
        private String gt;
        private String lte;
        private String lt;
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


    /**
     * DSL转换成bool检索 抽出代码
     * @param dslHelper
     * @return
     */
    public static BoolQuery DSLHelperToBoolQuery(DSLHelper dslHelper) {
        BoolQuery boolQuery = QueryBuilders.boolQuery();
        SearchSourceBuilder<BoolQuery, VoidAggs> request = new SearchSourceBuilder<>();

        dslHelper.getFilter().getExists().forEach(exists -> {
            boolQuery.filter(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getFilter().getTerm().forEach(term -> {
            boolQuery.filter(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getFilter().getTerms().forEach(terms -> {
            boolQuery.filter(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getFilter().getRange().forEach(range -> {
            boolQuery.filter(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getGt(), range.getLte(), range.getLt()));
        });
        dslHelper.getFilter().getRegexp().forEach(regexp -> {
            boolQuery.filter(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getFilter().getPrefix().forEach(prefix -> {
            boolQuery.filter(QueryBuilders.prefixQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getFilter().getWildcard().forEach(wildcard -> {
            boolQuery.filter(QueryBuilders.wildcardQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getFilter().getFuzzy().forEach(fuzzy -> {
            boolQuery.filter(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getFilter().getIds() && null != dslHelper.getFilter().getIds().getValue() && dslHelper.getFilter().getIds().getValue().size() > 0) {
            boolQuery.filter(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        //must
        dslHelper.getMust().getExists().forEach(exists -> {
            boolQuery.must(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getMust().getTerm().forEach(term -> {
            boolQuery.must(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getMust().getTerms().forEach(terms -> {
            boolQuery.must(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getMust().getRange().forEach(range -> {
            boolQuery.must(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getGt(), range.getLte(), range.getLt()));
        });
        dslHelper.getMust().getRegexp().forEach(regexp -> {
            boolQuery.must(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getMust().getPrefix().forEach(prefix -> {
            boolQuery.must(QueryBuilders.prefixQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getMust().getWildcard().forEach(wildcard -> {
            boolQuery.must(QueryBuilders.wildcardQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getMust().getFuzzy().forEach(fuzzy -> {
            boolQuery.must(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getMust().getIds() && null != dslHelper.getMust().getIds().getValue() && dslHelper.getMust().getIds().getValue().size() > 0) {
            boolQuery.must(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        //must_not
        dslHelper.getMust_not().getExists().forEach(exists -> {
            boolQuery.must_not(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getMust_not().getTerm().forEach(term -> {
            boolQuery.must_not(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getMust_not().getTerms().forEach(terms -> {
            boolQuery.must_not(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getMust_not().getRange().forEach(range -> {
            boolQuery.must_not(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getGt(), range.getLte(), range.getLt()));
        });
        dslHelper.getMust_not().getRegexp().forEach(regexp -> {
            boolQuery.must_not(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getMust_not().getPrefix().forEach(prefix -> {
            boolQuery.must_not(QueryBuilders.prefixQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getMust_not().getWildcard().forEach(wildcard -> {
            boolQuery.must_not(QueryBuilders.wildcardQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getMust_not().getFuzzy().forEach(fuzzy -> {
            boolQuery.must_not(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getMust_not().getIds() && null != dslHelper.getMust_not().getIds().getValue() && dslHelper.getMust_not().getIds().getValue().size() > 0) {
            boolQuery.must_not(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        //should
        dslHelper.getShould().getExists().forEach(exists -> {
            boolQuery.should(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getShould().getTerm().forEach(term -> {
            boolQuery.should(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getShould().getTerms().forEach(terms -> {
            boolQuery.should(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getShould().getRange().forEach(range -> {
            boolQuery.should(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getGt(), range.getLte(), range.getLt()));
        });
        dslHelper.getShould().getRegexp().forEach(regexp -> {
            boolQuery.should(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getShould().getPrefix().forEach(prefix -> {
            boolQuery.should(QueryBuilders.prefixQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getShould().getWildcard().forEach(wildcard -> {
            boolQuery.should(QueryBuilders.wildcardQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getShould().getFuzzy().forEach(fuzzy -> {
            boolQuery.should(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getShould().getIds() && null != dslHelper.getShould().getIds().getValue() && dslHelper.getShould().getIds().getValue().size() > 0) {
            boolQuery.should(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        return boolQuery;
    }
}
