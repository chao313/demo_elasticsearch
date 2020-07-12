package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 集群API
 */
@FeignClient(name = "cluster", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface ClusterService {

    /**
     * 功能:获取集群范围内的设置
     *
     * @param flat_settings    是否以平面格式返回,默认是false
     * @param include_defaults 是否返回所有的cluster的设置,默认是false
     * @param master_timeout   指定连接到master节点的时间段,如果规定时候未返回，则失败，默认30s
     * @param timeout          设置等待时间,超时失败，默认是30s
     * @return
     */
    @RequestMapping(value = "/_cluster/settings", method = RequestMethod.GET)
    String _cluster_settings(@RequestParam(value = "flat_settings") Boolean flat_settings,
                             @RequestParam(value = "include_defaults") Boolean include_defaults,
                             @RequestParam(value = "master_timeout") String master_timeout,
                             @RequestParam(value = "timeout") String timeout);


    /**
     * 功能:获取集群健康状态
     *
     * @param index                           路径参数:1.逗号分割list/2.通配符表达式（可选,string）
     * @param level                           用于控制健康消息的等级;可以是 cluster/indices/shards 中的一个;（可选,bool,默认cluster）
     * @param local                           从local节点还是master节点返回数据;true代表从local节点;(可选,bool,默认false)
     * @param master_timeout                  指定连接到master节点的时间段,如果规定时候未返回，则失败，默认30s
     * @param timeout                         设置等待时间,超时失败，默认是30s
     * @param wait_for_active_shards          控制等待active分片的数量;all -> 等待所有的分片(可选,string,默认0)
     * @param wait_for_events                 等待知道所有的给予优先级的,队列的时间处理完成,可以是immediate, urgent, high, normal, low, languid中的一个(可选,string)
     * @param wait_for_no_initializing_shards 当Cluster没有初始化shard,控制是否等待,默认为false,不等待(可选,bool)
     * @param wait_for_no_relocating_shards   当Cluster没有分配shard,控制是否等待,默认为false,不等待(可选,bool)
     * @param wait_for_nodes                  设置等待指定的node的数量可用，可以接收 >=N, <=N, >N and <N 或者 ge(N), le(N), gt(N) and lt(N)(可选,string)
     * @param wait_for_status                 设置等待，直到Cluster的status变成指定的值，或者更好;green > yellow > red;(可选,string，默认不等待任何status)
     * @return json
     *
     * <pre>
     * {
     *   "cluster_name" : "testcluster",
     *   "status" : "yellow",
     *   "timed_out" : false,
     *   "number_of_nodes" : 1,
     *   "number_of_data_nodes" : 1,
     *   "active_primary_shards" : 1,
     *   "active_shards" : 1,
     *   "relocating_shards" : 0,
     *   "initializing_shards" : 0,
     *   "unassigned_shards" : 1,
     *   "delayed_unassigned_shards": 0,
     *   "number_of_pending_tasks" : 0,
     *   "number_of_in_flight_fetch": 0,
     *   "task_max_waiting_in_queue_millis": 0,
     *   "active_shards_percent_as_number": 50.0
     * }
     * </pre>
     */
    @RequestMapping(value = "/_cluster/health/{index}", method = RequestMethod.GET)
    String _cluster_health(@PathVariable(value = "index") String index,
                           @RequestParam(value = "level") String level,
                           @RequestParam(value = "local") Boolean local,
                           @RequestParam(value = "master_timeout") String master_timeout,
                           @RequestParam(value = "timeout") String timeout,
                           @RequestParam(value = "wait_for_active_shards") String wait_for_active_shards,
                           @RequestParam(value = "wait_for_events") String wait_for_events,
                           @RequestParam(value = "wait_for_no_initializing_shards") Boolean wait_for_no_initializing_shards,
                           @RequestParam(value = "wait_for_no_relocating_shards") Boolean wait_for_no_relocating_shards,
                           @RequestParam(value = "wait_for_nodes") String wait_for_nodes,
                           @RequestParam(value = "wait_for_status") String wait_for_status);

    /**
     * 功能:返回有关集群状态的元数据
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-state.html#cluster-state-api-desc"></a>
     *
     *
     * <metrics>
     * （可选，字符串）以下选项的逗号分隔列表：
     * <p>
     * _all
     * 显示所有指标。
     * blocks
     * 显示blocks响应的一部分。
     * master_node
     * 显示master_node响应的选定部分。
     * metadata
     * 显示metadata响应的一部分。如果提供逗号分隔的索引列表，则返回的输出将仅包含这些索引的元数据。
     * nodes
     * 显示nodes响应的一部分。
     * routing_nodes
     * 显示routing_nodes响应的一部分。
     * routing_table
     * 显示routing_table响应的一部分。如果提供用逗号分隔的索引列表，则返回的输出将仅包含这些索引的路由表。
     * version
     * 显示集群状态版本。
     *
     *
     * <index>
     * （可选，字符串）索引名称的逗号分隔列表或通配符表达式，用于限制请求。
     */
    @RequestMapping(value = "/_cluster/state/{metrics}/{index}", method = RequestMethod.GET)
    String _cluster_state(@PathVariable(value = "metrics") String metrics,
                          @PathVariable(value = "index") String index);

    /**
     * 功能:返回集群统计信息
     * <p>
     * Cluster Stats API允许从集群范围的角度检索统计信息。该API返回基本索引指标（分片号，存储大小，内存使用情况）
     * 以及有关构成集群的当前节点的信息（数量，角色，操作系统，jvm版本，内存使用情况，cpu和已安装的插件）
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-stats.html"></a>
     */
    @RequestMapping(value = "/_cluster/stats", method = RequestMethod.GET)
    String _cluster_stats(
            @ApiParam(value = "（可选，布尔值）如果为true，则以平面格式返回设置。默认为 false")
            @RequestParam(value = "flat_settings", required = false, defaultValue = "false") Boolean flatSettings,
            @ApiParam(value = "（可选，时间单位）指定等待连接到主节点的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "master_timeout", required = false, defaultValue = "30s") String masterTimeout,
            @ApiParam(value = "（可选，时间单位）指定等待响应的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "timeout", required = false, defaultValue = "30s") String timeout);

    /**
     * 功能:返回有关功能用法的信息
     * <p>
     * 群集节点用法API使您可以检索有关每个节点的功能用法的信息。在此说明所有节点的选择选项
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-nodes-usage.html"></a>
     */
    @RequestMapping(value = "/_nodes/{node_id}/usage/{metric}", method = RequestMethod.GET)
    String _nodes_usage(
            @ApiParam(required = false, value = "（可选，字符串）用于限制返回信息的节点ID或名称的逗号分隔列表")
            @PathVariable(value = "node_id", required = false) String nodeId,
            @ApiParam(required = false, value = "（可选，字符串）将返回的信息限制为特定的指标。以逗号分隔的以下选项列表<br>1._all返回所有统计信息<br>2.rest_actions 返回REST操作类名，其中包括该操作在节点上被调用的次数", example = "_all,rest_actions")
            @PathVariable(value = "metric") String metric);

    /**
     * 功能:返回集群中每个选定节点上的热线程
     * <p>
     * 该API可以对群集中每个选定节点上的热线程进行细分。输出为纯文本，其中包含每个节点的顶​​部热线程的细分
     * <p>
     * 注意:
     * master_timeout 不知道为啥不起作用
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-nodes-usage.html"></a>
     */
    @RequestMapping(value = "/_nodes/{node_id}/hot_threads", method = RequestMethod.GET)
    String _nodes_usage(
            @ApiParam(value = "（可选，字符串）用于限制返回信息的节点ID或名称的逗号分隔列表")
            @PathVariable(value = "node_id", required = false) String node_id,
            @ApiParam(value = "可选，布尔值）如果为true，则将过滤掉已知的空闲线程（例如，在套接字选择中等待，或从空队列中获取任务）。默认为true")
            @RequestParam(value = "ignore_idle_threads", required = false, defaultValue = "true") Boolean ignore_idle_threads,
            @ApiParam(value = "（可选，时间单位）进行线程第二次采样的时间间隔。默认为500ms。")
            @RequestParam(value = "interval", required = false, defaultValue = "500ms") String interval,
            @ApiParam(value = "（可选，整数）线程stacktrace的样本数。默认为 10")
            @RequestParam(value = "snapshots", required = false, defaultValue = "10") Integer snapshots,
            @ApiParam(value = "(可选，整数）指定要为其提供信息的热线程的数量。默认为3")
            @RequestParam(value = "threads", required = false, defaultValue = "3") Integer threads,
//            @ApiParam(value = "（可选，时间单位）指定等待连接到主节点的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
//            @RequestParam(value = "master_timeout", required = false, defaultValue = "30s") String master_timeout,
            @ApiParam(value = "（可选，时间单位）指定等待响应的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "timeout", required = false, defaultValue = "30s") String timeout,
            @ApiParam(value = "（可选，字符串）要采样的类型。可用的选项有block，cpu，和 wait。默认为cpu", example = "block,cpu,wait")
            @RequestParam(value = "type", required = false, defaultValue = "cpu") String type);

}
