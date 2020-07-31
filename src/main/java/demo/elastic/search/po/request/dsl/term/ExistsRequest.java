package demo.elastic.search.po.request.dsl.term;

import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.compound.DSLQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExistsRequest extends ToRequestBody {

    private ExistsQuery query = new ExistsQuery();

    @Data
    public static class ExistsQuery implements DSLQuery {
        private ExistsParam exists = new ExistsParam();
    }

    @Data
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
