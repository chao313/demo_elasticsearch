package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.ClusterService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 集群Task相关
 */
@RequestMapping(value = "/Cluster_TaskController")
@RestController
public class Cluster_TaskController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回正在挂起的task")
    @GetMapping(value = "/_cat/pending_tasks")
    public String _cat_pending_tasks(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_pending_tasks(v);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回有关在群集中当前正在执行的任务的信息")
    @RequestMapping(value = "/_cat/tasks", method = RequestMethod.GET)
    public String _cat_tasks(@ApiParam(value = "是否格式化") @RequestParam(value = "v", defaultValue = "true") Boolean v,
                             @RequestParam(value = "detailed") Boolean detailed) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_tasks(v, detailed);
    }


    @ApiOperation(value = "返回有关集群中当前正在执行的任务的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_tasks/{task_id}", method = RequestMethod.GET)
    public Response _tasks_task_id(
            @ApiParam(value = "可选，字符串）要返回的任务的ID（node_id:task_number）")
            @RequestParam(value = "task_id", required = false) String task_id) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._tasks_task_id(task_id);
        return Response.Ok(JSONObject.parse(result));
    }

}
















