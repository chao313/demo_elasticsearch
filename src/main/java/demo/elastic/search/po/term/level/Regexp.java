package demo.elastic.search.po.term.level;

import lombok.Data;

/**
 * <pre>
 * {
 *     "query": {
 *         "regexp": {
 *             "user": {
 *                 "value": "k.*y",
 *                 "flags" : "ALL",
 *                 "max_determinized_states": 10000,
 *                 "rewrite": "constant_score"
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Regexp {
}
