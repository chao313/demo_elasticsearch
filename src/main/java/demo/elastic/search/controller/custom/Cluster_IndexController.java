package demo.elastic.search.controller.custom;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import demo.elastic.search.util.StringToJson;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * 集群索引相关
 */
@RequestMapping(value = "/Cluster_IndexController")
@RestController
public class Cluster_IndexController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "列出index")
    @GetMapping(value = "/_cat/indices")
    public String _cat_indices(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_indices(v);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "列出index(格式化)")
    @GetMapping(value = "/_cat/indices/format")
    public Response _cat_indices() throws IOException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String source = catService._cat_indices(true);
        return Response.Ok(StringToJson.toJSONArray(source));

    }


}
















