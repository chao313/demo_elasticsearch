package demo.elastic.search.po.request.dsl.compound;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoolRequest extends ToRequestBody implements DSLQuery {

    private BoolQuery query = new BoolQuery();

    @Data
    public static class BoolQuery {
        private MustQuery bool = new MustQuery();
    }

    @Data
    public static class MustQuery {
        List<DSLQuery> must = new ArrayList<>();
        List<DSLQuery> must_not = new ArrayList<>();
        List<DSLQuery> should = new ArrayList<>();
        List<DSLQuery> filter = new ArrayList<>();
    }

}
