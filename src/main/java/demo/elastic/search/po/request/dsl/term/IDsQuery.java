package demo.elastic.search.po.request.dsl.term;

import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Query;
import demo.elastic.search.po.request.ToRequestBody;
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
public class IDsQuery extends ToRequestBody implements Query {

    private IDsParam ids = new IDsParam();

    @Data
    public static class IDsParam {
        private List<String> values = new ArrayList<>();
    }

    public static IDsQuery builderQuery(List<String> values) {
        IDsQuery query = new IDsQuery();
        query.getIds().setValues(values);
        return query;
    }

}
