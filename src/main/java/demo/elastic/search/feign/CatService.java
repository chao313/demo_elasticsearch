package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * /_cat/allocation             #查看单节点的shard分配整体情况
 * /_cat/shards                 #查看各shard的详细情况
 * /_cat/shards/{index}         #查看指定分片的详细情况
 * /_cat/master                 #查看master节点信息
 * /_cat/nodes                  #查看所有节点信息
 * /_cat/indices                #查看集群中所有index的详细信息
 * /_cat/indices/{index}        #查看集群中指定index的详细信息
 * /_cat/segments               #查看各index的segment详细信息,包括segment名, 所属shard, 内存(磁盘)占用大小, 是否刷盘
 * /_cat/segments/{index}       #查看指定index的segment详细信息
 * /_cat/count                  #查看当前集群的doc数量
 * /_cat/count/{index}          #查看指定索引的doc数量
 * /_cat/recovery               #查看集群内每个shard的recovery过程.调整replica。
 * /_cat/recovery/{index}       #查看指定索引shard的recovery过程
 * /_cat/health                 #查看集群当前状态：红、黄、绿
 * /_cat/pending_tasks          #查看当前集群的pending task
 * /_cat/aliases                #查看集群中所有alias信息,路由配置等
 * /_cat/aliases/{alias}        #查看指定索引的alias信息
 * /_cat/thread_pool            #查看集群各节点内部不同类型的threadpool的统计信息,
 * /_cat/plugins                #查看集群各个节点上的plugin信息
 * /_cat/fielddata              #查看当前集群各个节点的fielddata内存使用情况
 * /_cat/fielddata/{fields}     #查看指定field的内存使用情况,里面传field属性对应的值
 * /_cat/nodeattrs              #查看单节点的自定义属性
 * /_cat/repositories           #输出集群中注册快照存储库
 * /_cat/templates              #输出当前正在存在的模板信息
 */

@FeignClient(name = "cat", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface CatService {

    @ApiOperation(value = "列出Cat的全部接口")
    @RequestMapping(value = "/_cat", method = RequestMethod.GET)
    String _cat();

    @ApiOperation(value = "列出当前配置的index的alias,包括filter和router的信息")
    @RequestMapping(value = "/_cat/aliases", method = RequestMethod.GET)
    String _cat_aliases(@RequestParam(value = "v") Boolean v);

    @ApiOperation(value = "列出分配给每个数据节点的分片数量及其磁盘空间的快照")
    @RequestMapping(value = "/_cat/allocation", method = RequestMethod.GET)
    String _cat_allocation(@RequestParam(value = "v") Boolean v);

    @ApiOperation(value = "列出单个索引或集群中所有索引的文档计数的快速访问")
    @RequestMapping(value = "/_cat/count", method = RequestMethod.GET)
    String _cat_count(@RequestParam(value = "v") Boolean v);

    @ApiOperation(value = "列出单个索引或集群中所有索引的文档计数的快速访问")
    @RequestMapping(value = "/_cat/count/{index}", method = RequestMethod.GET)
    String _cat_count(@RequestParam(value = "v") Boolean v,
                      @PathVariable(value = "index") String index);

    @ApiOperation(value = "列出集群中每个数据节点上的字段数据当前使用的堆内存量")
    @RequestMapping(value = "/_cat/fielddata", method = RequestMethod.GET)
    String _cat_fielddata(@RequestParam(value = "v") Boolean v);

    @ApiOperation(value = "列出集群中每个数据节点上的字段数据当前使用的堆内存量")
    @RequestMapping(value = "/_cat/fielddata/{field}", method = RequestMethod.GET)
    String _cat_fielddata_field(@RequestParam(value = "v") Boolean v,
                                @PathVariable(value = "field") String field);

    @ApiOperation(value = "返回集群的运行状况")
    @RequestMapping(value = "/_cat/health", method = RequestMethod.GET)
    String _cat_health(@RequestParam(value = "v") Boolean v);


    @ApiOperation(value = "列出index")
    @RequestMapping(value = "/_cat/indices", method = RequestMethod.GET)
    String _cat_indices(@RequestParam(value = "v") Boolean v);

    @ApiOperation(value = "列出index")
    @RequestMapping(value = "/_cat/indices/{index}", method = RequestMethod.GET)
    String _cat_indices_index(@RequestParam(value = "v") Boolean v,
                              @PathVariable(value = "index") String index);

    @RequestMapping(value = "/_cat/shards", method = RequestMethod.GET)
    String _cat_shards(@RequestParam(value = "v") Boolean v);

    @RequestMapping(value = "/_cat/master", method = RequestMethod.GET)
    String _cat_master(@RequestParam(value = "v") Boolean v);

    @RequestMapping(value = "/_cat/nodes", method = RequestMethod.GET)
    String _cat_nodes(@RequestParam(value = "v") Boolean v);

    @RequestMapping(value = "/_cat/segments", method = RequestMethod.GET)
    String _cat_segments(@RequestParam(value = "v") Boolean v);


    @RequestMapping(value = "/_cat/recovery", method = RequestMethod.GET)
    String _cat_recovery(@RequestParam(value = "v") Boolean v);


    @RequestMapping(value = "/_cat/pending_tasks", method = RequestMethod.GET)
    String _cat_pending_tasks(@RequestParam(value = "v") Boolean v);


    @RequestMapping(value = "/_cat/thread_pool", method = RequestMethod.GET)
    String _cat_thread_pool(@RequestParam(value = "v") Boolean v);

    @RequestMapping(value = "/_cat/plugins", method = RequestMethod.GET)
    String _cat_plugins(@RequestParam(value = "v") Boolean v);


    @RequestMapping(value = "/_cat/nodeattrs", method = RequestMethod.GET)
    String _cat_nodeattrs(@RequestParam(value = "v") Boolean v);


    @RequestMapping(value = "/_cat/repositories", method = RequestMethod.GET)
    String _cat_repositories(@RequestParam(value = "v") Boolean v);

    @RequestMapping(value = "/_cat/templates", method = RequestMethod.GET)
    String _cat_templates(@RequestParam(value = "v") Boolean v);


}
