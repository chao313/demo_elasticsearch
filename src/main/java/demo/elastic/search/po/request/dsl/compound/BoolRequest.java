package demo.elastic.search.po.request.dsl.compound;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BoolRequest extends ToRequestBody {

    private BoolQuery query = new BoolQuery();

    @Data
    public static class BoolQuery {
        private MustQuery bool = new MustQuery();
    }

    @Data
    public static class MustQuery {
        //        List<TermRequest.TermQuery> must;
//        TermRequest.TermQuery must;
        List<DSLQuery> must = new ArrayList<>();
    }

    //    public static class MustParam {
//
//        //        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT)
//        ExistsRequest.ExistsQuery exists = new ExistsRequest.ExistsQuery();
////        @JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.WRAPPER_OBJECT)
////        @JsonUnwrapped
////        RegexpRequest.RegexpQuery regexp = new RegexpRequest.RegexpQuery();
//
////        Map<String, FuzzyRequest.FuzzyParam> fuzzy = new HashMap<>();
//    }
//


}
