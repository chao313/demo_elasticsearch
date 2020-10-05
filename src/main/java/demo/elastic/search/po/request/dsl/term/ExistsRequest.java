package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import demo.elastic.search.po.request.dsl.DSLRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ExistsRequest extends ToRequestBody implements DSLRequest {

    private ExistsQuery query = new ExistsQuery();

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ExistsQuery implements DSLQuery {
        private ExistsParam exists = new ExistsParam();
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class ExistsParam {

        @ApiModelProperty(example = " ")
        private String field;

    }

    /**
     * 构建 request 请求
     */
    public static ExistsRequest builderRequest(String field) {
        ExistsRequest request = new ExistsRequest();
        request.getQuery().getExists().setField(field);
        return request;
    }

    public static ExistsQuery builderQuery(String field) {
        return builderRequest(field).getQuery();
    }
}
