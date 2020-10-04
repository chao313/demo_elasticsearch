package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.QueryBaseRequest;
import demo.elastic.search.po.request.dsl.compound.BoolRequest;
import demo.elastic.search.po.request.dsl.full.*;
import demo.elastic.search.po.request.dsl.matchall.MatchAllRequest;
import demo.elastic.search.po.request.dsl.term.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * 全文检索使用
 */
@FeignClient(name = "FullTextService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface FullTextService {

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


}
