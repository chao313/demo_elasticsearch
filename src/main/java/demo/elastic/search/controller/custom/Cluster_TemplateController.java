package demo.elastic.search.controller.custom;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 集群模板相关
 */
@RequestMapping(value = "/Cluster_TemplateController")
@RestController
public class Cluster_TemplateController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回群集中的index template的信息")
    @GetMapping(value = "/_cat/templates")
    public String _cat_templates(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_templates(v);
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回群集中的index template的信息")
    @GetMapping(value = "/_cat/templates/{template_name}")
    public String _cat_templates(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                 @PathVariable(value = "template_name") String template_name) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_templates(v, template_name);
    }


}
















