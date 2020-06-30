package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 查询使用
 */
@RequestMapping(value = "/SearchController")
@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;

    @ApiOperation(value = "查询一个index的type")
    @PostMapping(value = "/{index}/{type}/_search")
    public Response _search(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "type") String type,
            @RequestBody String body) {
        String s = searchService._search(index, type, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查询一个index的type", notes =
            "<pre>" +
                    " curl -X GET  \"http://39.107.236.187:9200/bank/_doc/_search?pretty\" -H 'Content-Type: application/json' -d'<br>" +
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
    @PostMapping(value = "/{index}/_search")
    public Response _search(
            @PathVariable(value = "index") String index,
            @RequestBody String body) {
        String s = searchService._search(index, body);
        return Response.Ok(JSONObject.parse(s));
    }

}
