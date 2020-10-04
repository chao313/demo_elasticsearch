package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.dsl.full.*;
import demo.elastic.search.po.request.lucene.LuceneRequest;
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
    String _search(@PathVariable(value = "index") String index, @RequestBody LuceneRequest luceneRequest);


}
