package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * index的相关操作
 */
@FeignClient(name = "index", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface IndexService {

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-create-index.html"></a>
     * 您可以使用create index API将新索引添加到Elasticsearch集群。创建索引时，可以指定以下内容：
     * <p>
     * 索引设置
     * 索引中字段的映射
     * 索引别名
     *
     * <index>
     * （必需，字符串）您要创建的索引的名称。
     * <p>
     * 索引名称必须满足以下条件：
     * <p>
     * 仅小写
     * 不能包括\，/，*，?，"，<，>，|，``（空格字符）， ,，#
     * 7.0之前的索引可能包含冒号（:），但已过时，并且在7.0+中不支持
     * 不能以-，_，+
     * 不能为.或..
     * 不能超过255个字节（请注意它是字节，因此多字节字符将更快地计入255个限制）
     * .不建议使用以 开头的名称，但隐藏索引和由插件管理的内部索引 除外
     *
     * body
     * <pre>
     * {
     *     "settings" : {
     *         "number_of_shards" : 1
     *     },
     *     "mappings" : {
     *         "properties" : {
     *             "field1" : { "type" : "text" }
     *         }
     *     },
     *    "aliases" : {
     *         "alias_1" : {},
     *         "alias_2" : {
     *             "filter" : {
     *                 "term" : {"user" : "kimchy" }
     *             },
     *             "routing" : "kimchy"
     *         }
     *     }
     * }
     * </pre>
     *
     * @param index
     * @param alias
     * @return
     */
    @RequestMapping(value = "/{index}", method = RequestMethod.PUT)
    String put_alias(
            @ApiParam(value = "（必需，字符串）索引名称的逗号分隔列表或通配符表达式，以添加到别名中;要将群集中的所有索引添加到别名，请使用值_all")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "（必需，字符串）要创建或更新的索引别名的名称")
            @PathVariable(value = "alias") String alias);


}

