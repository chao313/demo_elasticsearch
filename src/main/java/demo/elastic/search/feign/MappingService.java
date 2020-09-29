package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(name = "mapping", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface MappingService {


    /**
     * 查看指定index的全部的mapping
     */
    @RequestMapping(value = "/{index}/_mapping", method = RequestMethod.GET)
    String get(@PathVariable(value = "index") String index);

    /**
     * 查看指定index的指定field的mapping
     */
    @RequestMapping(value = "/{index}/_mapping/field/{fieldName}", method = RequestMethod.GET)
    String get(@PathVariable(value = "index") String index,
               @PathVariable(value = "fieldName") String fieldName
    );

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/indices-put-mapping.html"></a>
     * 添加新的字段
     * <pre>
     *  "properties": {
     *     "name": {
     *       "properties": {
     *         "last": {
     *           "type": "text"
     *         }
     *       }
     *     }
     *   }
     * </pre>
     */
    @RequestMapping(value = "/{index}/_mapping", method = RequestMethod.PUT, headers = {"content-type=application/json"})
    String put(@PathVariable(value = "index") String index,
               @RequestBody String body);

    /**
     * https://www.elastic.co/guide/en/elasticsearch/reference/2.3/indices-put-mapping.html
     * 添加新的字段
     * <pre>
     *  {
     *    "properties": {
     *      "email": {
     *        "type": "string"
     *      }
     *    }
     *  }
     * </pre>
     */
    @RequestMapping(value = "/{index}/_mapping/{type}", method = RequestMethod.PUT, headers = {"content-type=application/json"})
    String put(@PathVariable(value = "index") String index,
               @PathVariable(value = "type") String type,
               @RequestBody String body);


}
