package demo.elastic.search.controller.origin2.origin;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CCRService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用于 REST-API 集群 查询使用
 */
//@RequestMapping(value = "/origin/CCRController")
//@RestController
public class CCRController {

    @Resource
    private CCRService ccrService;

    @ApiOperation(value = "获取跨集群复制统计信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
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
















