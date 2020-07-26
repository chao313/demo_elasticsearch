package demo.elastic.search.po.request.dsl;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  {
 *    "query": {
 *      "ids" : {
 *        "values" : ["1", "4", "100"]
 *      }
 *    }
 *  }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IDsRequest {

    private IDsQuery query = new IDsQuery();

    @Data
    public static class IDsQuery {
        private IDsParam ids = new IDsParam();
    }

    @Data
    public static class IDsParam {
        private List<String> values = new ArrayList<>();
    }
}
