package demo.elastic.search.po.term.level;

import lombok.Data;

/**
 * <pre>
 *  {
 *     "query": {
 *         "range" : {
 *             "age" : {
 *                 "gte" : 10,
 *                 "lte" : 20,
 *                 "boost" : 2.0
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Range {
}
