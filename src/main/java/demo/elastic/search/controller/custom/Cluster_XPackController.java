package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CCRService;
import demo.elastic.search.feign.XPackService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 集群XPack相关
 */
@RequestMapping(value = "/Cluster_XPackController")
@RestController
public class Cluster_XPackController {


    /**
     * categories
     * （可选，列表）包含在响应中的信息类别的逗号分隔列表。例如，build,license,features。
     * human
     * （可选，布尔值）定义是否在响应中包括其他人类可读信息。特别是，它添加了描述和标语。默认值为true。
     */
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_xpack", method = RequestMethod.GET)
    public Response _xpack(
            @ApiParam(defaultValue = "build,license,features", value = "（可选，列表）包含在响应中的信息类别的逗号分隔列表。例如，build,license,features。")
            @RequestParam(value = "categories") String categories,
            @ApiParam(defaultValue = "true", value = "可选，布尔值）定义是否在响应中包括其他人类可读信息。特别是，它添加了描述和标语。默认值为true。")
            @RequestParam(value = "human") Boolean human
    ) {
        XPackService xPackService = ThreadLocalFeign.getFeignService(XPackService.class);
        String result = xPackService._xpack(categories, human);
        return Response.Ok(JSONObject.parseObject(result));
    }


    /**
     * categories
     * （可选，列表）包含在响应中的信息类别的逗号分隔列表。例如，build,license,features。
     * human
     * （可选，布尔值）定义是否在响应中包括其他人类可读信息。特别是，它添加了描述和标语。默认值为true。
     */
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_xpack/usage", method = RequestMethod.GET)
    public Response _xpack_usage(
            @ApiParam(value = "（可选，时间单位）指定等待连接到主节点的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "master_timeout", required = false, defaultValue = "30s") String masterTimeout
    ) {
        XPackService xPackService = ThreadLocalFeign.getFeignService(XPackService.class);
        String result = xPackService._xpack_usage(masterTimeout);
        return Response.Ok(JSONObject.parseObject(result));
    }

    @Resource
    private CCRService ccrService;

    @ApiOperation(value = "获取跨集群复制统计信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_ccr/stats", method = RequestMethod.GET)
    public Response _ccr_stats() {
        CCRService ccrService = ThreadLocalFeign.getFeignService(CCRService.class);
        String result = ccrService._ccr_stats();
        return Response.Ok(JSONObject.parse(result));
    }
}
















