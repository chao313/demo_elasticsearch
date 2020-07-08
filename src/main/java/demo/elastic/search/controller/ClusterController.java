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
//
//    @GetMapping(value = "/_cat/allocation")
//    public String _cat_allocation(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_allocation(Boolean.toString(v));
//        return s;
//    }
//
//
//    @GetMapping(value = "/_cat/shards")
//    public String _cat_shards(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_shards(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/master")
//    public String _cat_master(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_master(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/nodes")
//    public String _cat_nodes(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_nodes(Boolean.toString(v));
//        return s;
//    }
//
//
//    @GetMapping(value = "/_cat/indices")
//    public String _cat_indices(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_indices(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/segments")
//    public String _cat_segments(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_segments(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/count")
//    public String _cat_count(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_count(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/recovery")
//    public String _cat_recovery(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_recovery(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/health")
//    public String _cat_health(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_health(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/pending_tasks")
//    public String _cat_pending_tasks(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_pending_tasks(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/aliases")
//    public String _cat_aliases(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_aliases(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/thread_pool")
//    public String _cat_thread_pool(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_thread_pool(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/plugins")
//    public String _cat_plugins(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_plugins(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/fielddata")
//    public String _cat_fielddata(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_fielddata(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/nodeattrs")
//    public String _cat_nodeattrs(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_nodeattrs(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/repositories")
//    public String _cat_repositories(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_repositories(Boolean.toString(v));
//        return s;
//    }
//
//    @GetMapping(value = "/_cat/templates")
//    public String _cat_templates(
//            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
//            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
//                    String elasticSearchHost,
//            @ApiParam(value = "是否格式化")
//            @RequestParam(name = "v", defaultValue = "true")
//                    boolean v
//    ) {
//        String s = catService._cat_templates(Boolean.toString(v));
//        return s;
//    }


}
















