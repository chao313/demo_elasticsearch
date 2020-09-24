package demo.elastic.search.feign;


import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * XPack API
 * <p>
 * 内部版本信息-包括内部版本号和时间戳。
 * 许可证信息-有关当前安装的许可证的基本信息。
 * 功能信息-当前启用并在当前许可下可用的功能
 */
@FeignClient(name = "XPack", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface XPackService {

    /**
     * categories
     * （可选，列表）包含在响应中的信息类别的逗号分隔列表。例如，build,license,features。
     * human
     * （可选，布尔值）定义是否在响应中包括其他人类可读信息。特别是，它添加了描述和标语。默认值为true。
     */
    @ApiOperation(value = "提供有关已安装的X-Pack功能的常规信息")
    @RequestMapping(value = "/_xpack", method = RequestMethod.GET)
    String _xpack(
            @ApiParam(defaultValue = "build,license,features", value = "（可选，列表）包含在响应中的信息类别的逗号分隔列表。例如，build,license,features。")
            @RequestParam(value = "categories") String categories,
            @ApiParam(defaultValue = "true", value = "可选，布尔值）定义是否在响应中包括其他人类可读信息。特别是，它添加了描述和标语。默认值为true。")
            @RequestParam(value = "human") Boolean human
    );

    /**
     *
     */
    @ApiOperation(value = "更新证书")
    @RequestMapping(value = "/_xpack/license", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _xpack_license(@RequestBody String body);

    /**
     * 该API提供有关当前已启用哪些功能以及在当前许可下可用的信息以及一些使用情况统计信息
     */
    @ApiOperation(value = "提供有关已安装的X-Pack功能的常规信息")
    @RequestMapping(value = "/_xpack/usage", method = RequestMethod.GET)
    String _xpack_usage(
            @ApiParam(value = "（可选，时间单位）指定等待连接到主节点的时间段。如果在超时到期之前未收到任何响应，则请求将失败并返回错误。默认为30s")
            @RequestParam(value = "master_timeout", required = false, defaultValue = "30s") String masterTimeout
    );
}
