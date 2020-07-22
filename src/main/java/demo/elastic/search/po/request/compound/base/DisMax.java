package demo.elastic.search.po.request.compound.base;

import lombok.Data;

/**
 * <pre>
 * {
 *     "query": {
 *         "dis_max" : {
 *             "queries" : [
 *                 { "term" : { "title" : "Quick pets" }},
 *                 { "term" : { "body" : "Quick pets" }}
 *             ],
 *             "tie_breaker" : 0.7
 *         }
 *     }
 * }
 * </pre>
 */
@Data
public class DisMax {
}
