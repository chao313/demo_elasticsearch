package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import demo.elastic.search.po.request.index.doc.reindex.ReIndexRequest;
import io.swagger.annotations.ApiParam;
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
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/docs-index_.html#docs-index-api-request"></a>
     * <p>
     * 将JSON文档添加到指定索引并使其可搜索。如果文档已存在，请更新文档并增加其版本
     */
    @RequestMapping(value = "/{index}/_doc/{_id}", method = RequestMethod.PUT, headers = {"content-type=application/json"})
    String put_doc(
            @ApiParam(value = "（必需，字符串）目标索引的名称。默认情况下，如果索引不存在，则会自动创建")
            @PathVariable(value = "index") String index,
            @PathVariable(value = "_id") String _id,
            @RequestBody String body);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/docs-index_.html#docs-index-api-request"></a>
     * <p>
     * 将JSON文档添加到指定索引并使其可搜索。如果文档已存在，请更新文档并增加其版本
     */
    @RequestMapping(value = "/{index}/_doc", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String post_doc(
            @ApiParam(value = "（必需，字符串）目标索引的名称。默认情况下，如果索引不存在，则会自动创建")
            @PathVariable(value = "index") String index,
            @RequestBody String body);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/docs-get.html"></a>
     * <p>
     * 从索引检索指定的JSON文档
     */
    @RequestMapping(value = "/{index}/_doc/{_id}", method = RequestMethod.GET)
    String get(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
               @ApiParam(value = "（必需，字符串）文档的唯一标识符") @PathVariable(value = "_id") String _id);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/docs-delete.html"></a>
     * <p>
     * 从指定的索引中删除JSON文档
     */
    @RequestMapping(value = "/{index}/_doc/{_id}", method = RequestMethod.DELETE)
    String delete(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                  @ApiParam(value = "（必需，字符串）文档的唯一标识符") @PathVariable(value = "_id") String _id);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete-by-query.html"></a>
     * <p>
     * 删除与指定查询匹配的文档
     * <p>
     * 版本等于0的文档无法使用查询删除功能删除，因为internal版本控制不支持0作为有效版本号。
     */
    @RequestMapping(value = "/{index}/_delete_by_query", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _delete_by_query(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                            @RequestBody String body);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update.html"></a>
     * 使用指定的脚本更新文档
     * <p>
     * 该操作：
     * <p>
     * 从索引获取文档（与分片并置）。
     * 运行指定的脚本。
     * 索引结果。
     * <p>
     *
     * <pre>
     * ----------------------------------------------------
     *  相加计算
     * {
     *   "script" : {
     *     "source": "ctx._source.counter += params.count",
     *     "lang": "painless",
     *     "params" : {
     *       "count" : 4
     *     }
     *   }
     * }
     * ------------------------
     * 添加新字段
     * {
     *   "script" : "ctx._source.new_field = 'value_of_new_field'"
     * }
     * -------------------
     * 移除新字段
     * {
     *   "script" : "ctx._source.remove('new_field')"
     * }
     *
     * </pre>
     */
    @RequestMapping(value = "/{index}/_update/{_id}", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _update(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                   @ApiParam(value = "（必需，字符串）文档的唯一标识符") @PathVariable(value = "_id") String _id,
                   @ApiParam(value = "{<br>" +
                           "  \"script\" : {<br>" +
                           "    \"source\": \"ctx._source.age += params.count\",<br>" +
                           "    \"lang\": \"painless\",<br>" +
                           "    \"params\" : {<br>" +
                           "      \"count\" : 4<br>" +
                           "    }<br>" +
                           "  }<br>" +
                           "}")
                   @RequestBody String body);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html"></a>
     * <p>
     * 批量操作(提供执行多种方式index，create，delete，和update在一个请求的动作) 目前还有问题
     * <p>
     * 数据的最后一行必须以换行符结尾<br>。每个换行符前面都可以有一个回车符\r。
     * <p>
     * { "index" : { "_index" : "test", "_id" : "1" } }
     * { "field1" : "value1" }
     * { "delete" : { "_index" : "test", "_id" : "2" } }
     * { "create" : { "_index" : "test", "_id" : "3" } }
     * { "field1" : "value3" }
     * { "update" : {"_id" : "1", "_index" : "test"} }
     */
    @RequestMapping(value = "/{index}/_bulk", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _bulk(@ApiParam(value = "（必需，字符串）包含文档的索引的名称") @PathVariable(value = "index") String index,
                 @ApiParam(value = "{ \"index\" : { \"_index\" : \"test\", \"_id\" : \"1\" } }<br>" +
                         "{ \"field1\" : \"value1\" }<br>" +
                         "{ \"delete\" : { \"_index\" : \"test\", \"_id\" : \"2\" } }<br>" +
                         "{ \"create\" : { \"_index\" : \"test\", \"_id\" : \"3\" } }<br>" +
                         "{ \"field1\" : \"value3\" }<br>" +
                         "{ \"update\" : {\"_id\" : \"1\", \"_index\" : \"test\"} }<br>" +
                         "{ \"doc\" : {\"field2\" : \"value2\"} }")
                 @RequestBody String body);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html"></a>
     * <p>
     * 将文档从一个索引复制到另一索引
     * <p>
     * conflicts
     * （可选，枚举）设置为proceed即使发生冲突也继续重新编制索引。默认为abort。
     * <p>要开启白名单
     * reindex.remote.whitelist: "otherhost:9200, another:9200, 127.0.10.*:9200, localhost:*"
     * -> 支持通配 *:*
     * -> 两个集群间的复制,必须能端口相通
     */
    @RequestMapping(value = "_reindex", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _reindex(@RequestBody ReIndexRequest body);

    /**
     *
     * Multi get
     * @param body
     * @return
     */
//    @RequestMapping(value = "_reindex", method = RequestMethod.POST, headers = {"content-type=application/json"})
//    String _reindex(@RequestBody ReIndexRequest body);
}
