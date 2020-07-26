package demo.elastic.search.po.request.dsl;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExistsRequest {

    private QueryExists query;

    @Data
    public static class QueryExists {
        private Exists exists;
    }

    @Data
    public static class Exists {

        @ApiModelProperty(example = " ")
        private String field;

    }
}
