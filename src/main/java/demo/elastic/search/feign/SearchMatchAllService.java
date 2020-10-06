package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.matchall.MatchAllQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 匹配全面文档使用
 */
@FeignClient(name = "SearchMatchAllService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SearchMatchAllService {

    /**
     * 全文搜索(匹配全部的文档)
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_all_search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<MatchAllQuery, VoidAggs> matchAllRequest);


}
