package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import demo.elastic.search.po.request.index.CreateIndex;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
     * <p>
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
     * @return
     */
    @RequestMapping(value = "/{index}", method = RequestMethod.PUT, headers = {"content-type=application/json"})
    String create(@ApiParam(value = "索引名称")
                  @PathVariable(value = "index") String index,
                  @RequestBody CreateIndex createIndex);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-delete-index.html"></a>
     * 删除index
     */
    @RequestMapping(value = "/{index}", method = RequestMethod.DELETE)
    String delete(@ApiParam(value = "索引名称")
                  @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-close.html"></a>
     * 关闭index
     * <p>
     * 封闭索引被禁止进行读/写操作，并且不允许开放索引允许的所有操作。无法对文档建立索引或在封闭索引中搜索文档。这使封闭索引不必维护用于索引或搜索文档的内部数据结构，从而导致群集上的开销较小。
     */
    @RequestMapping(value = "/{index}/_close", method = RequestMethod.POST)
    String _close(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
                  @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html"></a>
     * 打开index
     */
    @RequestMapping(value = "/{index}/_open", method = RequestMethod.POST)
    String _open(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
                 @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/freeze-index-api.html"></a>
     * 冻结索引在群集上几乎没有开销（除了将其元数据保留在内存中），并且是只读的。只读索引被阻止进
     * 冻结index
     */
    @RequestMapping(value = "/{index}/_freeze", method = RequestMethod.POST)
    String _freeze(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
                   @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/unfreeze-index-api.html"></a>
     * 冻结index
     */
    @RequestMapping(value = "/{index}/_unfreeze", method = RequestMethod.POST)
    String _unfreeze(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
                     @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html"></a>
     * 获取index的stats
     */
    @RequestMapping(value = "/{index}/_stats/{index-metric}", method = RequestMethod.GET)
    String _stats(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
                  @PathVariable(value = "index") String index,
                  @ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)", defaultValue = "_all", allowableValues = "_all,completion,fielddata,flush,get,indexing,merge,query_cache,refresh,request_cache,search,segments,store,suggest,translog,warmer")
                  @PathVariable(value = "index-metric") String index_metric
    );

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html"></a>
     * 刷新index
     */
    @RequestMapping(value = "/{index}/_refresh", method = RequestMethod.GET)
    String _refresh(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
                    @PathVariable(value = "index") String index
    );

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clone-index.html"></a>
     * <p>
     * 克隆index
     * <p>
     * 只有满足以下条件的索引才能被克隆：
     * <p>
     * 目标索引必须不存在。
     * 源索引必须具有与目标索引相同数量的主碎片。
     * 处理克隆过程的节点必须具有足够的可用磁盘空间，以容纳现有索引的第二个副本。
     * <p>
     * -----------------------------------------------------------------
     * 先决条件编辑
     * 要克隆索引，该索引必须标记为只读，并且集群健康状况为green。
     * <p>
     * 例如，以下请求阻止对其进行写操作，my_source_index 因此可以对其进行克隆。仍允许进行元数据更改，例如删除索引。
     * <p>
     * PUT / my_source_index / _settings { “ settings” ：{ “ index.blocks.write” ：true } }
     * -----------------------------------------------------------------
     * <index>
     * （必需，字符串）要克隆的源索引的名称。
     * <target-index>
     * （必需，字符串）要创建的目标索引的名称。
     * <p>
     * 索引名称必须满足以下条件：
     * <p>
     * 仅小写
     * 不能包括\，/，*，?，"，<，>，|，``（空格字符）， ,，#
     * 7.0之前的索引可能包含冒号（:），但已过时，并且在7.0+中不支持
     * 无法下手-，_，+
     * 不能为.或..
     * 不能超过255个字节（请注意它是字节，因此多字节字符将更快地计入255个限制）
     * .不建议使用以 开头的名称，但隐藏索引和由插件管理的内部索引 除外
     */
    @RequestMapping(value = "/{source_index}/_clone/{target_index}", method = RequestMethod.POST)
    String _clone(@ApiParam(value = "要克隆的源索引的名称")
                  @PathVariable(value = "source_index") String source_index,
                  @ApiParam(value = "要创建的目标索引的名称")
                  @PathVariable(value = "target_index") String target_index
    );
}

