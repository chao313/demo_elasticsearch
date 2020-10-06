package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.SQLService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.sql.SqlRequest;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(SQL语法)
 */
@RequestMapping(value = "/Search_SQLController")
@RestController
@Slf4j
public class Search_SQLController {

    @ApiOperation(value = "使用SQL查询", notes = "" +
            "{<br>" +
            "  \"fetch_size\": 10,<br>" +
            "  \"query\": \"SELECT * FROM index44\"<br>" +
            "}" +
            "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_sql")
    public Response _search(@ApiParam(value = "格式化样式", allowableValues = "csv,json,tsv,txt,yaml,cbor,smile", defaultValue = "json")
                            @RequestParam(value = "format") String format,
                            @RequestBody SqlRequest body) {
        SQLService sqlService = ThreadLocalFeign.getFeignService(SQLService.class);
        String result = sqlService._sql(format, body);
        try {
            return Response.Ok(JSONObject.parseObject(result));
        } catch (Exception e) {
            log.error("转换成json失败");
            return Response.Ok(result);
        }
    }

    @ApiOperation(value = "使用SQL查询", notes = "" +
            "{<br>" +
            "  \"cursor\": \"...\"<br>" +
            "}" +
            "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_sql/cursor")
    public Response _search_cursor(@ApiParam(value = "格式化样式", allowableValues = "csv,json,tsv,txt,yaml,cbor,smile", defaultValue = "json")
                                   @RequestParam(value = "format") String format,
                                   @RequestBody SqlRequest body) {
        SQLService sqlService = ThreadLocalFeign.getFeignService(SQLService.class);
        String result = sqlService._sql(format, body);
        try {
            return Response.Ok(JSONObject.parseObject(result));
        } catch (Exception e) {
            log.error("转换成json失败");
            return Response.Ok(result);
        }
    }

    @ApiOperation(value = "使用SQL查询", notes = "" +
            "{<br>" +
            "  \"cursor\": \"...\"<br>" +
            "}" +
            "")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_sql/close")
    public Response _search_close(@RequestBody SqlRequest body) {
        SQLService sqlService = ThreadLocalFeign.getFeignService(SQLService.class);
        String result = sqlService._sql_close(body);
        try {
            return Response.Ok(JSONObject.parseObject(result));
        } catch (Exception e) {
            log.error("转换成json失败");
            return Response.Ok(result);
        }
    }
}
















