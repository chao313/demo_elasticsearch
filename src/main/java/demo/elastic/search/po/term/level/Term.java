package demo.elastic.search.po.term.level;

import lombok.Data;

/**
 * <pre>
 * {
 *     "query": {
 *         "term": {
 *             "user": {
 *                 "value": "Kimchy",
 *                 "boost": 1.0
 *             }
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class Term {
}
