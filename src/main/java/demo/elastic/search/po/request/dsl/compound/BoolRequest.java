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
public class BoolRequest extends ToRequestBody implements DSLQuery {

    private BoolQuery query = new BoolQuery();

    @Data
    public static class BoolQuery implements DSLQuery {
        private MustQuery bool = new MustQuery();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MustQuery {
        List<DSLQuery> must = new ArrayList<>();
        List<DSLQuery> must_not = new ArrayList<>();
        List<DSLQuery> should = new ArrayList<>();
        List<DSLQuery> filter = new ArrayList<>();
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

}
