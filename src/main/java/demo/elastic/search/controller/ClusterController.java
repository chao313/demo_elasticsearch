package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.ClusterService;
import demo.elastic.search.framework.Response;
import feign.Feign;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch es 查询使用
 */
@RequestMapping(value = "/ClusterController")
@RestController
public class ClusterController {

    @Resource
    private ClusterService clusterService;

    /**
     * 功能:获取集群范围内的设置
     *
     * @return
     */
    @ApiOperation(value = "获取集群范围内的设置")
    @GetMapping(value = "/_cluster/settings")
    public Response _cluster_settings(
            @RequestParam(value = "flat_settings", defaultValue = "false") Boolean flat_settings,
            @RequestParam(value = "include_defaults", defaultValue = "false") Boolean include_defaults,
            @RequestParam(value = "master_timeout", defaultValue = "30s") String master_timeout,
            @RequestParam(value = "timeout", defaultValue = "30s") String timeout
    ) {
        String result = clusterService._cluster_settings(flat_settings, include_defaults, master_timeout, timeout);
        return Response.Ok(JSONObject.parse(result));
    }

    /**
     * 功能:获取集群健康状态
     */
    @ApiOperation(value = "返回集群健康状态")
    @GetMapping(value = "/_cluster/health/{index}")
    public Response _cluster_health(@RequestParam(value = "index", required = false) String index,

                                    @ApiParam(allowableValues = "cluster,indices,shards", defaultValue = "cluster")
                                    @RequestParam(value = "level") String level,
                                    @RequestParam(value = "local", defaultValue = "false") Boolean local,
                                    @ApiParam(allowableValues = "30s,1m", defaultValue = "30s")
                                    @RequestParam(value = "master_timeout") String master_timeout,
                                    @ApiParam(allowableValues = "30s,1m", defaultValue = "30s")
                                    @RequestParam(value = "timeout") String timeout,
                                    @ApiParam(allowableValues = "0,1,all", defaultValue = "0")
                                    @RequestParam(value = "wait_for_active_shards") String wait_for_active_shards,
                                    @ApiParam(allowableValues = "immediate,urgent,high,normal,low,languid")
                                    @RequestParam(value = "wait_for_events", required = false) String wait_for_events,
                                    @RequestParam(value = "wait_for_no_initializing_shards", defaultValue = "false") Boolean wait_for_no_initializing_shards,
                                    @RequestParam(value = "wait_for_no_relocating_shards", defaultValue = "false") Boolean wait_for_no_relocating_shards,
                                    @ApiParam(allowableValues = ">=2,<=3,>3", defaultValue = "<=3")
                                    @RequestParam(value = "wait_for_nodes", required = false) String wait_for_nodes,
                                    @ApiParam(allowableValues = "green,yellow,red", defaultValue = "green")
                                    @RequestParam(value = "wait_for_status", required = false) String wait_for_status) {
        String result = clusterService._cluster_health(index, level, local, master_timeout, timeout, wait_for_active_shards, wait_for_events,
                wait_for_no_initializing_shards, wait_for_no_relocating_shards, wait_for_nodes, wait_for_status);
        return Response.Ok(JSONObject.parse(result));

    }

    /**
     * 功能:返回有关集群状态的元数据
     */
    @ApiOperation(value = "返回有关集群状态的元数据")
    @GetMapping(value = "/_cluster/state/{metrics}/{index}")
    public Response _cluster_state(
            @ApiParam(allowableValues = "_all,blocks,master_node,metadata,nodes,routing_nodes,routing_table,version", defaultValue = "_all")
            @PathVariable(value = "metrics", required = false) String metrics,
            @ApiParam(required = false)
            @PathVariable(value = "index", required = false) String index) {
        String result = clusterService._cluster_state(metrics, index);
        return Response.Ok(JSONObject.parse(result));

    }

