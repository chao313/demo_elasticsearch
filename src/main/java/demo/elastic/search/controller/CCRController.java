package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.CCRService;
import demo.elastic.search.feign.ClusterService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 REST-API 集群 查询使用
 */
@RequestMapping(value = "/CCRController")
@RestController
public class CCRController {

    @Resource
    private CCRService ccrService;


    @ApiOperation(value = "获取跨集群复制统计信息")
    @RequestMapping(value = "/_ccr/stats", method = RequestMethod.GET)
    private Response _ccr_stats() {
        String result = ccrService._ccr_stats();
        return Response.Ok(JSONObject.parse(result));
    }
}
















