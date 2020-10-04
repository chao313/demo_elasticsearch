package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchMatchAllService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.dsl.matchall.MatchAllRequest;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(match_all 语法)
 */
@RequestMapping(value = "/Search_DSL_MatchAllController")
@RestController
public class Search_DSL_MatchAllController {

    @ApiOperation(value = "match all检索")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index,
                            @RequestBody MatchAllRequest matchAllRequest) {
        SearchMatchAllService searchMatchAllService = ThreadLocalFeign.getFeignService(SearchMatchAllService.class);
        String result = searchMatchAllService.match_all_search(index, matchAllRequest);
        return Response.Ok(JSONObject.parse(result));
    }

}
















