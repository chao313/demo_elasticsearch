package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Query;
import demo.elastic.search.po.request.ToRequestBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExistsQuery extends ToRequestBody implements Query {

    private ExistsParam exists = new ExistsParam();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ExistsParam {

        @ApiModelProperty(example = " ")
        private String field;

    }

    public static ExistsQuery builderQuery(String field) {
        ExistsQuery query = new ExistsQuery();
        query.getExists().setField(field);
        return query;
    }
}
