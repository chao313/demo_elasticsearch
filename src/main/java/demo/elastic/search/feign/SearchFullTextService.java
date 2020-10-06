package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.dsl.full.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 全文检索使用
 */
@FeignClient(name = "SearchFullTextService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SearchFullTextService {

    /**
     * 全文检索(单字段)
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/query-dsl-match-query.html"></a>
     * <pre>
     * {
     *   "query": {
     *     "match": {
     *       "message": {
     *         "query": "this is a test"
     *       }
     *     }
     *   }
     * }
     * </pre>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<MatchQuery> matchRequest);


    /**
     * 全文检索 bool 前缀
     * <pre>
     * {
     *   "query": {
     *     "match_bool_prefix" : {
     *       "message" : "quick brown f"
     *     }
     *   }
     * }
     * </pre>
     * 等同于
     * <pre>
     * {
     *   "query": {
     *     "bool" : {
     *       "should": [
     *         { "term": { "message": "quick" }},
     *         { "term": { "message": "brown" }},
     *         { "prefix": { "message": "f"}}
     *       ]
     *     }
     *   }
     * }
     * </pre>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_bool_prefix_search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<MatchBoolPrefixQuery> matchBoolPrefixRequest);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/query-dsl-match-query-phrase.html"></a>
     * 匹配短语
     * <pre>
     * {
     *   "query": {
     *     "match_phrase": {
     *       "message": "this is a test"
     *     }
     *   }
     * }
     * </pre>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_phrase_search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<MatchPhraseQuery> matchPhraseRequest);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/query-dsl-match-query-phrase-prefix.html"></a>
     * 匹配短语+前缀
     * -->
     * quick brown f 将匹配message的值quick brown fox或two quick , brown ferrets
     * 不匹配 the fox is quick and brown。
     * <pre>
     * {
     *   "query": {
     *     "match_phrase_prefix": {
     *       "message": {
     *         "query": "quick brown f"
     *       }
     *     }
     *   }
     * }
     * </pre>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_phrase_prefix_search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<MatchPhrasePrefixQuery> matchPhrasePrefixRequest);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/query-dsl-multi-match-query.html"></a>
     * <pre>
     * {
     *   "query": {
     *     "multi_match" : {
     *       "query":    "this is a test",
     *       "fields": [ "subject", "message" ]
     *     }
     *   }
     * }
     * </pre>
     * FULL multi match 搜索
     * 多字段查询
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_multi_match_search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<MultiMatchQuery> multiMatchRequest);


}
