package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.ClusterService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import demo.elastic.search.util.StringToJson;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


/**
 * 集群节点相关
 */
@RequestMapping(value = "/Cluster_NodeController")
@RestController
public class Cluster_NodeController {

    @ApiOperation(value = "列出分配给每个数据节点的分片数量及其磁盘空间的快照")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cat/allocation")
    public String _cat_allocation(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_allocation(v);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "列出集群中每个数据节点上的字段数据 当前 使用的堆内存量")
    @GetMapping(value = "/_cat/fielddata")
    public String _cat_fielddata(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_fielddata(v);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回有关自定义节点属性的信息")
    @GetMapping(value = "/_cat/nodeattrs")
    public String _cat_nodeattrs(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_nodeattrs(v);
    }


    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回master节点的信息，包括ID绑定IP地址和名称")
    @GetMapping(value = "/_cat/master")
    public String _cat_master(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_master(v);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回有关群集节点的信息")
    @GetMapping(value = "/_cat/nodes")
    public String _cat_nodes(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                             @ApiParam(value = "要显示的以逗号分隔的列名称列表(ip,cpu,port...)") @RequestParam(value = "h") String h) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_nodes(v, h);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回有关群集节点的信息(这里移除了load_5m,load_15m)")
    @GetMapping(value = "/_cat/nodes/format")
    public Response _cat_nodes_format() throws IOException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String source = catService._cat_nodes(true, "id,pid,ip,port,http_address,version,flavor,type,build,jdk,disk.total,disk.used,disk.avail,disk.used_percent,heap.current,heap.percent,heap.max,ram.current,ram.percent,ram.max,file_desc.current,file_desc.percent,file_desc.max,cpu,load_1m,uptime,node.role,master,name,completion.size,fielddata.memory_size,fielddata.evictions,query_cache.memory_size,query_cache.evictions,request_cache.memory_size,request_cache.evictions,request_cache.hit_count,request_cache.miss_count,flush.total,flush.total_time,get.current,get.time,get.total,get.exists_time,get.exists_total,get.missing_time,get.missing_total,indexing.delete_current,indexing.delete_time,indexing.delete_total,indexing.index_current,indexing.index_time,indexing.index_total,indexing.index_failed,merges.current,merges.current_docs,merges.current_size,merges.total,merges.total_docs,merges.total_size,merges.total_time,refresh.total,refresh.time,refresh.external_total,refresh.external_time,refresh.listeners,script.compilations,script.cache_evictions,script.compilation_limit_triggered,search.fetch_current,search.fetch_time,search.fetch_total,search.open_contexts,search.query_current,search.query_time,search.query_total,search.scroll_current,search.scroll_time,search.scroll_total,segments.count,segments.memory,segments.index_writer_memory,segments.version_map_memory,segments.fixed_bitset_memory,suggest.current,suggest.time,suggest.total");
        return Response.Ok(StringToJson.toJSONArray(source));
    }

    /**
     * 功能:返回有关功能用法的信息
     * <p>
     * 群集节点用法API使您可以检索有关每个节点的功能用法的信息。在此说明所有节点的选择选项
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-nodes-usage.html"></a>
     */
    @ApiOperation(value = "返回有关功能用法的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_nodes/usage", method = RequestMethod.GET)
    public Response _nodes_usage() {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._nodes_usage();
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "返回有关功能用法的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_nodes/usage/{metric}", method = RequestMethod.GET)
    public Response _nodes_usage(
            @ApiParam(required = false, value = "（可选，字符串）将返回的信息限制为特定的指标。以逗号分隔的以下选项列表<br>1._all返回所有统计信息<br>2.rest_actions 返回REST操作类名，其中包括该操作在节点上被调用的次数", example = "_all,rest_actions")
            @RequestParam(value = "metric", required = false) String metric) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._nodes_usage(metric);
        return Response.Ok(JSONObject.parse(result));
    }


}
















