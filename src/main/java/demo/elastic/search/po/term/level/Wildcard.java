package demo.elastic.search.po.term.level;

import lombok.Data;

/**
 * <pre>
 * {
 *     "query": {
 *         "wildcard": {
 *             "user": {
 *                 "value": "ki*y",
 *                 "boost": 1.0,
 *                 "rewrite": "constant_score"
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Wildcard {
}
