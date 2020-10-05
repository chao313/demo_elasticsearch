package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExistsQuery extends ToRequestBody implements DSLQuery {

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
