package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "scroll", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface ScrollService {

    @RequestMapping(value = "/_search/scroll", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@RequestParam(value = "scroll") String scroll,
                   @RequestParam(value = "scroll_id") String scroll_id);


}
