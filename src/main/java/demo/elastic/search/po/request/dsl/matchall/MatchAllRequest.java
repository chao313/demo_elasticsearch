package demo.elastic.search.po.request.dsl.matchall;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MatchAllRequest extends ToRequestBody {

    private MatchAllQuery query = new MatchAllQuery();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchAllQuery implements DSLQuery {
        private MatchAllParam match_all = new MatchAllParam();
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchAllParam {

        @ApiModelProperty(example = "1.0")
        private Double boost;

    }

    public static MatchAllRequest builderRequest(Double boost) {
        MatchAllRequest request = new MatchAllRequest();
        request.getQuery().getMatch_all().setBoost(boost);
        return request;
    }

    public static MatchAllRequest builderRequest() {
        MatchAllRequest request = new MatchAllRequest();
        return request;
    }

    public static MatchAllQuery builderQuery() {
        return builderRequest().getQuery();
    }

    public static MatchAllQuery builderQuery(Double boost) {
        return builderRequest(boost).getQuery();
    }
}
