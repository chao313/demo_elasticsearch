package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "script", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface ScriptService {


    /**
     * 创建一个 script
     */
    @RequestMapping(value = "/_scripts/{id}", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String add(@PathVariable(value = "id") String id,
               @RequestBody String body);

    /**
     * 查看一个 script
     */
    @RequestMapping(value = "/_scripts/{id}", method = RequestMethod.GET)
    String get(@PathVariable(value = "id") String id);

    /**
     * 删除一个 script
     */
    @RequestMapping(value = "/_scripts/{id}", method = RequestMethod.DELETE)
    String del(@PathVariable(value = "id") String id);


}
