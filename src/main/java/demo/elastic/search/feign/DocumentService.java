package demo.elastic.search.feign;

import demo.elastic.search.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


@FeignClient(name = "document", url = "http://39.107.236.187:9200/", configuration = FeignServiceConfig.class)
//@FeignClient(name = "es", url = "http://127.0.0.1:80/", configuration = FeignServiceConfig.class)
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
     * 匹配插入
     */
    @RequestMapping(value = "/{index}/_bulk", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _bulk(@PathVariable(value = "index") String index,
                 @RequestBody String body);

    @RequestMapping(value = "/{index}/{type}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index,
                   @PathVariable(value = "type") String type,
                   @RequestBody String body);

    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathVariable(value = "index") String index,
                   @RequestBody String body);
}
