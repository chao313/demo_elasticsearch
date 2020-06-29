package demo.elastic.search.po.term.level;

import lombok.Data;

/**
 * <pre>
 * {
 *     "query" : {
 *         "terms" : {
 *             "user" : ["kimchy", "elasticsearch"],
 *             "boost" : 1.0
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Terms {
}
