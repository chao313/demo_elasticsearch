package demo.elastic.search.po.request.dsl.term;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExistsRequest {

    private ExistsQuery query;

    @Data
    public static class ExistsQuery {
        private ExistsParam exists;
    }

    @Data
    public static class ExistsParam {

        @ApiModelProperty(example = " ")
        private String field;

    }
}
