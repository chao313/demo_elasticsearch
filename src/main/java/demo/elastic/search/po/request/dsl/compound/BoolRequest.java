package demo.elastic.search.po.request.dsl.compound;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import demo.elastic.search.po.request.dsl.term.ExistsRequest;
import demo.elastic.search.po.request.dsl.term.FuzzyRequest;
import demo.elastic.search.po.request.dsl.term.RegexpRequest;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoolRequest {

    private BoolQuery query = new BoolQuery();

    @Data
    public static class BoolQuery {
        private MustQuery bool = new MustQuery();
    }

    @Data
    public static class MustQuery {
        private MustParam must = new MustParam();
    }

    @Data
    public static class MustParam {
        @JsonUnwrapped
        ExistsRequest.ExistsQuery exists = new ExistsRequest.ExistsQuery();
        @JsonUnwrapped
        RegexpRequest.RegexpQuery regexp = new RegexpRequest.RegexpQuery();

//        Map<String, FuzzyRequest.FuzzyParam> fuzzy = new HashMap<>();
    }


}
