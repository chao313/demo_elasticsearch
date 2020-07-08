package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 集群API
 */
@FeignClient(name = "cluster", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface ClusterService {

    /**
     * 功能:获取集群范围内的设置
     *
     * @param flat_settings    是否以平面格式返回,默认是false
     * @param include_defaults 是否返回所有的cluster的设置,默认是false
     * @param master_timeout   指定连接到master节点的时间段,如果规定时候未返回，则失败，默认30s
     * @param timeout          设置等待时间,超时失败，默认是30s
     * @return
     */
    @RequestMapping(value = "/_cluster/settings", method = RequestMethod.GET)
    String _cluster_settings(@RequestParam(value = "flat_settings") Boolean flat_settings,
                             @RequestParam(value = "include_defaults") Boolean include_defaults,
                             @RequestParam(value = "master_timeout") String master_timeout,
                             @RequestParam(value = "timeout") String timeout);


    /**
     * 功能:获取集群健康状态
     *
     * @param index                           路径参数:1.逗号分割list/2.通配符表达式（可选,string）
     * @param level                           用于控制健康消息的等级;可以是 cluster/indices/shards 中的一个;（可选,bool,默认cluster）
     * @param local                           从local节点还是master节点返回数据;true代表从local节点;(可选,bool,默认false)
     * @param master_timeout                  指定连接到master节点的时间段,如果规定时候未返回，则失败，默认30s
     * @param timeout                         设置等待时间,超时失败，默认是30s
     * @param wait_for_active_shards          控制等待active分片的数量;all -> 等待所有的分片(可选,string,默认0)
     * @param wait_for_events                 等待知道所有的给予优先级的,队列的时间处理完成,可以是immediate, urgent, high, normal, low, languid中的一个(可选,string)
     * @param wait_for_no_initializing_shards 当Cluster没有初始化shard,控制是否等待,默认为false,不等待(可选,bool)
     * @param wait_for_no_relocating_shards   当Cluster没有分配shard,控制是否等待,默认为false,不等待(可选,bool)
     * @param wait_for_nodes                  设置等待指定的node的数量可用，可以接收 >=N, <=N, >N and <N 或者 ge(N), le(N), gt(N) and lt(N)(可选,string)
     * @param wait_for_status                 设置等待，直到Cluster的status变成指定的值，或者更好;green > yellow > red;(可选,string，默认不等待任何status)
     * @return json
     *
     * <pre>
     * {
     *   "cluster_name" : "testcluster",
     *   "status" : "yellow",
     *   "timed_out" : false,
     *   "number_of_nodes" : 1,
     *   "number_of_data_nodes" : 1,
     *   "active_primary_shards" : 1,
     *   "active_shards" : 1,
     *   "relocating_shards" : 0,
     *   "initializing_shards" : 0,
     *   "unassigned_shards" : 1,
     *   "delayed_unassigned_shards": 0,
     *   "number_of_pending_tasks" : 0,
     *   "number_of_in_flight_fetch": 0,
     *   "task_max_waiting_in_queue_millis": 0,
     *   "active_shards_percent_as_number": 50.0
     * }
     * </pre>
     */
    @RequestMapping(value = "/_cluster/health/{index}", method = RequestMethod.GET)
    String _cluster_health(@PathVariable(value = "index") String index,
                           @RequestParam(value = "level") String level,
                           @RequestParam(value = "local") Boolean local,
                           @RequestParam(value = "master_timeout") String master_timeout,
                           @RequestParam(value = "timeout") String timeout,
                           @RequestParam(value = "wait_for_active_shards") String wait_for_active_shards,
                           @RequestParam(value = "wait_for_events") String wait_for_events,
                           @RequestParam(value = "wait_for_no_initializing_shards") Boolean wait_for_no_initializing_shards,
                           @RequestParam(value = "wait_for_no_relocating_shards") Boolean wait_for_no_relocating_shards,
                           @RequestParam(value = "wait_for_nodes") String wait_for_nodes,
                           @RequestParam(value = "wait_for_status") String wait_for_status);
}
