package demo.elastic.search.controller;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.feign.CatService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch es使用
 */
@RequestMapping(value = "/CatController")
@RestController
public class CatController {

    @Resource
    private CatService catService;

    /**
     * 列出CAT的全部接口
     *
     * @return
     */
    @ApiOperation(value = "列出Cat的全部接口", notes = "列出CAT的全部接口")
    @GetMapping(value = "/_cat")
    public String cat() {
        return catService._cat();
    }

    @ApiOperation(value = "列出当前配置的index的alias,包括filter和router的信息")
    @GetMapping(value = "/_cat/aliases")
    public String _cat_aliases(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_aliases(v);
    }

    @ApiOperation(value = "列出分配给每个数据节点的分片数量及其磁盘空间的快照")
    @GetMapping(value = "/_cat/allocation")
    public String _cat_allocation(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_allocation(v);
    }

    @ApiOperation(value = "提供对单个索引或集群中所有索引的文档计数的快速访问")
    @GetMapping(value = "/_cat/count")
    public String _cat_count(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_count(v);
    }

    @ApiOperation(value = "提供对单个索引或集群中所有索引的文档计数的快速访问")
    @GetMapping(value = "/_cat/count/{index}")
    public String _cat_count(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                             @PathVariable(value = "index") String index) {
        return catService._cat_count(v, index);
    }

    @ApiOperation(value = "列出集群中每个数据节点上的字段数据 当前 使用的堆内存量")
    @GetMapping(value = "/_cat/fielddata")
    public String _cat_fielddata(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_fielddata(v);
    }

    @ApiOperation(value = "列出集群中每个数据节点上的字段数据 当前 使用的堆内存量")
    @GetMapping(value = "/_cat/fielddata/{field}")
    public String _cat_fielddata(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                 @PathVariable(value = "field") String field) {
        return catService._cat_fielddata_field(v, field);
    }

    @ApiOperation(value = "返回集群的运行状况")
    @GetMapping(value = "/_cat/health")
    public String _cat_health(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_health(v);
    }

    @ApiOperation(value = "列出index")
    @GetMapping(value = "/_cat/indices")
    public String _cat_indices(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_indices(v);
    }

    @ApiOperation(value = "列出index(指定)")
    @GetMapping(value = "/_cat/indices/{index}")
    public String _cat_indices_index(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                     @PathVariable(value = "index") String index) {
        return catService._cat_indices_index(v, index);
    }

    @GetMapping(value = "/_cat/shards")
    public String _cat_shards(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_shards(v);
    }

    @GetMapping(value = "/_cat/master")
    public String _cat_master(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_master(v);
    }

    @GetMapping(value = "/_cat/nodes")
    public String _cat_nodes(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_nodes(v);
    }


    @GetMapping(value = "/_cat/segments")
    public String _cat_segments(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_segments(v);
    }


    @GetMapping(value = "/_cat/recovery")
    public String _cat_recovery(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_recovery(v);
    }


    @GetMapping(value = "/_cat/pending_tasks")
    public String _cat_pending_tasks(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_pending_tasks(v);
    }


    @GetMapping(value = "/_cat/thread_pool")
    public String _cat_thread_pool(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_thread_pool(v);
    }

    @GetMapping(value = "/_cat/plugins")
    public String _cat_plugins(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_plugins(v);
    }


    @GetMapping(value = "/_cat/nodeattrs")
    public String _cat_nodeattrs(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_nodeattrs(v);
    }

    @GetMapping(value = "/_cat/repositories")
    public String _cat_repositories(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_repositories(v);
    }

    @GetMapping(value = "/_cat/templates")
    public String _cat_templates(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        return catService._cat_templates(v);
    }


}
















