/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.compound.base;

import com.alibaba.fastjson.annotation.JSONField;
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
 *
 * <pre>
 *  curl -X GET  "http://39.107.236.187:9200/bank/_doc/_search?pretty" -H 'Content-Type: application/json' -d'
 *  {
 *     "from": 0,
 *     "size": 1,
 *     "query": {
 *         "bool": {
 *             "must":[
 *              {"exists": {"field": "age"}},
 *              {"exists": {"field": "address"}},
 *              {"term": {"age": {
 *                             "boost": 0,
 *                             "value": 32
 *                         }}
 *              }
 *             ]
 *         }
 *     }
 * }'
 * </pre>
 */
@Data
public class Bool {

    @JSONField(name = "must")
    private TermLevel must;
    @JSONField(name = "must_not")
    private TermLevel must_not;
    @JSONField(name = "should")
    private TermLevel should;
    @JSONField(name = "filter")
    private TermLevel filter;

    public static String _must = "must";
    public static String _must_not = "must_not";
    public static String _should = "should";
    public static String _filter = "filter";
}