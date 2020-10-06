package demo.elastic.search.controller.custom;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.po.request.aggs.AggsQuery;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 指标聚合相关
 */
@RequestMapping(value = "/Aggregation_MetricsController")
@RestController
public class Aggregation_MetricsController {


    @ApiOperation(value = "列出当前配置的index的alias,包括filter和router的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/aggs")
    public String _cat_aliases(@RequestBody AggsQuery aggsQuery) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return null;
    }

}
















