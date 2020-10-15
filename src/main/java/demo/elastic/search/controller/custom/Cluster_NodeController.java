package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.ClusterService;
import demo.elastic.search.feign.enums.FormatEnum;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 集群节点相关
 */
@RequestMapping(value = "/Cluster_NodeController")
@RestController
public class Cluster_NodeController {

    @ApiOperation(value = "列出分配给每个数据节点的分片数量及其磁盘空间的快照")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_cat/allocation")
    public Object _cat_allocation(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                  @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_allocation(v, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "列出集群中每个数据节点上的字段数据 当前 使用的堆内存量")
    @GetMapping(value = "/_cat/fielddata")
    public Object _cat_fielddata(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                 @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_fielddata(v, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回有关自定义节点属性的信息")
    @GetMapping(value = "/_cat/nodeattrs")
    public Object _cat_nodeattrs(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                 @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_nodeattrs(v, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }


    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回master节点的信息，包括ID绑定IP地址和名称")
    @GetMapping(value = "/_cat/master")
    public Object _cat_master(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                              @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_master(v, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE),
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_PAGE_HEADER_KEY,
                    value = CustomInterceptConfig.ES_PAGE_HEADER_KEY_DEFAULT,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = CustomInterceptConfig.ES_PAGE_HEADER_KEY_DEFAULT),
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY,
                    value = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY_DEFAULT,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY_DEFAULT)
    })
    @ApiOperation(value = "返回有关群集节点的信息")
    @GetMapping(value = "/_cat/nodes")
    public Object _cat_nodes(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                             @ApiParam(value = "要显示的以逗号分隔的列名称列表(ip,cpu,port...)") @RequestParam(value = "h") String h,
                             @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_nodes(v, h, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }


    /**
     * 功能:返回有关功能用法的信息
     * <p>
     * 群集节点用法API使您可以检索有关每个节点的功能用法的信息。在此说明所有节点的选择选项
     *
     * @see <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.8/cluster-nodes-usage.html"></a>
     */
    @ApiOperation(value = "返回有关功能用法的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_nodes/usage", method = RequestMethod.GET)
    public Response _nodes_usage() {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._nodes_usage();
        return Response.Ok(JSONObject.parse(result));
    }

    @ApiOperation(value = "返回有关功能用法的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_nodes/usage/{metric}", method = RequestMethod.GET)
    public Response _nodes_usage(
            @ApiParam(required = false, value = "（可选，字符串）将返回的信息限制为特定的指标。以逗号分隔的以下选项列表<br>1._all返回所有统计信息<br>2.rest_actions 返回REST操作类名，其中包括该操作在节点上被调用的次数", example = "_all,rest_actions")
            @RequestParam(value = "metric", required = false) String metric) {
        ClusterService clusterService = ThreadLocalFeign.getFeignService(ClusterService.class);
        String result = clusterService._nodes_usage(metric);
        return Response.Ok(JSONObject.parse(result));
    }


}
















