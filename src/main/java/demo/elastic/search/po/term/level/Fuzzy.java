package demo.elastic.search.po.term.level;

import lombok.Data;

/**
 * <pre>
 *  "fuzzy":
 *  {
 *   "user": {
 *       "value": "ki",
 *       "fuzziness": "AUTO",
 *       "max_expansions": 50,
 *       "prefix_length": 0,
 *       "transpositions": true,
 *       "rewrite": "constant_score"
 *   }
 *  }
 * </pre>
 */
@Data
public class Fuzzy {
    private String value;// ki
    private String fuzziness;//AUTO
    private Integer max_expansions;//50
    private Integer prefix_length;//0
    private Boolean transpositions;//true
    private String rewrite;//constant_score
}
