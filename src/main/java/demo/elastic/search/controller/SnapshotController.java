package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.SnapshotService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.snapshot.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 */
@RequestMapping(value = "/SnapshotController")
@RestController
public class SnapshotController {

    @Resource
    private SnapshotService snapshotService;

    @ApiOperation(value = "注册FS快照存储库", notes = "{<br>" +
            "&nbsp;\"settings\": {<br>" +
            "&nbsp;&nbsp;\"compress\": true,<br>" +
            "&nbsp;&nbsp;\"location\": \"string\",<br>" +
            "&nbsp;&nbsp;\"max_restore_bytes_per_sec\": \"40mb\",<br>" +
            "&nbsp;&nbsp;\"readonly\": false<br>" +
            "&nbsp;},<br>" +
            "&nbsp;\"type\": \"fs\"<br>" +
            "}")
    @PutMapping(value = "/FS/_snapshot/{repositoryName}")
    public Response create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                               @RequestBody CreateSnapshotRepositoryFS body) {
        String s = snapshotService.create_snapshot_repository(repositoryName, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "注册Url快照存储库", notes = "")
    @PutMapping(value = "/URL/_snapshot/{repositoryName}")
    public Response create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                               @RequestBody CreateSnapshotRepositoryURL body) {
        String s = snapshotService.create_snapshot_repository(repositoryName, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "注册Source快照存储库", notes = "")
    @PutMapping(value = "/Source/_snapshot/{repositoryName}")
    public Response create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                               @RequestBody CreateSnapshotRepositorySource body) {
        String s = snapshotService.create_snapshot_repository(repositoryName, body);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "获取仓库详情", notes = "要检索有关多个存储库的信息，请指定以逗号分隔的存储库列表。*指定存储库名称时，也可以使用通配符")
    @GetMapping(value = "/_snapshot/{repositoryName}")
    public Response get_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        String s = snapshotService.get_snapshot_repository(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "删除仓库")
    @DeleteMapping(value = "/_snapshot/{repositoryName}")
    public Response delete_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        String s = snapshotService.delete_snapshot_repository(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "创建快照", notes = " 默认情况下，快照会备份群集中的所有打开的索引。您可以通过在快照请求的正文中指定索引列表来更改此行为<br>快照名称可以使用日期数学表达式自动导出，类似于创建新索引时。特殊字符必须经过URI编码")
    @PutMapping(value = "/_snapshot/{repositoryName}/{snapshotName}")
    public Response create_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                    @ApiParam(value = "（必需，字符串）快照名称<br>快照名称可以使用日期数学表达式自动导出 <br>snapshot-{now/d}<br> ，类似于创建新索引时。特殊字符必须经过URI编码") @PathVariable(value = "snapshotName") String snapshotName,
                                    @ApiParam(value = "是否等待完成") @RequestParam(value = "wait_for_completion") Boolean wait_for_completion,
                                    @RequestBody CreateSnapshot body) {
        String s = snapshotService.create_snapshot(repositoryName, snapshotName, wait_for_completion, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "还原快照", notes = "默认情况下，将还原快照中的所有索引，但不还原群集状态 。使用indices参数仅还原特定索引" + "<br>{<br>" +
            "&nbsp;\"indices\": \"index_1,index_2\",<br>" +
            "&nbsp;\"ignore_unavailable\": true,<br>" +
            "&nbsp;\"include_global_state\": false,<br>" +
            "&nbsp;\"rename_pattern\": \"index(.+)\",<br>" +
            "&nbsp;\"rename_replacement\": \"restored_index$1\",<br>" +
            "&nbsp;\"include_aliases\": false<br>" +
            "}")
    @PostMapping(value = "/_snapshot/{repositoryName}/{snapshotName}/_restore")
    public Response restore_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                     @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName,
                                     @RequestBody RestoreSnapshot body) {
        String s = snapshotService.restore_snapshot(repositoryName, snapshotName, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索快照", notes = "_current检索集群中所有当前正在运行的快照(运行完就检索不到了)")
    @GetMapping(value = "/_snapshot/{repositoryName}/_current")
    public Response get_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        String s = snapshotService.get_snapshot(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索指定快照")
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}", method = RequestMethod.GET)
    public Response get_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                 @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName) {
        String s = snapshotService.get_snapshot(repositoryName, snapshotName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除指定仓库的指定快照", notes = "该操作检查删除的快照当前是否正在运行。如果是，则删除快照操作将停止该快照，然后再从存储库中删除快照数据")
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}", method = RequestMethod.DELETE)
    public Response delete_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                    @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName) {
        String s = snapshotService.delete_snapshot(repositoryName, snapshotName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索指定仓库的全部快照")
    @RequestMapping(value = "/_snapshot/{repositoryName}/_all", method = RequestMethod.GET)
    public Response get_repository_all_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        String s = snapshotService.get_repository_all_snapshot(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索全部仓库")
    @RequestMapping(value = "/_snapshot/_all", method = RequestMethod.GET)
    public Response get_all_repository() {
        String s = snapshotService.get_all_repository();
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索所有当前正在运行的快照以及详细的状态信息")
    @RequestMapping(value = "/_snapshot/_status", method = RequestMethod.GET)
    public Response get_snapshot_all_status() {
        String s = snapshotService.get_snapshot_all_status();
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索所有当前正在运行的指定仓库快照以及详细的状态信息")
    @RequestMapping(value = "/_snapshot/{repositoryName}/_status", method = RequestMethod.GET)
    public Response get_snapshot_repository_all_status(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        String s = snapshotService.get_snapshot_repository_all_status(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索指定仓库快照指定快照详细的状态信息")
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}/_status", method = RequestMethod.GET)
    public Response get_snapshot_repository_snapshot_status(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                                            @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName) {
        String s = snapshotService.get_snapshot_repository_snapshot_status(repositoryName, snapshotName);
        return Response.Ok(JSONObject.parse(s));
    }

}
















