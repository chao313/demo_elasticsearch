package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.IndexService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 集群分片相关
 */
@RequestMapping(value = "/Cluster_SegmentController")
@RestController
public class Index_SegmentController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回index中的低级关于Lucene段的信息碎片")
    @GetMapping(value = "/_cat/segments/{index}")
    public String _cat_segments_index(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                      @PathVariable(value = "index") String index) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_segments_index(v, index);
    }


    @ApiOperation(value = "返回有关 索引分片中Lucene段的低级信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_segments", method = RequestMethod.GET)
    public Response _segments(@ApiParam(value = "(可选，字符串）索引名称的逗号分隔列表或通配符表达式，用于限制请求") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._segments(index);
        return Response.Ok(JSONObject.parse(s));
    }

}
















