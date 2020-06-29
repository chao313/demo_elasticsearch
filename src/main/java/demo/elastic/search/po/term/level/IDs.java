package demo.elastic.search.po.term.level;

import lombok.Data;

import java.util.List;

/**
 * <pre>
 * {
 *     "query": {
 *         "ids" : {
 *             "values" : ["1", "4", "100"]
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class IDs {
    List<String> values;
}
