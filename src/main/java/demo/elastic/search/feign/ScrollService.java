package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * ES scroll 滚动查询
 */
@FeignClient(name = "scroll", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface ScrollService {

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/scroll-api.html"></a>
     */
    @RequestMapping(value = "/_search/scroll", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)") @RequestParam(value = "scroll") String scroll,
                   @ApiParam(name = "scroll_id", value = "有效的scroll_id") @RequestParam(value = "scroll_id") String scroll_id);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/clear-scroll-api.html"></a>
     */
    @RequestMapping(value = "/_search/scroll", method = RequestMethod.DELETE, headers = {"content-type=application/json"})
    String _search(@RequestParam(value = "scroll_id") String scroll_id);


}
