package demo.elastic.search.po.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class QueryBaseRequest extends ToRequestBody {

    @ApiModelProperty(example = "0")
    private Integer from;

    @ApiModelProperty(example = "10")
    private Integer size;

    private Query query;

    public static QueryBaseRequest builderRequest(Integer from, Integer size, Query query) {
        QueryBaseRequest request = new QueryBaseRequest();
        request.setFrom(from);
        request.setSize(size);
        request.setQuery(query);
        return request;
    }

    public static QueryBaseRequest builderRequest(Query query) {
        return builderRequest(0, 10, query);
    }

}
