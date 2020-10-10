package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.feign.enums.FormatEnum;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/cat.html"></a>
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

    @ApiOperation(value = "ES的base请求")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    String _base();

    @ApiOperation(value = "列出Cat的全部接口")
    @RequestMapping(value = "/_cat", method = RequestMethod.GET)
    String _cat();

    @ApiOperation(value = "列出当前配置的index的alias,包括filter和router的信息")
    @RequestMapping(value = "/_cat/aliases", method = RequestMethod.GET)
    String _cat_aliases(@RequestParam(value = "v") Boolean v,
                        @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "列出分配给每个数据节点的分片数量及其磁盘空间的快照")
    @RequestMapping(value = "/_cat/allocation", method = RequestMethod.GET)
    String _cat_allocation(@RequestParam(value = "v") Boolean v,
                           @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "列出指定数据节点的分片数量及其磁盘空间的快照")
    @RequestMapping(value = "/_cat/allocation/{node_id}", method = RequestMethod.GET)
    String _cat_allocation_nodeId(@RequestParam(value = "v") Boolean v,
                                  @PathVariable(value = "node_id") String node_id,
                                  @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "列出单个索引或集群中所有索引的文档计数的快速访问")
    @RequestMapping(value = "/_cat/count", method = RequestMethod.GET)
    String _cat_count(@RequestParam(value = "v") Boolean v,
                      @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "列出单个索引或集群中所有索引的文档计数的快速访问")
    @RequestMapping(value = "/_cat/count/{index}", method = RequestMethod.GET)
    String _cat_count(@RequestParam(value = "v") Boolean v,
                      @PathVariable(value = "index") String index,
                      @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "列出集群中每个数据节点上的字段数据当前使用的堆内存量")
    @RequestMapping(value = "/_cat/fielddata", method = RequestMethod.GET)
    String _cat_fielddata(@RequestParam(value = "v") Boolean v,
                          @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "列出集群中每个数据节点上的字段数据当前使用的堆内存量")
    @RequestMapping(value = "/_cat/fielddata/{field}", method = RequestMethod.GET)
    String _cat_fielddata_field(@RequestParam(value = "v") Boolean v,
                                @PathVariable(value = "field") String field,
                                @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回集群的运行状况")
    @RequestMapping(value = "/_cat/health", method = RequestMethod.GET)
    String _cat_health(@RequestParam(value = "v") Boolean v,
                       @RequestParam(value = "format") FormatEnum formatEnum);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/cat-indices.html"></a>
     * health status index                            pri rep docs.count docs.deleted store.size pri.store.size
     * green  open   comstore_tb_object_6891_v2         6   1     675393        10761    464.4mb        232.2mb
     * green  open   comstore_tb_object_6570_v2         6   1      61880           10       49mb         24.5mb\
     * 可以获得全部索引的
     * 健康状况，索引状态，主分片数量，复制分片数量，文档数量，文档删除数量，索引的存储size，主分片的存储size
     */
    @ApiOperation(value = "列出index")
    @RequestMapping(value = "/_cat/indices", method = RequestMethod.GET)
    String _cat_indices(@RequestParam(value = "v") Boolean v,
                        @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "列出index")
    @RequestMapping(value = "/_cat/indices/{index}", method = RequestMethod.GET)
    String _cat_indices_index(@RequestParam(value = "v") Boolean v,
                              @PathVariable(value = "index") String index,
                              @RequestParam(value = "format") FormatEnum formatEnum);

    /**
     * id                     host         ip           node
     * erCsPp8sQa-P5TfEqbeW0A 10.200.6.168 10.200.6.168 masterNode-dwserver18-1
     */
    @ApiOperation(value = "返回master节点的信息，包括ID绑定IP地址和名称")
    @RequestMapping(value = "/_cat/master", method = RequestMethod.GET)
    String _cat_master(@RequestParam(value = "v") Boolean v,
                       @RequestParam(value = "format") FormatEnum formatEnum);

    /**
     * node                    host         ip           attr   value
     * dataNode-dwserver18-3   10.200.6.168 10.200.6.168 master false
     * masterNode-dwserver17-1 10.200.2.204 10.200.2.204 data   false
     * masterNode-dwserver17-1 10.200.2.204 10.200.2.204 master true
     */
    @ApiOperation(value = "返回有关自定义节点属性的信息")
    @RequestMapping(value = "/_cat/nodeattrs", method = RequestMethod.GET)
    String _cat_nodeattrs(@RequestParam(value = "v") Boolean v,
                          @RequestParam(value = "format") FormatEnum formatEnum);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/cat-nodes.html"></a>
     * ip， i
     * （默认）IP地址，例如127.0.1.1。
     * heap.percent，hp，heapPercent
     * （默认）最大配置堆，例如7。
     * ram.percent，rp，ramPercent
     * （默认）已使用的总内存百分比，例如47。
     * file_desc.percent，fdp，fileDescriptorPercent
     * （默认）使用的文件描述符百分比，例如1。
     * node.role，r，role，nodeRole
     * （默认）节点的角色。返回的值包括d（数据节点），i （目标节点），m（主资格节点），l（机器学习节点），v （仅投票节点），t（转换节点），r（远程集群客户端节点）和-（仅协调节点） ）。
     * <p>
     * 例如，dim指示符合主机资格的数据和摄取节点。参见 Node。
     * <p>
     * master， m
     * （默认值）指示该节点是否为当选的主节点。返回的值包括*（当选的主服务器）和-（当选的主服务器）。
     * name， n
     * （默认）节点名称，例如I8hydUG。
     * id， nodeId
     * 节点的ID，例如k0zy。
     * pid， p
     * 进程ID，例如13061。
     * port， po
     * 绑定的运输港口，例如9300。
     * http_address， http
     * 绑定的http地址，例如127.0.0.1:9200。
     * version， v
     * Elasticsearch版本，例如7.9.1。
     * build， b
     * Elasticsearch构建哈希，例如5c03844。
     * jdk， j
     * Java版本，例如1.8.0。
     * disk.total，dt，diskTotal
     * 总磁盘空间，例如458.3gb。
     * disk.used，du，diskUsed
     * 已使用的磁盘空间，例如259.8gb。
     * disk.avail，d，disk，diskAvail
     * 可用磁盘空间，例如198.4gb。
     * disk.used_percent，dup，diskUsedPercent
     * 已用磁盘空间百分比，例如47。
     * heap.current，hc，heapCurrent
     * 已用堆，例如311.2mb。
     * ram.current，rc，ramCurrent
     * 已用的总内存，例如513.4mb。
     * ram.max，rm，ramMax
     * 总内存，例如2.9gb。
     * file_desc.current，fdc，fileDescriptorCurrent
     * 使用的文件描述符，例如123。
     * file_desc.max，fdm，fileDescriptorMax
     * 文件描述符的最大数量，例如1024。
     * cpu
     * 最近的系统CPU使用率，例如百分比12。
     * load_1m， l
     * 最近的平均负载，例如0.22。
     * load_5m， l
     * 最近五分钟的平均负载，例如0.78。
     * load_15m， l
     * 最近15分钟的平均负载，例如1.24。
     * uptime， u
     * 节点正常运行时间，例如17.3m。
     * completion.size，cs，completionSize
     * 完成大小，例如0b。
     * fielddata.memory_size，fm，fielddataMemory
     * 已使用的字段数据缓存存储器，例如0b。
     * fielddata.evictions，fe，fielddataEvictions
     * 字段数据缓存逐出，例如0。
     * query_cache.memory_size，qcm，queryCacheMemory
     * 已使用的查询缓存存储器，例如0b。
     * query_cache.evictions，qce，queryCacheEvictions
     * 查询缓存逐出，例如0。
     * request_cache.memory_size，rcm，requestCacheMemory
     * 已使用的请求缓存存储器，例如0b。
     * request_cache.evictions，rce，requestCacheEvictions
     * 请求缓存逐出，例如0。
     * request_cache.hit_count，rchc，requestCacheHitCount
     * 请求缓存命中计数，例如0。
     * request_cache.miss_count，rcmc，requestCacheMissCount
     * 请求缓存未命中计数，例如0。
     * flush.total，ft，flushTotal
     * 冲洗次数，例如1。
     * flush.total_time，ftt，flushTotalTime
     * 花在冲洗上的时间，例如1。
     * get.current，gc，getCurrent
     * 当前获取操作的数量，例如0。
     * get.time，gti，getTime
     * 花费的时间，例如14ms。
     * get.total，gto，getTotal
     * 获取操作的数量，例如2。
     * get.exists_time，geti，getExistsTime
     * 成功获取所花费的时间，例如14ms。
     * get.exists_total，geto，getExistsTotal
     * 成功执行get操作的次数，例如2。
     * get.missing_time，gmti，getMissingTime
     * 花费失败的时间，例如0s。
     * get.missing_total，gmto，getMissingTotal
     * 获取操作失败的次数，例如1。
     * indexing.delete_current，idc，indexingDeleteCurrent
     * 当前删除操作的数量，例如0。
     * indexing.delete_time，idti，indexingDeleteTime
     * 删除所花费的时间，例如2ms。
     * indexing.delete_total，idto，indexingDeleteTotal
     * 删除操作的数量，例如2。
     * indexing.index_current，iic，indexingIndexCurrent
     * 当前索引操作的数量，例如0。
     * indexing.index_time，iiti，indexingIndexTime
     * 用于建立索引的时间，例如134ms。
     * indexing.index_total，iito，indexingIndexTotal
     * 索引操作的数量，例如1。
     * indexing.index_failed，iif，indexingIndexFailed
     * 索引操作失败的次数，例如0。
     * merges.current，mc，mergesCurrent
     * 当前合并操作的数量，例如0。
     * merges.current_docs，mcd，mergesCurrentDocs
     * 当前合并文档的数量，例如0。
     * merges.current_size，mcs，mergesCurrentSize
     * 当前合并的大小，例如0b。
     * merges.total，mt，mergesTotal
     * 完成的合并操作的数量，例如0。
     * merges.total_docs，mtd，mergesTotalDocs
     * 合并文档的数量，例如0。
     * merges.total_size，mts，mergesTotalSize
     * 当前合并的大小，例如0b。
     * merges.total_time，mtt，mergesTotalTime
     * 合并文档所花费的时间，例如0s。
     * refresh.total，rto，refreshTotal
     * 刷新次数，例如16。
     * refresh.time，rti，refreshTime
     * 刷新所花费的时间，例如91ms。
     * script.compilations，scrcc，scriptCompilations
     * 脚本编译总数，例如17。
     * script.cache_evictions，scrce，scriptCacheEvictions
     * 从缓存中逐出的已编译脚本总数，例如6。
     * search.fetch_current，sfc，searchFetchCurrent
     * 当前的提取阶段操作，例如0。
     * search.fetch_time，sfti，searchFetchTime
     * 在抓取阶段花费的时间，例如37ms。
     * search.fetch_total，sfto，searchFetchTotal
     * 提取操作的数量，例如7。
     * search.open_contexts，so，searchOpenContexts
     * 打开搜索上下文，例如0。
     * search.query_current，sqc，searchQueryCurrent
     * 当前的查询阶段操作，例如0。
     * search.query_time，sqti，searchQueryTime
     * 在查询阶段花费的时间，例如43ms。
     * search.query_total，sqto，searchQueryTotal
     * 查询操作的数量，例如9。
     * search.scroll_current，scc，searchScrollCurrent
     * 打开滚动上下文，例如2。
     * search.scroll_time，scti，searchScrollTime
     * 时间滚动上下文保持打开状态，例如2m。
     * search.scroll_total，scto，searchScrollTotal
     * 完成滚动上下文，例如1。
     * segments.count，sc，segmentsCount
     * 段数，例如4。
     * segments.memory，sm，segmentsMemory
     * 段使用的内存，例如1.4kb。
     * segments.index_writer_memory，siwm，segmentsIndexWriterMemory
     * 索引编写器使用的内存，例如18mb。
     * segments.version_map_memory，svmm，segmentsVersionMapMemory
     * 版本映射使用的内存，例如1.0kb。
     * segments.fixed_bitset_memory，sfbm，fixedBitsetMemory
     * 固定位集使用的内存用于嵌套对象字段类型，而类型过滤器用于join字段中引用的类型，例如1.0kb。
     * suggest.current，suc，suggestCurrent
     * 当前建议操作的数量，例如0。
     * suggest.time，suti，suggestTime
     * 建议所花费的时间，例如0。
     * suggest.total，suto，suggestTotal
     * 建议操作的数量，例如0。
     *
     * <pre>
     * {
     *  "build": "d34da0ea4a966c4e49417f2da2f244e3e97b4e6e",//Elasticsearch构建哈希
     *  "completion.size": "0b",//完成大小
     *  "cpu": "37",//节点最近的系统CPU使用率
     *  "disk.avail": "34.7gb",//可用磁盘空间
     *  "disk.total": "232.5gb",//节点总磁盘空间
     *  "disk.used": "197.7gb",//节点已使用的磁盘空间
     *  "disk.used_percent": "85.04",//已用磁盘空间百分比
     *  "fielddata.evictions": "0",//字段数据缓存逐出
     *  "fielddata.memory_size": "0b",//已使用的字段数据缓存存储器
     *  "file_desc.current": "290",//节点当前文件描述
     *  "file_desc.max": "10240",//节点最大文件描述
     *  "file_desc.percent": "2",//节点使用的文件描述符百分比
     *  "flavor": "default",//节点分支
     *  "flush.total": "16",//冲洗次数
     *  "flush.total_time": "1.8s",//花在冲洗上的时间
     *  "get.current": "0",//当前获取操作的数量
     *  "get.exists_time": "154ms",//成功获取所花费的时间
     *  "get.exists_total": "2",//成功执行get操作的次数
     *  "get.missing_time": "0s",//花费失败的时间
     *  "get.missing_total": "0",//获取操作失败的次数
     *  "get.time": "154ms",//花费的时间
     *  "get.total": "2",//获取操作的数量
     *  "heap.current": "481.9mb",//已用堆
     *  "heap.max": "1gb",
     *  "heap.percent": "47",//节点最大配置堆
     *  "http_address": "127.0.0.1:9202",//节点http地址
     *  "id": "m8L3",
     *  "indexing.delete_current": "0",//当前删除操作的数量
     *  "indexing.delete_time": "202ms",//删除所花费的时间
     *  "indexing.delete_total": "1",//indexing.delete_total
     *  "indexing.index_current": "0",//当前索引操作的数量
     *  "indexing.index_failed": "0",//索引操作失败的次数
     *  "indexing.index_time": "2.8s",//用于建立索引的时间
     *  "indexing.index_total": "12113",//索引操作的数量
     *  "ip": "127.0.0.1",//节点ip
     *  "jdk": "15",//节点JDK版本
     *  "load_1m": "4.30",//最近的平均负载
     *  "master": "*",//节点是否为当选的主节点。返回的值包括*（当选的主服务器）和-（当选的主服务器
     *  "merges.current": "0",//当前合并操作的数量
     *  "merges.current_docs": "0",//当前合并文档的数量
     *  "merges.current_size": "0b",//当前合并的大小
     *  "merges.total": "0",//完成的合并操作的次数
     *  "merges.total_docs": "0",//合并文档的数量
     *  "merges.total_size": "0b",//当前合并的大小
     *  "merges.total_time": "0s",//合并文档所花费的时间
     *  "name": "chaodeMacBook-Pro.local",//节点主机名称
     *  "node.role": "dilmrt",//节点角色 节点的角色。返回的值包括d（数据节点），i （目标节点），m（主资格节点），l（机器学习节点），v （仅投票节点），t（转换节点），r（远程集群客户端节点）和-（仅协调节点） ）。
     *  "pid": "9059",//节点ES进程号
     *  "port": "9300",//节点端口号
     *  "query_cache.evictions": "0",//查询缓存逐出
     *  "query_cache.memory_size": "0b",//已使用的查询缓存存储器
     *  "ram.current": "7.9gb",//已用的总内存
     *  "ram.max": "8gb",//总内存
     *  "ram.percent": "100",//已使用的总内存百分比
     *  "refresh.external_time": "3.4s",
     *  "refresh.external_total": "86",
     *  "refresh.listeners": "0",
     *  "refresh.time": "3.5s",//刷新所花费的时间，
     *  "refresh.total": "102",//刷新次数
     *  "request_cache.evictions": "0",//请求缓存逐出
     *  "request_cache.hit_count": "477",//请求缓存命中计数
     *  "request_cache.memory_size": "8.9kb",//已使用的请求缓存存储器
     *  "request_cache.miss_count": "13",//请求缓存未命中计数
     *  "script.cache_evictions": "0",//从缓存中逐出的已编译脚本总数
     *  "script.compilation_limit_triggered": "0",
     *  "script.compilations": "1",//脚本编译总数
     *  "search.fetch_current": "0",//search.fetch_current
     *  "search.fetch_time": "1.3s",//在抓取阶段花费的时间
     *  "search.fetch_total": "358",//提取操作的数量
     *  "search.open_contexts": "0",//打开搜索上下文
     *  "search.query_current": "0",//当前的查询阶段操作
     *  "search.query_time": "5.1s",//在查询阶段花费的时间
     *  "search.query_total": "854",//节点完成检索总次数
     *  "search.scroll_current": "0",//打开滚动上下文
     *  "search.scroll_time": "6.3h",//上下文保持打开状态时间滚动
     *  "search.scroll_total": "31",//节点完成滚动检索总次数
     *  "segments.count": "25",//segments.count
     *  "segments.fixed_bitset_memory": "0b",//固定位集使用的内存用于嵌套对象字段类型，而类型过滤器用于join字段中引用的类型，例如1.0kb。
     *  "segments.index_writer_memory": "0b",//索引编写器使用的内存
     *  "segments.memory": "54.8kb",//段使用的内存
     *  "segments.version_map_memory": "0b",//版本映射使用的内存
     *  "suggest.current": "0",
     *  "suggest.time": "0s",//建议所花费的时间
     *  "suggest.total": "0",//建议操作的数量
     *  "type": "tar",//节点类型
     *  "uptime": "2.2d",//节点正常运行时间
     *  "version": "7.9.2"//节点版本
     * }
     * </pre>
     */
    @ApiOperation(value = "返回有关群集节点的信息")
    @RequestMapping(value = "/_cat/nodes", method = RequestMethod.GET)
    String _cat_nodes(@RequestParam(value = "v") Boolean v,
                      @RequestParam(value = "h") String h,
                      @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回正在挂起的task")
    @RequestMapping(value = "/_cat/pending_tasks", method = RequestMethod.GET)
    String _cat_pending_tasks(@RequestParam(value = "v") Boolean v,
                              @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回一个群集的每个节点上运行的插件的列表")
    @RequestMapping(value = "/_cat/plugins", method = RequestMethod.GET)
    String _cat_plugins(@RequestParam(value = "v") Boolean v,
                        @RequestParam(value = "format") FormatEnum formatEnum);

    /**
     * index                            shard time    type       stage source_host  target_host  repository snapshot files files_percent bytes       bytes_percent total_files total_bytes translog translog_percent total_translog
     * comstore_tb_object_6891_v2       0     39      store      done  10.200.6.45  10.200.6.45  n/a        n/a      0     100.0%        0           100.0%        43          39999805    0        100.0%           0
     * comstore_tb_object_6891_v2       0     47      replica    done  10.200.6.45  10.200.2.204 n/a        n/a      0     0.0%          0           0.0%          0           0           0        100.0%           0
     * comstore_tb_object_6891_v2       1     68      store      done  10.200.6.168 10.200.6.168 n/a        n/a      0     100.0%        0           100.0%        31          40148758    0        100.0%           0
     * <p>
     * type 为 store 表示从本地恢复
     * type 为 replica 表示复制，从其他节点恢复
     * type 为 snapshot 表示从快照恢复
     */
    @ApiOperation(value = "返回有关正在进行的和已完成的碎片恢复的信息")
    @RequestMapping(value = "/_cat/recovery", method = RequestMethod.GET)
    String _cat_recovery(@RequestParam(value = "v") Boolean v,
                         @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回有关正在进行的和已完成的碎片恢复的信息(指定index)")
    @RequestMapping(value = "/_cat/recovery/{index}", method = RequestMethod.GET)
    String _cat_recovery_index(@RequestParam(value = "v") Boolean v,
                               @PathVariable(value = "index") String index,
                               @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回快照仓库")
    @RequestMapping(value = "/_cat/repositories", method = RequestMethod.GET)
    String _cat_repositories(@RequestParam(value = "v") Boolean v,
                             @RequestParam(value = "format") FormatEnum formatEnum);

    /**
     * <a href='https://www.elastic.co/guide/en/elasticsearch/reference/current/cat-shards.html'></a>
     */
    @ApiOperation(value = "返回分片信息", notes = "返回节点包含哪些分片,是主分片还是复制分片,doc的数量,使用的磁盘空间")
    @RequestMapping(value = "/_cat/shards", method = RequestMethod.GET)
    String _cat_shards(@RequestParam(value = "v") Boolean v,
                       @RequestParam(value = "h") String h,
                       @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回分片信息", notes = "返回节点包含哪些分片,是主分片还是复制分片,doc的数量,使用的磁盘空间")
    @RequestMapping(value = "/_cat/shards/{index}", method = RequestMethod.GET)
    String _cat_shards_index(@RequestParam(value = "v") Boolean v,
                             @PathVariable(value = "index") String index,
                             @RequestParam(value = "h") String h,
                             @RequestParam(value = "format") FormatEnum formatEnum);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/cat-segments.html"></a>
     *
     * <pre>
     * {
     *  "committed": "true",//是否提交
     *  "compound": "true",//是否存储在复合文件中
     *  "docs.count": "32",//文档数量
     *  "docs.deleted": "0",//文档删除数量
     *  "generation": "0",//世代号，例如0。对于每个写入的段，Elasticsearch都会增加该世代号
     *  "index": "这个是样例数据",//索引
     *  "ip": "127.0.0.1",//ip
     *  "prirep": "p",//分片类型(主分片/)
     *  "searchable": "true",//是否可搜索
     *  "segment": "_0",//段的名称
     *  "shard": "0",//分片名称
     *  "size": "5.1kb",//段使用的磁盘空间
     *  "size.memory": "1716",//段使用的内存大小
     *  "version": "8.6.2"//编写段的Lucene版本
     * }
     * </pre>
     */
    @ApiOperation(value = "返回index中的低级关于Lucene段的信息碎片")
    @RequestMapping(value = "/_cat/segments", method = RequestMethod.GET)
    String _cat_segments(@RequestParam(value = "v") Boolean v,
                         @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回index中的低级关于Lucene段的信息碎片")
    @RequestMapping(value = "/_cat/segments/{index}", method = RequestMethod.GET)
    String _cat_segments_index(@RequestParam(value = "v") Boolean v,
                               @PathVariable(value = "index") String index,
                               @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回信息有关快照存储在一个或多个存储库")
    @RequestMapping(value = "/_cat/snapshots/{repository}", method = RequestMethod.GET)
    String _cat_snapshots(@RequestParam(value = "v") Boolean v,
                          @PathVariable(value = "repository") String repository,
                          @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回有关在群集中当前正在执行的任务的信息")
    @RequestMapping(value = "/_cat/tasks", method = RequestMethod.GET)
    String _cat_tasks(@RequestParam(value = "v") Boolean v,
                      @RequestParam(value = "detailed", defaultValue = "false") Boolean detailed,
                      @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回群集中的index template的信息")
    @RequestMapping(value = "/_cat/templates", method = RequestMethod.GET)
    String _cat_templates(@RequestParam(value = "v") Boolean v,
                          @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回群集中的index template的信息")
    @RequestMapping(value = "/_cat/templates/{template_name}", method = RequestMethod.GET)
    String _cat_templates(@RequestParam(value = "v") Boolean v,
                          @PathVariable(value = "template_name") String template_name,
                          @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回在群集中每个节点的返回线程池统计信息")
    @RequestMapping(value = "/_cat/thread_pool", method = RequestMethod.GET)
    String _cat_thread_pool(@RequestParam(value = "v") Boolean v,
                            @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回在群集中每个节点的返回线程池统计信息")
    @RequestMapping(value = "/_cat/thread_pool/{thread_pool}", method = RequestMethod.GET)
    String _cat_thread_pool(@RequestParam(value = "v") Boolean v,
                            @PathVariable(value = "thread_pool") String thread_pool,
                            @RequestParam(value = "format") FormatEnum formatEnum);

    @ApiOperation(value = "返回有关转换的配置和使用信息")
    @RequestMapping(value = "/_cat/transforms", method = RequestMethod.POST)
    String _cat_transforms(@RequestParam(value = "v") Boolean v,
                           @RequestParam(value = "format") FormatEnum formatEnum);


}
