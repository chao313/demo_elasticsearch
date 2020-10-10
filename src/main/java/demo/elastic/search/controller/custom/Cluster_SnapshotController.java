package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.SnapshotService;
import demo.elastic.search.feign.enums.FormatEnum;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.snapshot.CreateSnapshot;
import demo.elastic.search.po.request.snapshot.RestoreSnapshot;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 集群快照相关
 */
@RequestMapping(value = "/Cluster_SnapshotController")
@RestController
public class Cluster_SnapshotController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回信息有关快照存储在一个或多个存储库")
    @GetMapping(value = "/_cat/snapshots/{repository}")
    public Object _cat_snapshots(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                 @PathVariable(value = "repository") String repository,
                                 @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_snapshots(v, repository, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }

    @ApiOperation(value = "创建快照", notes = " 默认情况下，快照会备份群集中的所有打开的索引。您可以通过在快照请求的正文中指定索引列表来更改此行为<br>快照名称可以使用日期数学表达式自动导出，类似于创建新索引时。特殊字符必须经过URI编码")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PutMapping(value = "/_snapshot/{repositoryName}/{snapshotName}")
    public Response create_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                    @ApiParam(value = "（必需，字符串）快照名称<br>快照名称可以使用日期数学表达式自动导出 <br>snapshot-{now/d}<br> ，类似于创建新索引时。特殊字符必须经过URI编码") @PathVariable(value = "snapshotName") String snapshotName,
                                    @ApiParam(value = "是否等待完成") @RequestParam(value = "wait_for_completion") Boolean wait_for_completion,
                                    @RequestBody CreateSnapshot body) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.create_snapshot(repositoryName, snapshotName, wait_for_completion, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "还原快照", notes = "默认情况下，将还原快照中的所有索引，但不还原群集状态 。使用indices参数仅还原特定索引\n" +
            "```\n" +
            "{\n" +
            "    \"indices\":\"index_1,index_2\",\n" +
            "    \"ignore_unavailable\":true,\n" +
            "    \"include_global_state\":false,\n" +
            "    \"rename_pattern\":\"index(.+)\",\n" +
            "    \"rename_replacement\":\"restored_index$1\",\n" +
            "    \"include_aliases\":false\n" +
            "}" +
            "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_snapshot/{repositoryName}/{snapshotName}/_restore")
    public Response restore_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                     @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName,
                                     @RequestBody RestoreSnapshot body) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.restore_snapshot(repositoryName, snapshotName, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索快照", notes = "_current检索集群中所有当前正在运行的快照(运行完就检索不到了)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_snapshot/{repositoryName}/_current")
    public Response get_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.get_snapshot(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索指定快照")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}", method = RequestMethod.GET)
    public Response get_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                 @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.get_snapshot(repositoryName, snapshotName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除指定仓库的指定快照", notes = "该操作检查删除的快照当前是否正在运行。如果是，则删除快照操作将停止该快照，然后再从存储库中删除快照数据")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}", method = RequestMethod.DELETE)
    public Response delete_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                    @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.delete_snapshot(repositoryName, snapshotName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索指定仓库的全部快照")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_snapshot/{repositoryName}/_all", method = RequestMethod.GET)
    public Response get_repository_all_snapshot(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.get_repository_all_snapshot(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索全部仓库")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_snapshot/_all", method = RequestMethod.GET)
    public Response get_all_repository() {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.get_all_repository();
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索所有当前正在运行的快照以及详细的状态信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_snapshot/_status", method = RequestMethod.GET)
    public Response get_snapshot_all_status() {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.get_snapshot_all_status();
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索所有当前正在运行的指定仓库快照以及详细的状态信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_snapshot/{repositoryName}/_status", method = RequestMethod.GET)
    public Response get_snapshot_repository_all_status(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.get_snapshot_repository_all_status(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "检索指定仓库快照指定快照详细的状态信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_snapshot/{repositoryName}/{snapshotName}/_status", method = RequestMethod.GET)
    public Response get_snapshot_repository_snapshot_status(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                                            @ApiParam(value = "（必需，字符串）快照名称") @PathVariable(value = "snapshotName") String snapshotName) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.get_snapshot_repository_snapshot_status(repositoryName, snapshotName);
        return Response.Ok(JSONObject.parse(s));
    }

}
















