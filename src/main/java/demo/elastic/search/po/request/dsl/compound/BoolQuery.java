package demo.elastic.search.po.request.dsl.compound;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoolQuery extends ToRequestBody implements DSLQuery {

    private WrapQuery bool = new WrapQuery();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class WrapQuery {
        List<DSLQuery> must = new ArrayList<>();
        List<DSLQuery> must_not = new ArrayList<>();
        List<DSLQuery> should = new ArrayList<>();
        List<DSLQuery> filter = new ArrayList<>();

        /**
         * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/query-dsl-minimum-should-match.html"></a>
         * should的最少匹配
         * 1. 数字 1
         * 2. 百分比 100%
         * 3. -2 should减去这个数字
         * ...
         */
        private String minimum_should_match;
    }


    public static BoolQuery builderQuery() {
        return new BoolQuery();
    }

    /**
     * No {@code null} value allowed.
     */
    public BoolQuery must(DSLQuery dslQuery) {
        if (dslQuery == null) {
            throw new IllegalArgumentException("inner bool query clause cannot be null");
        }
        this.getBool().getMust().add(dslQuery);
        return this;
    }

    /**
     * No {@code null} value allowed.
     */
    public BoolQuery must_not(DSLQuery dslQuery) {
        if (dslQuery == null) {
            throw new IllegalArgumentException("inner bool query clause cannot be null");
        }
        this.getBool().getMust_not().add(dslQuery);
        return this;
    }

    /**
     * No {@code null} value allowed.
     */
    public BoolQuery filter(DSLQuery dslQuery) {
        if (dslQuery == null) {
            throw new IllegalArgumentException("inner bool query clause cannot be null");
        }
        this.getBool().getFilter().add(dslQuery);
        return this;
    }


    /**
     * No {@code null} value allowed.
     */
    public BoolQuery should(DSLQuery dslQuery) {
        if (dslQuery == null) {
            throw new IllegalArgumentException("inner bool query clause cannot be null");
        }
        this.getBool().getShould().add(dslQuery);
        return this;
    }

    /**
     * 设置should至少匹配
     */
    public BoolQuery minimumShouldMatch(String minimumShouldMatch) {
        this.getBool().minimum_should_match = minimumShouldMatch;
        return this;
    }

    /**
     * 设置should至少匹配
     */
    public BoolQuery minimumShouldMatch(Integer minimumShouldMatch) {
        this.getBool().minimum_should_match = String.valueOf(minimumShouldMatch);
        return this;
    }

}
