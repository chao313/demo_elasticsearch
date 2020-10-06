package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;


/**
 * explain+Validate 使用
 */
@FeignClient(name = "explainValidate", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface ExplainValidateService {

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/search-explain.html"></a>
     * 解释为什么匹配不上
     */
    @RequestMapping(value = "/{index}/_explain/{id}", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _explain(@PathVariable(value = "index") String index,
                    @PathVariable(value = "id") String id,
                    @RequestBody String body);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/search-validate.html"></a>
     * 验证搜索的代价
     */
    @RequestMapping(value = "/{index}/_validate/query", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _validate(@PathVariable(value = "index") String index,
                     @ApiParam(defaultValue = "false", value = "如果为true，则对所有分片执行验证，而不是对每个索引随机分配一个分片。默认为false")
                     @RequestParam(value = "all_shards", required = false) Boolean all_shards,
                     @ApiParam(defaultValue = "false", value = "如果为true，则在发生错误时，响应将返回详细信息。默认为false")
                     @RequestParam(value = "explain") Boolean explain,
                     @ApiParam(defaultValue = "false", value = "如果为true，则返回更详细的说明，显示将执行的实际Lucene查询。默认为false")
                     @RequestParam(value = "rewrite") Boolean rewrite,
                     @RequestBody String body);


}
