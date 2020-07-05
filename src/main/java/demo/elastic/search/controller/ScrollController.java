package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.ScrollService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 查询使用
 */
@RequestMapping(value = "/ScrollController")
@RestController
public class ScrollController {

    @Resource
    private ScrollService scrollService;


    @ApiOperation(value = "使用滚动查询")
    @PostMapping(value = "/_search/scroll")
    public Response _search(
            @ApiParam(name = "scroll_id", value = "有效的scroll_id")
            @RequestParam(value = "scroll_id") String scroll_id,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll) {
        String result = scrollService._search(scroll, scroll_id);

        return Response.Ok(JSONObject.parse(result));
    }

}
