package demo.elastic.search.po.request.aggs;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 聚合
 * "max_price" : { "max" : { "field" : "age" } }
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AggsQuery {

    private Map<String, AggsMiddle> aggs = new HashMap<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AggsMiddle {
        private AggsParam max;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class AggsParam {
        private String field;
    }


}