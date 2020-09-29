package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * index的相关操作
 */
@FeignClient(name = "AnalyzeService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface AnalyzeService {

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-analyze.html"></a>
     * 分词器(有多种情况，这里暂未写全)
     * <pre>
     * {
     *   "analyzer" : "standard",
     *   "text" : "this is a test"
     * }
     * </pre>
     */
    @RequestMapping(value = "/_analyze", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _analyze(@RequestBody String body);

}
