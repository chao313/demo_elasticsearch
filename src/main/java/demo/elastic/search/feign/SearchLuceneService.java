package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.lucene.LuceneQueryStringQuery;
import demo.elastic.search.po.request.lucene.LuceneSimpleQueryStringQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Lucene语法使用
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/query-dsl-query-string-query.html"></a>
 */
@FeignClient(name = "SearchLuceneService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SearchLuceneService {


    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search_query_String(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<LuceneQueryStringQuery> luceneQueryStringRequest);


    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search_simple_query_String(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<LuceneSimpleQueryStringQuery> simpleQueryStringRequest);


}
