package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.AnalyzeService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.analyze.AnalyzeRequest;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 检索相关(分词器相关)
 */
@RequestMapping(value = "/Search_AnalyzeController")
@RestController
@Slf4j
public class Search_AnalyzeController {

    @ApiOperation(value = "使用分词器查询", notes = "```\n" +
            "{\n" +
            "    \"fetch_size\":10,\n" +
            "    \"query\":\"SELECT * FROM index44\"\n" +
            "}" +
            "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_analyze")
    public Response _search(@RequestBody AnalyzeRequest body) {
        AnalyzeService analyzeService = ThreadLocalFeign.getFeignService(AnalyzeService.class);
        String result = analyzeService._analyze(body);
        return Response.Ok(JSONObject.parseObject(result));
    }
}
















