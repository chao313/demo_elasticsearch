package demo.elastic.search.po.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.dsl.DSLQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QueryBaseRequest extends ToRequestBody {

    @ApiModelProperty(example = "0")
    private Integer from;

    @ApiModelProperty(example = "10")
    private Integer size;

    private DSLQuery query;

    public static QueryBaseRequest builderRequest(Integer from, Integer size, DSLQuery query) {
        QueryBaseRequest request = new QueryBaseRequest();
        request.setFrom(from);
        request.setSize(size);
        request.setQuery(query);
        return request;
    }

    public static QueryBaseRequest builderRequest(DSLQuery query) {
        return builderRequest(0, 10, query);
    }

}
