package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.ToRequestBody;
import demo.elastic.search.po.request.dsl.DSLQuery;
import demo.elastic.search.po.request.dsl.DSLRequest;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
public class IDsRequest extends ToRequestBody implements DSLRequest {

    private IDsQuery query = new IDsQuery();

    @Data
    public static class IDsQuery implements DSLQuery {
        private IDsParam ids = new IDsParam();
    }

    @Data
    public static class IDsParam {
        private List<String> values = new ArrayList<>();
    }

    public static IDsRequest builderRequest(List<String> values) {
        IDsRequest request = new IDsRequest();
        request.getQuery().getIds().setValues(values);
        return request;
    }

    public static IDsQuery builderQuery(List<String> values) {
        return builderRequest(values).getQuery();
    }


}
