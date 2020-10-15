package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.ClusterService;
import demo.elastic.search.feign.enums.FormatEnum;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 节点相关
 */
@RequestMapping(value = "/Node")
@RestController
public class Node {

    @ApiOperation(value = "列出指定数据节点的分片数量及其磁盘空间的快照 (e.g. dataNode-dwserver18-4)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cat/allocation/{node_id}")
    public Object _cat_allocation_nodeId(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                         @ApiParam(value = "节点Id") @PathVariable(value = "node_id") String node_id,
                                         @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_allocation_nodeId(v, node_id, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "列出集群中每个数据节点上的字段数据 当前 使用的堆内存量")
    @GetMapping(value = "/_cat/fielddata/{field}")
    public Object _cat_fielddata(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                 @PathVariable(value = "field") String field,
                                 @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_fielddata_field(v, field, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }

    @ApiOperation(value = "返回有关功能用法的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_nodes/{node_id}/usage/{metric}", method = RequestMethod.GET)
    public Response _nodes_usage(
            @ApiParam(required = false, value = "（可选，字符串）用于限制返回信息的节点ID或名称的逗号分隔列表")
            @RequestParam(value = "node_id", required = false) String nodeId,
            @ApiParam(required = false, value = "（可选，字符串）将返回的信息限制为特定的指标。以逗号分隔的以下选项列表<br>1._all返回所有统计信息<br>2.rest_actions 返回REST操作类名，其中包括该操作在节点上被调用的次数", example = "_all,rest_actions")
            @RequestParam(value = "metric", required = false) String metric) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
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
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
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
            @ApiParam(value = "（可选，字符串）要采样的类型。可用的选项有block，cpu，和 wait。默认为cpu", allowableValues = "block,cpu,wait")
            @RequestParam(value = "type", required = false, defaultValue = "cpu") String type) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._nodes_usage(node_id, ignore_idle_threads, interval, snapshots, threads, /*masterTimeout,*/ timeout, type);
        return result;
    }


    /**
     * 功能:返回集群节点信息
     * <p>
     * 群集节点信息API允许检索一个或多个（或全部）群集节点信息。在此说明所有节点的选择选项 。
     * 默认情况下，它将返回节点的所有属性和核心设置。
     * <p>
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-nodes-info.html"></a>
     *
     * <metric>
     * （可选，字符串）将返回的信息限制为特定的指标。以逗号分隔的以下选项列表：
     * <p>
     * http
     * HTTP连接信息。
     * ingest
     * 有关摄取预处理的统计信息。
     * jvm
     * JVM统计信息，内存池信息，垃圾回收，缓冲池，已加载/已卸载类的数量。
     * os
     * 操作系统统计信息，平均负载，内存，交换。
     * plugins
     * 有关每个节点已安装的插件和模块的详细信息。以下信息可用于每个插件和模块：
     * <p>
     * name：插件名称
     * version：插件所针对的Elasticsearch版本
     * description：插件用途的简短说明
     * classname：插件入口点的标准类名
     * has_native_controller：插件是否具有本机控制器进程-
     * process
     * 进程统计信息，内存消耗，CPU使用率，打开文件描述符。
     * settings
     * thread_pool
     * 有关每个线程池的统计信息，包括当前大小，队列和拒绝的任务
     * transport
     * 集群通信中有关已发送和已接收字节的传输统计信息。
     * <node_id>
     * （可选，字符串）用于限制返回信息的节点ID或名称的逗号分隔列表。
     */
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_nodes/{node_id}/{metric}", method = RequestMethod.GET)
    public Response _nodes(
            @ApiParam(value = "（可选，字符串）将返回的信息限制为特定的指标。以逗号分隔的以下选项列表")
            @RequestParam(value = "node_id", required = false) String node_id,
            @ApiParam(value = "（可选，字符串）用于限制返回信息的节点ID或名称的逗号分隔列表", allowableValues = "_all,http,ingest,jvm,os,plugins,process,settings,thread_pool,transport")
            @RequestParam(value = "metric", required = false) String metric) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._nodes(node_id, metric);
        return Response.Ok(JSONObject.parse(result));
    }

}
















