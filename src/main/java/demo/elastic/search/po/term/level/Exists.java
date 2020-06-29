/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.term.level;

import lombok.Data;

/**
 * <pre>
 * {
 *     "query": {
 *         "exists": {
 *             "field": "user"
 *         }
 *     }
 * }
 * </pre>
 */

@Data
public class Exists {
    private String field;
}