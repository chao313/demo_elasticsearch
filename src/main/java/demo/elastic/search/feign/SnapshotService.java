package demo.elastic.search.feign;


import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import demo.elastic.search.po.request.snapshot.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "snapshot", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SnapshotService {

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-register-repository.html"></a>
     * <p>
     * 注册FS快照存储库
     * <p>
     * 共享文件系统存储库（"type": "fs"）使用共享文件系统存储快照
     */
    @RequestMapping(value = "/_snapshot/{repositoryName}", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                      @RequestBody CreateSnapshotRepositoryFS body);

    /**
     * （"type": "url"）
     * 注册Url快照存储库
     */
    @RequestMapping(value = "/_snapshot/{repositoryName}", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                      @RequestBody CreateSnapshotRepositoryURL body);

    /**
     * （"type": "source"）
     * 注册Source快照存储库
     */
    @RequestMapping(value = "/_snapshot/{repositoryName}", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                      @RequestBody CreateSnapshotRepositorySource body);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-register-repository.html"></a>
     * 要检索有关多个存储库的信息，请指定以逗号分隔的存储库列表。*指定存储库名称时，也可以使用通配符
     * 获取仓库详情
     */
    @RequestMapping(value = "/_snapshot/{repositoryName}", method = RequestMethod.GET)
    String get_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-register-repository.html"></a>
     * 删除仓库
     */
    @RequestMapping(value = "/_snapshot/{repositoryName}", method = RequestMethod.DELETE)
    String delete_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-monitor-snapshot-restore.html"></a>
     */
    @ApiOperation(value = "检索全部仓库")
    @RequestMapping(value = "/_snapshot/_all", method = RequestMethod.GET)
    String get_all_repository();

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-take-snapshot.html"></a>
     * 创建快照
     * <pre>
     * {
     *   "indices": "index_1,index_2",
     *   "ignore_unavailable": true,
     *   "include_global_state": false,
     *   "metadata": {
     *     "taken_by": "kimchy",
     *     "taken_because": "backup before upgrading"
     *   }
     * }
     * </pre>
     * 快照名称可以使用日期数学表达式自动导出，类似于创建新索引时。特殊字符必须经过URI编码
     */
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}", method = RequestMethod.PUT, headers = {"content-type=application/json"})
    String create_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                           @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName,
                           @ApiParam(value = "是否等待完成") @RequestParam(value = "wait_for_completion") Boolean wait_for_completion,
                           @RequestBody CreateSnapshot body);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-restore-snapshot.html"></a>
     * 还原快照
     * 默认情况下，将还原快照中的所有索引，但不还原群集状态 。使用indices参数仅还原特定索引
     * <pre>
     * {
     *   "indices": "index_1,index_2",
     *   "ignore_unavailable": true,
     *   "include_global_state": false,
     *   "rename_pattern": "index_(.+)",
     *   "rename_replacement": "restored_index_$1",
     *   "include_aliases": false
     * }
     * </pre>
     */
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}/_restore", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String restore_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                            @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName,
                            @RequestBody RestoreSnapshot body);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-monitor-snapshot-restore.html"></a>
     * _current检索集群中所有当前正在运行的快照
     */
    @ApiOperation(value = "检索快照", notes = "_current检索集群中所有当前正在运行的快照(运行完就检索不到了)")
    @RequestMapping(value = "/_snapshot/{repositoryName}/_current", method = RequestMethod.GET)
    String get_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-monitor-snapshot-restore.html"></a>
     */
    @ApiOperation(value = "检索指定仓库的指定快照")
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}", method = RequestMethod.GET)
    String get_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                        @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-monitor-snapshot-restore.html"></a>
     */
    @ApiOperation(value = "删除指定仓库的指定快照", notes = "该操作检查删除的快照当前是否正在运行。如果是，则删除快照操作将停止该快照，然后再从存储库中删除快照数据")
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}", method = RequestMethod.DELETE)
    String delete_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                           @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-monitor-snapshot-restore.html"></a>
     */
    @ApiOperation(value = "检索指定仓库的全部快照")
    @RequestMapping(value = "/_snapshot/{repositoryName}/_all", method = RequestMethod.GET)
    String get_repository_all_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName);

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-monitor-snapshot-restore.html"></a>
     * 检索所有当前正在运行的快照以及详细的状态信息
     */
    @ApiOperation(value = "检索所有当前正在运行的快照以及详细的状态信息")
    @RequestMapping(value = "/_snapshot/_status", method = RequestMethod.GET)
    String get_snapshot_all_status();

    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-monitor-snapshot-restore.html"></a>
     * 检索所有当前正在运行的指定仓库快照以及详细的状态信息
     */
    @ApiOperation(value = "检索所有当前正在运行的指定仓库快照以及详细的状态信息")
    @RequestMapping(value = "/_snapshot/{repositoryName}/_status", method = RequestMethod.GET)
    String get_snapshot_repository_all_status(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName);


    /**
     * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/snapshots-monitor-snapshot-restore.html"></a>
     * 检索指定仓库快照指定快照详细的状态信息
     */
    @ApiOperation(value = "检索指定仓库快照指定快照详细的状态信息")
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}/_status", method = RequestMethod.GET)
    String get_snapshot_repository_snapshot_status(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                                   @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName);

}
