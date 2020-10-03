package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.ClusterService;
import demo.elastic.search.feign.IndexService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 索引相关
 */
@RequestMapping(value = "/Index")
@RestController
public class Index {


    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "提供对单个索引或集群中所有索引的文档计数的快速访问")
    @GetMapping(value = "/_cat/count/{index}")
    public String _cat_count(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                             @PathVariable(value = "index") String index) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_count(v, index);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "列出index(指定)")
    @GetMapping(value = "/_cat/indices/{index}")
    public String _cat_indices_index(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                     @PathVariable(value = "index") String index) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_indices_index(v, index);
    }


    /**
     * 功能:获取集群健康状态（指定索引）
     */
    @ApiOperation(value = "返回集群健康状态(指定索引)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cluster/health/{index}")
    public Response _cluster_health_index(@RequestParam(value = "index", required = false) String index,

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
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._cluster_health_index(index, level, local, master_timeout, timeout, wait_for_active_shards, wait_for_events,
                wait_for_no_initializing_shards, wait_for_no_relocating_shards, wait_for_nodes, wait_for_status);
        return Response.Ok(JSONObject.parse(result));

    }


    /**
     * 功能:返回有关集群状态的元数据
     */
    @ApiOperation(value = "返回有关集群状态的元数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cluster/state/{metrics}/{index}")
    public Response _cluster_state(
            @ApiParam(allowableValues = "_all,blocks,master_node,metadata,nodes,routing_nodes,routing_table,version", defaultValue = "_all")
            @PathVariable(value = "metrics", required = false) String metrics,
            @ApiParam(required = false)
            @PathVariable(value = "index", required = false) String index) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._cluster_state(metrics, index);
        return Response.Ok(JSONObject.parse(result));

    }


    @ApiOperation(value = "查看index的状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_stats/{index-metric}", method = RequestMethod.GET)
    public Response _stats(
            @ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "度量", defaultValue = "_all", allowableValues = "_all,completion,fielddata,flush,get,indexing,merge,query_cache,refresh,request_cache,search,segments,store,suggest,translog,warmer")
            @PathVariable(value = "index-metric") String index_metric) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._stats(index, index_metric);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "在一个或多个索引的分片上强制合并")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_forcemerge", method = RequestMethod.POST)
    public Response _forcemerge(@ApiParam(value = "索引名称(可以用,分隔,要操作所有索引，请使用_all)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._forcemerge(index);
        return Response.Ok(JSONObject.parse(s));
    }
}
















