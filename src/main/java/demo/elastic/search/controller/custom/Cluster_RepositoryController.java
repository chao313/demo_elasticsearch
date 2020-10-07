package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.SnapshotService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.snapshot.CreateSnapshotRepositoryFS;
import demo.elastic.search.po.request.snapshot.CreateSnapshotRepositorySource;
import demo.elastic.search.po.request.snapshot.CreateSnapshotRepositoryURL;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 集群仓库相关
 */
@RequestMapping(value = "/Cluster_RepositoryController")
@RestController
public class Cluster_RepositoryController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回快照仓库")
    @GetMapping(value = "/_cat/repositories")
    public String _cat_repositories(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_repositories(v);
    }


    @ApiOperation(value = "注册FS快照存储库", notes = "```\n" +
            "{\n" +
            "    \"settings\":{\n" +
            "        \"compress\":true,\n" +
            "        \"location\":\"string\",\n" +
            "        \"max_restore_bytes_per_sec\":\"40mb\",\n" +
            "        \"readonly\":false\n" +
            "    },\n" +
            "    \"type\":\"fs\"\n" +
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
    @PutMapping(value = "/FS/_snapshot/{repositoryName}")
    public Response create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                               @RequestBody CreateSnapshotRepositoryFS body) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.create_snapshot_repository(repositoryName, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "注册Url快照存储库", notes = "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PutMapping(value = "/URL/_snapshot/{repositoryName}")
    public Response create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                               @RequestBody CreateSnapshotRepositoryURL body) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.create_snapshot_repository(repositoryName, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "注册Source快照存储库", notes = "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PutMapping(value = "/Source/_snapshot/{repositoryName}")
    public Response create_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName,
                                               @RequestBody CreateSnapshotRepositorySource body) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.create_snapshot_repository(repositoryName, body);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "获取仓库详情", notes = "要检索有关多个存储库的信息，请指定以逗号分隔的存储库列表。*指定存储库名称时，也可以使用通配符")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_snapshot/{repositoryName}")
    public Response get_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.get_snapshot_repository(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "删除仓库")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @DeleteMapping(value = "/_snapshot/{repositoryName}")
    public Response delete_snapshot_repository(@ApiParam(value = "（必需，字符串）仓库名称") @PathVariable(value = "repositoryName") String repositoryName) {
        SnapshotService snapshotService = ThreadLocalFeign.getFeignService(SnapshotService.class);
        String s = snapshotService.delete_snapshot_repository(repositoryName);
        return Response.Ok(JSONObject.parse(s));
    }

}
















