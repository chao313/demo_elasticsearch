package demo.elastic.search.controller.origin;


import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.LicenseService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 用于 证书 查询使用
 */
@RequestMapping(value = "/origin/LicenseController")
@RestController
public class LicenseController {

    @Resource
    private LicenseService licenseService;

    /**
     * 当您的许可证过期时，X-Pack将以降级模式运行。有关更多信息，请参阅 许可到期
     * <a href="https://www.elastic.co/subscriptions">有关不同类型许可证的更多信息</a>
     */
    @ApiOperation(value = "当您的许可证过期时，X-Pack将以降级模式运行。有关更多信息，请参阅 许可到期")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_license", method = RequestMethod.DELETE)
    public Response del() {
        LicenseService licenseService = ThreadLocalFeign.getFeignService(LicenseService.class);
        String result = licenseService.del();
        return Response.Ok(JSONObject.parseObject(result));
    }

    /**
     * 检索许可信息
     *
     * @param local （布尔值）指定是否检索本地信息。默认值为false，表示从主节点检索信息
     */
    @ApiOperation(value = "检索许可信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_license", method = RequestMethod.GET)
    public Response get(
            @ApiParam(value = "（布尔值）指定是否检索本地信息。默认值为false，表示从主节点检索信息")
            @RequestParam(value = "local", defaultValue = "false") String local
    ) {
        LicenseService licenseService = ThreadLocalFeign.getFeignService(LicenseService.class);
        String result = licenseService.get(local);
        return Response.Ok(JSONObject.parseObject(result));
    }


    /**
     * 如果您想尝试所有订阅功能，则可以开始30天的试用期
     */
    @ApiOperation(value = "如果您想尝试所有订阅功能，则可以开始30天的试用期")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_license/trial_status", method = RequestMethod.GET)
    public Response _license_trial_status() {
        LicenseService licenseService = ThreadLocalFeign.getFeignService(LicenseService.class);
        String result = licenseService._license_trial_status();
        return Response.Ok(JSONObject.parseObject(result));
    }


    /**
     * 如果您想尝试所有订阅功能，则可以开始30天的试用期
     */
    @ApiOperation(value = "如果您想尝试所有订阅功能，则可以开始30天的试用期")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_license/start_trial", method = RequestMethod.POST)
    public Response _license_start_trial() {
        LicenseService licenseService = ThreadLocalFeign.getFeignService(LicenseService.class);
        String result = licenseService._license_start_trial();
        return Response.Ok(JSONObject.parseObject(result));
    }

    /**
     * 基本许可证的状态
     */
    @ApiOperation(value = "基本许可证的状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_license/basic_status", method = RequestMethod.GET)
    public Response _license_basic_status() {
        LicenseService licenseService = ThreadLocalFeign.getFeignService(LicenseService.class);
        String result = licenseService._license_basic_status();
        return Response.Ok(JSONObject.parseObject(result));
    }

    /**
     * 开始无限期基本许可证
     */
    @ApiOperation(value = "开始无限期基本许可证")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/_license/start_basic", method = RequestMethod.POST)
    public Response _license_start_basic() {
        LicenseService licenseService = ThreadLocalFeign.getFeignService(LicenseService.class);
        String result = licenseService._license_start_basic();
        return Response.Ok(JSONObject.parseObject(result));
    }


}
