package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 集群API
 */
@FeignClient(name = "ccr", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface CCRService {

    /**
     * 功能:获取跨集群复制统计信息
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/ccr-get-stats.html"></a>
     */
    @RequestMapping(value = "/_ccr/stats", method = RequestMethod.GET)
    String _ccr_stats();


}
