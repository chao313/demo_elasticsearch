package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import demo.elastic.search.po.request.QueryBaseRequest;
import demo.elastic.search.po.request.dsl.compound.BoolRequest;
import demo.elastic.search.po.request.dsl.full.*;
import demo.elastic.search.po.request.dsl.matchall.MatchAllRequest;
import demo.elastic.search.po.request.dsl.term.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * ES 搜索使用
 */
@FeignClient(name = "search", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SearchService {

    /**
     * 搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index, @RequestBody String body);

    /**
     * scroll搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index,
                   @RequestParam(value = "scroll") String scroll,
                   @RequestBody String body);


    /**
     * 全文搜索(匹配全部的文档)
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_all_search(@PathVariable(value = "index") String index, @RequestBody MatchAllRequest matchAllRequest);

    /**
     * FULL 搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_search(@PathVariable(value = "index") String index, @RequestBody MatchRequest matchRequest);

    /**
     * FULL 搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_phrase_search(@PathVariable(value = "index") String index, @RequestBody MatchPhraseRequest matchPhraseRequest);

    /**
     * FULL 搜索(匹配全部的文档)
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_phrase_prefix_search(@PathVariable(value = "index") String index, @RequestBody MatchPhrasePrefixRequest matchPhrasePrefixRequest);

    /**
     * FULL bool prefix 搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_bool_prefix_search(@PathVariable(value = "index") String index, @RequestBody MatchBoolPrefixRequest matchBoolPrefixRequest);

    /**
     * FULL multi match 搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_multi_match_search(@PathVariable(value = "index") String index, @RequestBody MultiMatchRequest multiMatchRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-exists-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody ExistsRequest existsRequest);


    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody FuzzyRequest fuzzyRequest);


    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-ids-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody IDsRequest iDsRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-prefix-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody PrefixRequest prefixRequest);


    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-range-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody RangeRequest rangeRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-regexp-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody RegexpRequest regexpRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-term-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody TermRequest termRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-terms-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody TermsRequest termsRequest);

    /**
     * DSL搜索
     * 返回包含与通配符模式匹配的术语的文档
     * ?, which matches any single character
     * *, which can match zero or more characters, including an empty one
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-wildcard-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search(@PathVariable(value = "index") String index, @RequestBody WildcardRequest wildcardRequest);

    /**
     * Bool搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_bool_search(@PathVariable(value = "index") String index, @RequestBody BoolRequest boolRequest);

    /**
     * 整体搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index, @RequestBody QueryBaseRequest queryBaseRequest);


}
