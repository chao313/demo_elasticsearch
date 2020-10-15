package demo.elastic.search.controller.custom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.enums.FormatEnum;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 集群分片相关
 */
@RequestMapping(value = "/Cluster_SegmentController")
@RestController
public class Cluster_SegmentController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE),
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_PAGE_HEADER_KEY,
                    value = CustomInterceptConfig.ES_PAGE_HEADER_KEY_DEFAULT,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = CustomInterceptConfig.ES_PAGE_HEADER_KEY_DEFAULT),
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY,
                    value = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY_DEFAULT,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY_DEFAULT)
    })
    @ApiOperation(value = "返回index中的低级关于Lucene段的信息碎片")
    @GetMapping(value = "/_cat/segments")
    public Object _cat_segments(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_segments(v, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }

}
















