package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
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
    String create(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index,
                  @RequestBody CreateIndex createIndex);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-index.html"</a>
     * 返回索引的信息
     */
    @RequestMapping(value = "/{index}", method = RequestMethod.GET)
    String get(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-delete-index.html"></a>
     * 删除index
     */
    @RequestMapping(value = "/{index}", method = RequestMethod.DELETE)
    String delete(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-close.html"></a>
     * 关闭index
     * <p>
     * 封闭索引被禁止进行读/写操作，并且不允许开放索引允许的所有操作。无法对文档建立索引或在封闭索引中搜索文档。这使封闭索引不必维护用于索引或搜索文档的内部数据结构，从而导致群集上的开销较小。
     */
    @RequestMapping(value = "/{index}/_close", method = RequestMethod.POST)
    String _close(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html"></a>
     * 打开index
     */
    @RequestMapping(value = "/{index}/_open", method = RequestMethod.POST)
    String _open(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/freeze-index-api.html"></a>
     * 冻结索引在群集上几乎没有开销（除了将其元数据保留在内存中），并且是只读的。只读索引被阻止进
     * 冻结index
     */
    @RequestMapping(value = "/{index}/_freeze", method = RequestMethod.POST)
    String _freeze(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/unfreeze-index-api.html"></a>
     * 冻结index
     */
    @RequestMapping(value = "/{index}/_unfreeze", method = RequestMethod.POST)
    String _unfreeze(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-stats.html"></a>
     * 获取index的stats
     */
    @RequestMapping(value = "/{index}/_stats/{index-metric}", method = RequestMethod.GET)
    String _stats(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index,
                  @ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)", defaultValue = "_all", allowableValues = "_all,completion,fielddata,flush,get,indexing,merge,query_cache,refresh,request_cache,search,segments,store,suggest,translog,warmer")
                  @PathVariable(value = "index-metric") String index_metric
    );

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html"></a>
     * 刷新index
     */
    @RequestMapping(value = "/{index}/_refresh", method = RequestMethod.GET)
    String _refresh(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index);

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
    String _clone(@ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
                  @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index
    );

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html"></a>
     * flush index
     */
    @RequestMapping(value = "/{index}/_flush", method = RequestMethod.GET)
    String _flush(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-synced-flush-api.html"></a>
     * 同步 flush index
     * <p>
     * Sync-flush已弃用，并将在8.0中删除。请改用冲洗。刷新与在Elasticsearch 7.6或更高版本上的同步刷新具有相同的效果
     *
     */
    @RequestMapping(value = "/{index}/_flush/synced", method = RequestMethod.GET)
    String _flush_synced(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-segments.html"></a>
     * 返回有关 索引分片中Lucene段的低级信息
     */
    @RequestMapping(value = "/{index}/_segments", method = RequestMethod.GET)
    String _segments(@ApiParam(value = "(可选，字符串）索引名称的逗号分隔列表或通配符表达式，用于限制请求") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html"></a>
     * 返回索引的设置信息
     */
    @RequestMapping(value = "/{index}/_settings", method = RequestMethod.GET)
    String _settings(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html"></a>
     * 返回索引的设置信息
     */
    @RequestMapping(value = "/{index}/_settings/{setting}", method = RequestMethod.GET)
    String _settings(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index,
                     @ApiParam(value = "(可选，字符串)用于限制请求的设置名称的逗号分隔列表或通配符表达式") @PathVariable(value = "setting") String setting);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-recovery.html"></a>
     * 返回有关正在进行和已完成的碎片恢复的信息
     */
    @RequestMapping(value = "/{index}/_recovery", method = RequestMethod.GET)
    String _recovery(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shards-stores.html"></a>
     * 返回一个或多个索引中有关副本分片的存储信息
     */
    @RequestMapping(value = "/{index}/_shard_stores", method = RequestMethod.GET)
    String _shard_stores(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shrink-index.html"></a>
     * <p>
     * 使用更少的主碎片将现有索引缩减为新索引
     * <p>
     * -----------------------------------------------------------------
     * 在收缩索引之前：
     * <p>
     * 索引必须是只读的。
     * 索引中每个分片的副本必须位于同一节点上。
     * 该集群的健康状态必须是绿色的。
     * -----------------------------------------------------------------
     * 参数 {@link #_clone(String, String)}
     */
    @RequestMapping(value = "/{source_index}/_shrink/{target_index}", method = RequestMethod.POST)
    String _shrink(@ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
                   @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index
    );

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-split-index.html"></a>
     * <p>
     * 将现有索引拆分为具有更多主碎片的新索引
     * <p>
     * -----------------------------------------------------------------
     * 在收缩索引之前：
     * <p>
     * 索引必须是只读的。
     * 索引中每个分片的副本必须位于同一节点上。
     * 该集群的健康状态必须是绿色的。
     * -----------------------------------------------------------------
     * 参数 {@link #_clone(String, String)}
     */
    @RequestMapping(value = "/{source_index}/_split/{target_index}", method = RequestMethod.POST)
    String _split(@ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
                  @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index
    );

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html"></a>
     * 在一个或多个索引的分片上强制合并
     * <p>
     * 使用强制合并API可以在一个或多个索引的分片上强制合并。合并通过将每个分片中的某些片段合并在一起来减少其数量，还可以释放已删除文档所占用的空间。
     * 合并通常自动发生，但是有时手动触发合并很有用。
     */
    @RequestMapping(value = "/{index}/_forcemerge", method = RequestMethod.POST)
    String _forcemerge(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html"></a>
     * 将新字段添加到现有索引或更改现有字段的搜索设置
     * <p>
     * 使用强制合并API可以在一个或多个索引的分片上强制合并。合并通过将每个分片中的某些片段合并在一起来减少其数量，还可以释放已删除文档所占用的空间。
     * 合并通常自动发生，但是有时手动触发合并很有用。
     */
    @RequestMapping(value = "/{index}/_mapping", method = RequestMethod.PUT)
    String _mapping(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index);


}

