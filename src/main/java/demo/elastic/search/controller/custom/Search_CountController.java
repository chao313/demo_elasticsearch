package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CountService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(EQL语法)
 */
@RequestMapping(value = "/Search_EQLController")
@RestController
public class Search_CountController {

    @ApiOperation(value = "只查询匹配到的数量", notes =
            "" +
                    "<pre>" +
                    " {<br>" +
                    " &nbsp;\"from\": 0,<br>" +
                    " &nbsp;\"size\": 1,<br>" +
                    " &nbsp;\"query\": {<br>" +
                    " &nbsp;&nbsp;\"bool\": {<br>" +
                    " &nbsp;&nbsp;&nbsp;\"must\":[<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"exists\": {\"field\": \"age\"}},<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"exists\": {\"field\": \"address\"}},<br>" +
                    " &nbsp;&nbsp;&nbsp; {\"term\": {\"age\": {<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp &nbsp;\"boost\": 0,<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp &nbsp;\"value\": 32<br>" +
                    " &nbsp;&nbsp;&nbsp;&nbsp;&nbsp }}<br>" +
                    " &nbsp;&nbsp;&nbsp; }<br>" +
                    " &nbsp;&nbsp;&nbsp;]<br>" +
                    " &nbsp;&nbsp;}<br>" +
                    " &nbsp;}<br>" +
                    "}'<br>" +
                    "</pre>")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_count")
    public Response _search(@ApiParam(defaultValue = "tb_object_0088") @PathVariable(value = "index") String index,
                            @RequestBody String body) {
        CountService countService = ThreadLocalFeign.getFeignService(CountService.class);
        String result = countService._count(index, body);
        return Response.Ok(JSONObject.parseObject(result));
    }

}
















