/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.compound;

import demo.elastic.search.po.term.level.TermLevel;
import lombok.Data;

import java.util.List;

/**
 * <pre>
 * {
 *   "query": {
 *     "bool" : {
 *       "must" : {
 *         "term" : { "user" : "kimchy" }
 *       },
 *       "filter": {
 *         "term" : { "tag" : "tech" }
 *       },
 *       "must_not" : {
 *         "range" : {
 *           "age" : { "gte" : 10, "lte" : 20 }
 *         }
 *       },
 *       "should" : [
 *         { "term" : { "tag" : "wow" } },
 *         { "term" : { "tag" : "elasticsearch" } }
 *       ],
 *       "minimum_should_match" : 1,
 *       "boost" : 1.0
 *     }
 *   }
 * }
 * </pre>
 */
@Data
public class Bool {
    private List<TermLevel> must;
//    private TermLevel must_not;
//    private TermLevel should;
//    private TermLevel filter;
}