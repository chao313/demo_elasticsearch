package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * 查询数量 使用
 */
@FeignClient(name = "CountService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface CountService {

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/search-count.html"></a>
     * 只返回数量
     */
    @RequestMapping(value = "/{index}/_count", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _count(@PathVariable(value = "index") String index,
                    @RequestBody String body);

}
