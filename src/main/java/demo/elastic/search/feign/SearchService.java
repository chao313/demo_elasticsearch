package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.QueryBaseRequest;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.compound.BoolQuery;
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
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-exists-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_exists(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<ExistsQuery, VoidAggs> existsRequest);


    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-fuzzy-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_fuzzy(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<FuzzyQuery, VoidAggs> fuzzyRequest);


    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-ids-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_ids(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<IDsQuery, VoidAggs> iDsRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-prefix-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_prefix(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<PrefixQuery, VoidAggs> prefixRequest);


    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-range-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_range(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<RangeQuery, VoidAggs> rangeRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-regexp-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_regexp(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<RegexpQuery, VoidAggs> regexpRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-term-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_term(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<TermQuery, VoidAggs> termRequest);

    /**
     * DSL搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-terms-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_terms(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<TermsQuery, VoidAggs> termsRequest);

    /**
     * DSL搜索
     * 返回包含与通配符模式匹配的术语的文档
     * ?, which matches any single character
     * *, which can match zero or more characters, including an empty one
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-wildcard-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_wildcard(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<WildcardQuery, VoidAggs> wildcardRequest);

    /**
     * Bool搜索
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-query.html"></a>
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String DSL_search_bool(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<BoolQuery, VoidAggs> boolRequest);

    /**
     * 整体搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index, @RequestBody QueryBaseRequest queryBaseRequest);

    /**
     * 整体搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder searchSourceBuilder);

    /**
     * 整体搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index,
                   @RequestParam(value = "scroll") String scroll,
                   @RequestBody SearchSourceBuilder searchSourceBuilder);


    /**
     * 返回request将会执行的shard
     */
    @RequestMapping(value = "/{index}/_search_shards", method = RequestMethod.GET)
    String _search_shards(@PathVariable(value = "index") String index);


}
