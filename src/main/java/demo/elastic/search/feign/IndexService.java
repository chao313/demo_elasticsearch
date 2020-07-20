package demo.elastic.search.feign;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.FeignServiceConfig;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * index的相关操作
 */
@FeignClient(name = "index", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface IndexService {

    /**
     * 创建或更新索引别名
     * 还有其他参数未加入
     *
     * @param index
     * @param alias
     * @return
     */
    @RequestMapping(value = "/{index}/_alias/{alias}", method = RequestMethod.PUT)
    String put_alias(
            @ApiParam(value = "（必需，字符串）索引名称的逗号分隔列表或通配符表达式，以添加到别名中;要将群集中的所有索引添加到别名，请使用值_all")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "（必需，字符串）要创建或更新的索引别名的名称")
            @PathVariable(value = "alias") String alias);

    /**
     * 删除现有索引别名。
     * 还有其他参数未加入
     *
     * @param index
     * @param alias
     * @return
     */
    @RequestMapping(value = "/{index}/_alias/{alias}", method = RequestMethod.DELETE)
    String delete_alias(
            @ApiParam(value = "（必需，字符串）索引名称的逗号分隔列表或通配符表达式，以添加到别名中;要将群集中的所有索引添加到别名，请使用值_all")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "（必需，字符串）要创建或更新的索引别名的名称")
            @PathVariable(value = "alias") String alias);


}
