package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.Body;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 查询使用
 */
@RequestMapping(value = "/SearchBaseController")
@RestController
public class SearchBaseController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "查询一个index的type")
    @PostMapping(value = "/{index}/_search")
    public Response _search(
            @PathVariable(value = "index") String index,
            @RequestBody Body body) {
        String s = searchService._search(index, JSONObject.toJSON(body).toString());
        return Response.Ok(JSONObject.parse(s));
    }
}
