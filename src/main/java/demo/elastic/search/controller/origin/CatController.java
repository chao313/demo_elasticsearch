//package demo.elastic.search.controller.origin;
//
//import demo.elastic.search.config.Bootstrap;
//import demo.elastic.search.config.web.CustomInterceptConfig;
//import demo.elastic.search.feign.CatService;
//import demo.elastic.search.thread.ThreadLocalFeign;
//import io.swagger.annotations.ApiImplicitParam;
//import io.swagger.annotations.ApiImplicitParams;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import org.springframework.web.bind.annotation.*;
//
//
///**
// * 用于 ElasticSearch es使用
// */
//@RequestMapping(value = "/origin/CatController")
//@RestController
//public class CatController {
//
//    private CatService catService;
//
//    /**
//     * 列出CAT的全部接口
//     *
//     * @return
//     */
//    @ApiOperation(value = "列出Cat的全部接口", notes = "列出CAT的全部接口")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @GetMapping(value = "/_cat")
//    public String cat() {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat();
//    }
//
////
////    @ApiOperation(value = "列出当前配置的index的alias,包括filter和router的信息")
////    @ApiImplicitParams(value = {
////            @ApiImplicitParam(
////                    name = CustomInterceptConfig.HEADER_KEY,
////                    value = Bootstrap.EXAMPLE,
////                    dataType = "string",
////                    paramType = "header",
////                    defaultValue = Bootstrap.DEFAULT_VALUE)
////    })
////    @GetMapping(value = "/_cat/aliases")
////    public String _cat_aliases(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
////        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
////        return catService._cat_aliases(v);
////    }
////
//
////    @ApiOperation(value = "列出分配给每个数据节点的分片数量及其磁盘空间的快照")
////    @ApiImplicitParams(value = {
////            @ApiImplicitParam(
////                    name = CustomInterceptConfig.HEADER_KEY,
////                    value = Bootstrap.EXAMPLE,
////                    dataType = "string",
////                    paramType = "header",
////                    defaultValue = Bootstrap.DEFAULT_VALUE)
////    })
////    @GetMapping(value = "/_cat/allocation")
////    public String _cat_allocation(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
////        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
////        return catService._cat_allocation(v);
////    }
//
//
//    @ApiOperation(value = "列出指定数据节点的分片数量及其磁盘空间的快照 (e.g. dataNode-dwserver18-4)")
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @GetMapping(value = "/_cat/allocation/{node_id}")
//    public String _cat_allocation_nodeId(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                                         @ApiParam(value = "节点Id") @PathVariable(value = "node_id") String node_id) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_allocation_nodeId(v, node_id);
//    }
//
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "提供对单个索引或集群中所有索引的文档计数的快速访问")
//    @GetMapping(value = "/_cat/count/{index}")
//    public String _cat_count(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                             @PathVariable(value = "index") String index) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_count(v, index);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "列出集群中每个数据节点上的字段数据 当前 使用的堆内存量")
//    @GetMapping(value = "/_cat/fielddata")
//    public String _cat_fielddata(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_fielddata(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "列出集群中每个数据节点上的字段数据 当前 使用的堆内存量")
//    @GetMapping(value = "/_cat/fielddata/{field}")
//    public String _cat_fielddata(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                                 @PathVariable(value = "field") String field) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_fielddata_field(v, field);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回集群的运行状况")
//    @GetMapping(value = "/_cat/health")
//    public String _cat_health(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_health(v);
//    }
//
////    @ApiImplicitParams(value = {
////            @ApiImplicitParam(
////                    name = CustomInterceptConfig.HEADER_KEY,
////                    value = Bootstrap.EXAMPLE,
////                    dataType = "string",
////                    paramType = "header",
////                    defaultValue = Bootstrap.DEFAULT_VALUE)
////    })
////    @ApiOperation(value = "列出index")
////    @GetMapping(value = "/_cat/indices")
////    public String _cat_indices(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
////        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
////        return catService._cat_indices(v);
////    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "列出index(指定)")
//    @GetMapping(value = "/_cat/indices/{index}")
//    public String _cat_indices_index(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                                     @PathVariable(value = "index") String index) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_indices_index(v, index);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回master节点的信息，包括ID绑定IP地址和名称")
//    @GetMapping(value = "/_cat/master")
//    public String _cat_master(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_master(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回有关自定义节点属性的信息")
//    @GetMapping(value = "/_cat/nodeattrs")
//    public String _cat_nodeattrs(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_nodeattrs(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回有关群集节点的信息")
//    @GetMapping(value = "/_cat/nodes")
//    public String _cat_nodes(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                             @ApiParam(value = "要显示的以逗号分隔的列名称列表(ip,cpu,port...)") @RequestParam(value = "h") String h) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_nodes(v, h);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回正在挂起的task")
//    @GetMapping(value = "/_cat/pending_tasks")
//    public String _cat_pending_tasks(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_pending_tasks(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回一个群集的每个节点上运行的插件的列表")
//    @GetMapping(value = "/_cat/plugins")
//    public String _cat_plugins(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_plugins(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回有关正在进行的和已完成的碎片恢复的信息")
//    @GetMapping(value = "/_cat/recovery")
//    public String _cat_recovery(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_recovery(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回有关正在进行的和已完成的碎片恢复的信息")
//    @GetMapping(value = "/_cat/recovery/{index}")
//    public String _cat_recovery_index(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                                      @PathVariable(value = "index") String index) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_recovery_index(v, index);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回快照仓库")
//    @GetMapping(value = "/_cat/repositories")
//    public String _cat_repositories(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_repositories(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回分片信息", notes = "返回节点包含哪些分片,是主分片还是复制分片,doc的数量,使用的磁盘空间")
//    @GetMapping(value = "/_cat/shards")
//    public String _cat_shards(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                              @ApiParam(value = "要显示的以逗号分隔的列名称列表(index,shard,prirep...)") @RequestParam(value = "h") String h) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_shards(v, h);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回分片信息", notes = "返回节点包含哪些分片,是主分片还是复制分片,doc的数量,使用的磁盘空间")
//    @GetMapping(value = "/_cat/shards/{index}")
//    public String _cat_shards(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                              @PathVariable(value = "index") String index,
//                              @ApiParam(value = "要显示的以逗号分隔的列名称列表(index,shard,prirep...)") @RequestParam(value = "h") String h) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_shards_index(v, index, h);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回index中的低级关于Lucene段的信息碎片")
//    @GetMapping(value = "/_cat/segments")
//    public String _cat_segments(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_segments(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回index中的低级关于Lucene段的信息碎片")
//    @GetMapping(value = "/_cat/segments/{index}")
//    public String _cat_segments_index(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                                      @PathVariable(value = "index") String index) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_segments_index(v, index);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回信息有关快照存储在一个或多个存储库")
//    @GetMapping(value = "/_cat/snapshots/{repository}")
//    public String _cat_snapshots(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                                 @PathVariable(value = "repository") String repository) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_snapshots(v, repository);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回有关在群集中当前正在执行的任务的信息")
//    @RequestMapping(value = "/_cat/tasks", method = RequestMethod.GET)
//    public String _cat_tasks(@ApiParam(value = "是否格式化") @RequestParam(value = "v", defaultValue = "true") Boolean v,
//                             @RequestParam(value = "detailed") Boolean detailed) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_tasks(v, detailed);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回群集中的index template的信息")
//    @GetMapping(value = "/_cat/templates")
//    public String _cat_templates(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_templates(v);
//    }
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @ApiOperation(value = "返回群集中的index template的信息")
//    @GetMapping(value = "/_cat/templates/{template_name}")
//    public String _cat_templates(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
//                                 @PathVariable(value = "template_name") String template_name) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_templates(v, template_name);
//    }
//
////    @ApiOperation(value = "提供对单个索引或集群中所有索引的文档计数的快速访问")
////    @ApiImplicitParams(value = {
////            @ApiImplicitParam(
////                    name = CustomInterceptConfig.HEADER_KEY,
////                    value = Bootstrap.EXAMPLE,
////                    dataType = "string",
////                    paramType = "header",
////                    defaultValue = Bootstrap.DEFAULT_VALUE)
////    })
////    @GetMapping(value = "/_cat/count")
////    public String _cat_count(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
////        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
////        return catService._cat_count(v);
////    }
//
//
//    @ApiImplicitParams(value = {
//            @ApiImplicitParam(
//                    name = CustomInterceptConfig.HEADER_KEY,
//                    value = Bootstrap.EXAMPLE,
//                    dataType = "string",
//                    paramType = "header",
//                    defaultValue = Bootstrap.DEFAULT_VALUE)
//    })
//    @GetMapping(value = "/_cat/thread_pool")
//    public String _cat_thread_pool(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
//        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
//        return catService._cat_thread_pool(v);
//    }
//
//
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
