package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import demo.elastic.search.feign.XPackService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 证书 查询使用
 */
@RequestMapping(value = "/XPackController")
@RestController
public class XPackController {

    @Resource
    private XPackService xPackService;

    /**
     * categories
     * （可选，列表）包含在响应中的信息类别的逗号分隔列表。例如，build,license,features。
     * human
     * （可选，布尔值）定义是否在响应中包括其他人类可读信息。特别是，它添加了描述和标语。默认值为true。
     */
    @RequestMapping(value = "/_xpack", method = RequestMethod.GET)
    public Response _xpack(
            @ApiParam(defaultValue = "build,license,features", value = "（可选，列表）包含在响应中的信息类别的逗号分隔列表。例如，build,license,features。")
            @RequestParam(value = "categories") String categories,
            @ApiParam(defaultValue = "true", value = "可选，布尔值）定义是否在响应中包括其他人类可读信息。特别是，它添加了描述和标语。默认值为true。")
            @RequestParam(value = "human") Boolean human
    ) {
        String result = xPackService._xpack(categories, human);
        return Response.Ok(JSONObject.parseObject(result));
    }

    /**
     * 更新证书
     */
    @ApiOperation(value = "6.x的更新迷")
    @RequestMapping(value = "/_xpack/license", method = RequestMethod.POST)
    public Response _xpack(@RequestBody String body) {
        String result = xPackService._xpack_license(body);
        return Response.Ok(JSONObject.parseObject(result));
    }


    /**
     * categories
     * （可选，列表）包含在响应中的信息类别的逗号分隔列表。例如，build,license,features。
     * human
     * （可选，布尔值）定义是否在响应中包括其他人类可读信息。特别是，它添加了描述和标语。默认值为true。
     */
    @RequestMapping(value = "/_xpack/usage", method = RequestMethod.GET)
    public Response _xpack_usage(
            @ApiParam(value = "（可选，时间单位）指定等待连接到主节点的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "master_timeout", required = false, defaultValue = "30s") String masterTimeout
    ) {
        String result = xPackService._xpack_usage(masterTimeout);
        return Response.Ok(JSONObject.parseObject(result));
    }
}
