package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.ClusterService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 自定义集群操作
 */
@RequestMapping(value = "/Cluster")
@RestController
public class Cluster {


    /**
     * 列出CAT的全部接口
     *
     * @return
     */
    @ApiOperation(value = "ES的base请求")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/")
    public String _base() {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._base();
    }

    /**
     * 列出CAT的全部接口
     *
     * @return
     */
    @ApiOperation(value = "列出Cat的全部接口", notes = "列出CAT的全部接口")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cat")
    public String cat() {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat();
    }


    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回集群的运行状况")
    @GetMapping(value = "/_cat/health")
    public String _cat_health(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_health(v);
    }


    @ApiOperation(value = "提供对单个索引或集群中所有索引的文档计数的快速访问")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cat/count")
    public String _cat_count(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_count(v);
    }


    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cat/thread_pool")
    public String _cat_thread_pool(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_thread_pool(v);
    }

    @ApiOperation(value = "提供集群中分片分配的说明")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cluster/allocation/explain")
    public Response _cluster_allocation_explain(
            @ApiParam(value = "（可选，布尔值）如果为true，则返回有关磁盘使用情况和碎片大小的信息。默认为false")
            @RequestParam(value = "include_disk_info", defaultValue = "false") Boolean include_disk_info,
            @ApiParam(value = "（可选，布尔值）如果为true，则返回YES决策说明。默认为false")
            @RequestParam(value = "include_yes_decisions", defaultValue = "false") Boolean include_yes_decisions,
            @ApiParam(value = "（可选，字符串）指定节点ID或节点名称，以仅说明当前位于指定节点上的分片")
            @RequestParam(value = "current_node", required = false) String current_node,
            @ApiParam(value = "（可选，字符串）指定要为其解释的索引的名称")
            @RequestParam(value = "index", required = false) String index,
            @ApiParam(value = "可选，布尔值）如果为true，则返回给定分片ID的主分片的说明。")
            @RequestParam(value = "primary", required = false) Boolean primary,
            @ApiParam(value = "（可选，整数）指定您要解释的分片的ID")
            @RequestParam(value = "shard", required = false) Integer shard
    ) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._cluster_allocation_explain(include_disk_info, include_yes_decisions, current_node, index, primary, shard);
        return Response.Ok(JSONObject.parse(result));
    }

    /**
     * 功能:获取集群范围内的设置
     *
     * @return
     */
    @ApiOperation(value = "获取集群范围内的设置")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cluster/settings")
    public Response _cluster_settings(
            @RequestParam(value = "flat_settings", defaultValue = "false") Boolean flat_settings,
            @RequestParam(value = "include_defaults", defaultValue = "false") Boolean include_defaults,
            @RequestParam(value = "master_timeout", defaultValue = "30s") String master_timeout,
            @RequestParam(value = "timeout", defaultValue = "30s") String timeout
    ) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._cluster_settings(flat_settings, include_defaults, master_timeout, timeout);
        return Response.Ok(JSONObject.parse(result));
    }

    /**
     * 功能:获取集群健康状态
     */
    @ApiOperation(value = "返回集群健康状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cluster/health")
    public Response _cluster_health(@ApiParam(allowableValues = "cluster,indices,shards", defaultValue = "cluster")
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
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._cluster_health(level, local, master_timeout, timeout, wait_for_active_shards, wait_for_events,
                wait_for_no_initializing_shards, wait_for_no_relocating_shards, wait_for_nodes, wait_for_status);
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
    @ApiOperation(value = "返回集群统计信息", notes = "节点的信息（数量，角色，操作系统，jvm版本，内存使用情况，cpu和已安装的插件）")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cluster/stats")
    public Response _cluster_stats(
//            @ApiParam(value = "（可选，布尔值）如果为true，则以平面格式返回设置。默认为 false")
//            @RequestParam(value = "flat_settings", required = false, defaultValue = "false") Boolean flatSettings,
//            @ApiParam(value = "（可选，时间单位）指定等待连接到主节点的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
//            @RequestParam(value = "master_timeout", required = false, defaultValue = "30s") String masterTimeout,
//            @ApiParam(value = "（可选，时间单位）指定等待响应的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "timeout", required = false, defaultValue = "30s") String timeout) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._cluster_stats(timeout);
        return Response.Ok(JSONObject.parse(result));
    }

    /**
     * 功能:返回已配置的远程集群信息
     * <p>
     * 群集远程信息API使您可以检索所有已配置的远程群集信息。它返回由配置的远程集群别名键入的连接和端点信息。
     * <p>
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-remote-info.html"></a>
     *
     * <metric>
     * （可选，字符串）将返回的信息限制为特定的指标。以逗号分隔的以下选项列表：
     * <p>
     * response:
     * mode
     * 远程集群的连接模式。返回值为sniff和 proxy。
     * connected
     * 如果到远程集群至少有一个连接，则为True。
     * initial_connect_timeout
     * 远程集群连接的初始连接超时。
     * skip_unavailable
     * 如果通过跨集群搜索请求搜索了远程集群，但没有节点可用，则是否跳过该远程集群。
     * seeds
     * 配置了嗅探模式时，远程群集的初始种子传输地址。
     * num_nodes_connected
     * 配置嗅探模式时，远程集群中已连接的节点数。
     * max_connections_per_cluster
     * 配置嗅探模式时，为远程群集维护的最大连接数。
     * proxy_address
     * 配置代理模式时的远程连接地址。
     * num_proxy_sockets_connected
     * 配置代理模式时，与远程集群的开放套接字连接数。
     * max_proxy_socket_connections
     * 配置代理模式时，与远程集群的最大套接字连接数。
     */

    @ApiOperation(value = "返回已配置的远程集群信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_remote/info", method = RequestMethod.GET)
    public Response _remote_info() {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._remote_info();
        return Response.Ok(JSONObject.parse(result));
    }


}
















