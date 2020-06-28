package demo.elastic.search.feign;

import demo.elastic.search.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
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

@FeignClient(name = "cat", url = "http://39.107.236.187:9200/", configuration = FeignServiceConfig.class)
//@FeignClient(name = "es", url = "http://127.0.0.1:80/", configuration = FeignServiceConfig.class)
public interface CatService {

    /**
     * @return
     */
    @RequestMapping(value = "/_cat", method = RequestMethod.GET)
    String _cat();

    @RequestMapping(value = "/_cat/allocation", method = RequestMethod.GET)
    String _cat_allocation(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/shards", method = RequestMethod.GET)
    String _cat_shards(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/master", method = RequestMethod.GET)
    String _cat_master(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/nodes", method = RequestMethod.GET)
    String _cat_nodes(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/indices", method = RequestMethod.GET)
    String _cat_indices(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/segments", method = RequestMethod.GET)
    String _cat_segments(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/count", method = RequestMethod.GET)
    String _cat_count(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/recovery", method = RequestMethod.GET)
    String _cat_recovery(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/health", method = RequestMethod.GET)
    String _cat_health(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/pending_tasks", method = RequestMethod.GET)
    String _cat_pending_tasks(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/aliases", method = RequestMethod.GET)
    String _cat_aliases(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/thread_pool", method = RequestMethod.GET)
    String _cat_thread_pool(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/plugins", method = RequestMethod.GET)
    String _cat_plugins(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/fielddata", method = RequestMethod.GET)
    String _cat_fielddata(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/nodeattrs", method = RequestMethod.GET)
    String _cat_nodeattrs(@RequestParam(value = "v") String v);


    @RequestMapping(value = "/_cat/repositories", method = RequestMethod.GET)
    String _cat_repositories(@RequestParam(value = "v") String v);

    @RequestMapping(value = "/_cat/templates", method = RequestMethod.GET)
    String _cat_templates(@RequestParam(value = "v") String v);


}
