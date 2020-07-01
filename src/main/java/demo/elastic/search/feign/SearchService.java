package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "search", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SearchService {

    @RequestMapping(value = "/{index}/{type}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index,
                   @PathVariable(value = "type") String type,
                   @RequestBody String body);

    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index,
                   @RequestBody String body);


}
