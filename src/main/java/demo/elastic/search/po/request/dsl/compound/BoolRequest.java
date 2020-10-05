package demo.elastic.search.po.request.dsl.compound;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import demo.elastic.search.po.request.dsl.DSLRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoolRequest extends ToRequestBody implements DSLRequest {

    private BoolQuery query = new BoolQuery();


    @Data
    public static class BoolQuery implements DSLQuery {
        private WrapQuery bool = new WrapQuery();
    }

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

    /**
     * 构建 request 请求
     */
    public static BoolRequest builderRequest(List<DSLQuery> must, List<DSLQuery> must_not, List<DSLQuery> should, List<DSLQuery> filter) {
        BoolRequest request = new BoolRequest();
        request.getQuery().getBool().setMust(must);
        request.getQuery().getBool().setMust_not(must_not);
        request.getQuery().getBool().setShould(should);
        request.getQuery().getBool().setFilter(filter);
        return request;
    }

    public static BoolQuery builderQuery(List<DSLQuery> must, List<DSLQuery> must_not, List<DSLQuery> should, List<DSLQuery> filter) {
        return builderRequest(must, must_not, should, filter).getQuery();
    }

    public static BoolQuery builderQuery() {
        return new BoolQuery();
    }

    /**
     * No {@code null} value allowed.
     */
    public BoolRequest must(DSLQuery dslQuery) {
        if (dslQuery == null) {
            throw new IllegalArgumentException("inner bool query clause cannot be null");
        }
        this.getQuery().getBool().getMust().add(dslQuery);
        return this;
    }

    /**
     * No {@code null} value allowed.
     */
    public BoolRequest must_not(DSLQuery dslQuery) {
        if (dslQuery == null) {
            throw new IllegalArgumentException("inner bool query clause cannot be null");
        }
        this.getQuery().getBool().getMust_not().add(dslQuery);
        return this;
    }

    /**
     * No {@code null} value allowed.
     */
    public BoolRequest filter(DSLQuery dslQuery) {
        if (dslQuery == null) {
            throw new IllegalArgumentException("inner bool query clause cannot be null");
        }
        this.getQuery().getBool().getFilter().add(dslQuery);
        return this;
    }


    /**
     * No {@code null} value allowed.
     */
    public BoolRequest should(DSLQuery dslQuery) {
        if (dslQuery == null) {
            throw new IllegalArgumentException("inner bool query clause cannot be null");
        }
        this.getQuery().getBool().getShould().add(dslQuery);
        return this;
    }

    /**
     * 设置should至少匹配
     */
    public BoolRequest minimumShouldMatch(String minimumShouldMatch) {
        this.getQuery().getBool().minimum_should_match = minimumShouldMatch;
        return this;
    }

    /**
     * 设置should至少匹配
     */
    public BoolRequest minimumShouldMatch(Integer minimumShouldMatch) {
        this.getQuery().getBool().minimum_should_match = String.valueOf(minimumShouldMatch);
        return this;
    }

}
