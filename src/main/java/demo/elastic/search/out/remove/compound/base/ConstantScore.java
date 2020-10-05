package demo.elastic.search.out.remove.compound.base;

import lombok.Data;

/**
 * <pre>
 * {
 *     "query": {
 *         "constant_score" : {
 *             "filter" : {
 *                 "term" : { "user" : "kimchy"}
 *             },
 *             "boost" : 1.2
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class ConstantScore {
}