    /**
     * 功能:返回集群统计信息
     * <p>
     * Cluster Stats API允许从集群范围的角度检索统计信息。该API返回基本索引指标（分片号，存储大小，内存使用情况）
     * 以及有关构成集群的当前节点的信息（数量，角色，操作系统，jvm版本，内存使用情况，cpu和已安装的插件）
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-stats.html"></a>
     */
    @ApiOperation(value = "返回集群统计信息")
    @GetMapping(value = "/_cluster/stats")
    public Response _cluster_stats(
            @ApiParam(value = "（可选，布尔值）如果为true，则以平面格式返回设置。默认为 false")
            @RequestParam(value = "flat_settings", required = false, defaultValue = "false") Boolean flatSettings,
            @ApiParam(value = "（可选，时间单位）指定等待连接到主节点的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "master_timeout", required = false, defaultValue = "30s") String masterTimeout,
            @ApiParam(value = "（可选，时间单位）指定等待响应的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "timeout", required = false, defaultValue = "30s") String timeout) {
        String result = clusterService._cluster_stats(flatSettings, masterTimeout, timeout);
        return Response.Ok(JSONObject.parse(result));
    }

    /**
     * 功能:返回有关功能用法的信息
     * <p>
     * 群集节点用法API使您可以检索有关每个节点的功能用法的信息。在此说明所有节点的选择选项
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-nodes-usage.html"></a>
     */
    @ApiOperation(value = "返回有关功能用法的信息")
    @RequestMapping(value = "/_nodes/{node_id}/usage/{metric}", method = RequestMethod.GET)
    public Response _nodes_usage(
            @ApiParam(required = false, value = "（可选，字符串）用于限制返回信息的节点ID或名称的逗号分隔列表")
            @RequestParam(value = "node_id", required = false) String nodeId,
            @ApiParam(required = false, value = "（可选，字符串）将返回的信息限制为特定的指标。以逗号分隔的以下选项列表<br>1._all返回所有统计信息<br>2.rest_actions 返回REST操作类名，其中包括该操作在节点上被调用的次数", example = "_all,rest_actions")
            @RequestParam(value = "metric", required = false) String metric) {
        String result = clusterService._nodes_usage(nodeId, metric);
        return Response.Ok(JSONObject.parse(result));
    }

    /**
     * 功能:返回集群中每个选定节点上的热线程
     * <p>
     * 该API可以对群集中每个选定节点上的热线程进行细分。输出为纯文本，其中包含每个节点的顶​​部热线程的细分
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-nodes-usage.html"></a>
     */
    @RequestMapping(value = "/_nodes/{node_id}/hot_threads", method = RequestMethod.GET)
    public String _nodes_usage(
            @ApiParam(value = "（可选，字符串）用于限制返回信息的节点ID或名称的逗号分隔列表")
            @RequestParam(value = "node_id", required = false) String node_id,
            @ApiParam(value = "可选，布尔值）如果为true，则将过滤掉已知的空闲线程（例如，在套接字选择中等待，或从空队列中获取任务）。默认为true")
            @RequestParam(value = "ignore_idle_threads", required = false, defaultValue = "true") Boolean ignore_idle_threads,
            @ApiParam(value = "（可选，时间单位）进行线程第二次采样的时间间隔。默认为500ms。")
            @RequestParam(value = "interval", required = false, defaultValue = "500ms") String interval,
            @ApiParam(value = "（可选，整数）线程stacktrace的样本数。默认为 10")
            @RequestParam(value = "snapshots", required = false, defaultValue = "10") Integer snapshots,
            @ApiParam(value = "(可选，整数）指定要为其提供信息的热线程的数量。默认为3")
            @RequestParam(value = "threads", required = false, defaultValue = "3") Integer threads,
//            @ApiParam(value = "（可选，时间单位）指定等待连接到主节点的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
//            @RequestParam(value = "master_timeout", required = false, defaultValue = "30s") String masterTimeout,
            @ApiParam(value = "（可选，时间单位）指定等待响应的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "timeout", required = false, defaultValue = "30s") String timeout,
            @ApiParam(value = "（可选，字符串）要采样的类型。可用的选项有block，cpu，和 wait。默认为cpu", example = "block,cpu,wait")
            @RequestParam(value = "type", required = false, defaultValue = "cpu") String type) {
        String result = clusterService._nodes_usage(node_id, ignore_idle_threads, interval, snapshots, threads, /*masterTimeout,*/ timeout, type);
        return result;
    }

}
















