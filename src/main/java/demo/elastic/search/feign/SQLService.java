package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.sql.SqlRequest;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/xpack-sql.html"></a>
 */
@FeignClient(name = "SQLService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SQLService {

    /**
     * {
     * "query": "SELECT * FROM library WHERE release_date < '2000-01-01'"
     * }
     *
     * @param format 格式方式
     *               csv
     *               json
     *               tsv
     *               txt
     *               yaml
     *               cbor
     *               smile
     */
    @RequestMapping(value = "/_sql", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _sql(@ApiParam(value = "格式化样式") @RequestParam(value = "format") String format,
                @RequestBody SqlRequest body);

    /**
     * {
     * "query": "SELECT * FROM library WHERE release_date < '2000-01-01'"
     * }
     */
    @RequestMapping(value = "/_sql/translate", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _sql_translate(@RequestBody SqlRequest body);

    /**
     * {
     * "query": "SELECT * FROM library WHERE release_date < '2000-01-01'"
     * }
     */
    @RequestMapping(value = "/_sql/close", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _sql_close(@RequestBody SqlRequest body);


}
