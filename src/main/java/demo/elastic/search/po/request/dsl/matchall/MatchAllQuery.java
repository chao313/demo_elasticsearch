package demo.elastic.search.po.request.dsl.matchall;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MatchAllQuery extends ToRequestBody implements DSLQuery {

    private MatchAllParam match_all = new MatchAllParam();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class MatchAllParam {

        @ApiModelProperty(example = "1.0")
        private Double boost;

    }

    public static MatchAllQuery builderQuery(Double boost) {
        MatchAllQuery query = new MatchAllQuery();
        query.getMatch_all().setBoost(boost);
        return query;
    }

    public static MatchAllQuery builderQuery() {
        MatchAllQuery query = new MatchAllQuery();
        return query;
    }
}
