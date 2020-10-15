package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 聚合相关
 */
@RequestMapping(value = "/Aggregation")
@RestController
public class Aggregation {

    @ApiOperation(value = "组合聚合示例", notes = "```" +
            "\n" +
            "{\n" +
            "    \"_source\":[\n" +
            "        \"*\"\n" +
            "    ],\n" +
            "    \"aggs\":{\n" +
            "        \"key\":{\n" +
            "            \"histogram\":{\n" +
            "                \"extended_bounds\":{\n" +
            "                    \"max\":400,\n" +
            "                    \"min\":0\n" +
            "                },\n" +
            "                \"field\":\"age\",\n" +
            "                \"interval\":5,\n" +
            "                \"keyed\":false,\n" +
            "                \"min_doc_count\":0,\n" +
            "                \"missing\":0\n" +
            "            },\n" +
            "            \"aggs\":{\n" +
            "                \"the_sum\":{\n" +
            "                    \"sum\":{\n" +
            "                        \"field\":\"age\"\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "    },\n" +
            "    \"from\":0,\n" +
            "    \"query\":{\n" +
            "        \"match_all\":{\n" +
            "            \"boost\":1\n" +
            "        }\n" +
            "    },\n" +
            "    \"size\":10\n" +
            "}"
            + "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search")
    public Response _search(
            @ApiParam(defaultValue = "index_bulk") @PathVariable(value = "index") String index,
            @RequestBody String body) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search(index, body);
        return Response.Ok(JSONObject.parse(result));
    }
}
















