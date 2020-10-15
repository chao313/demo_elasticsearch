package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.IndexService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 索引开关相关
 */
@RequestMapping(value = "/Index_OpenCloseController")
@RestController
public class Index_OpenCloseController {

    @ApiOperation(value = "关闭index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_close", method = RequestMethod.POST)
    public Response close(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._close(index);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "打开index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_open", method = RequestMethod.POST)
    public Response _open(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._open(index);
        return Response.Ok(JSONObject.parse(s));
    }

}
















