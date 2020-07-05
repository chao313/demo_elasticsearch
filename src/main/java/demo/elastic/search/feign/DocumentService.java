package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "document", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface DocumentService {


    @RequestMapping(value = "/{index}/{type}/{id}", method = RequestMethod.PUT, headers = {"content-type=application/json"})
    String add(@PathVariable(value = "index") String index,
               @PathVariable(value = "type") String type,
               @PathVariable(value = "id") String id,
               @RequestBody String body);

    @RequestMapping(value = "/{index}/{type}/{id}", method = RequestMethod.GET)
    String get(@PathVariable(value = "index") String index,
               @PathVariable(value = "type") String type,
               @PathVariable(value = "id") String id);

    @RequestMapping(value = "/{index}/{type}/{id}", method = RequestMethod.DELETE)
    String del(@PathVariable(value = "index") String index,
               @PathVariable(value = "type") String type,
               @PathVariable(value = "id") String id);

    /**
     * 批量插入
     */
    @RequestMapping(value = "/{index}/_bulk", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _bulk(@PathVariable(value = "index") String index,
                 @RequestBody String body);
}
