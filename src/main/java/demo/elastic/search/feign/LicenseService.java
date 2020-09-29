package demo.elastic.search.feign;


import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.feign.FeignServiceConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 证书 API
 */
@FeignClient(name = "license", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface LicenseService {

    /**
     * 当您的许可证过期时，X-Pack将以降级模式运行。有关更多信息，请参阅 许可到期
     * <a href="https://www.elastic.co/subscriptions">有关不同类型许可证的更多信息</a>
     */
    @RequestMapping(value = "/_license", method = RequestMethod.DELETE)
    String del();

    /**
     * 检索许可信息
     *
     * @param local （布尔值）指定是否检索本地信息。默认值为false，表示从主节点检索信息
     */
    @RequestMapping(value = "/_license", method = RequestMethod.GET)
    String get(
            @ApiParam(value = "（布尔值）指定是否检索本地信息。默认值为false，表示从主节点检索信息")
            @RequestParam(value = "local", defaultValue = "false") String local
    );

    /**
     * 如果您想尝试所有订阅功能，则可以开始30天的试用期
     */
    @RequestMapping(value = "/_license/trial_status", method = RequestMethod.GET)
    String _license_trial_status();


    /**
     * 如果您想尝试所有订阅功能，则可以开始30天的试用期
     */
    @RequestMapping(value = "/_license/start_trial?acknowledge=true", method = RequestMethod.POST)
    String _license_start_trial();

    /**
     * 基本许可证的状态
     */
    @RequestMapping(value = "/_license/basic_status", method = RequestMethod.GET)
    String _license_basic_status();

    /**
     * 开始无限期基本许可证
     */
    @RequestMapping(value = "/_license/start_basic?acknowledge=true", method = RequestMethod.POST)
    String _license_start_basic();

}
