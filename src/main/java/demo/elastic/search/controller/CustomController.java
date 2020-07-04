package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.feign.ScrollService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.response.ESResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.io.stream.InputStreamStreamInput;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;


/**
 * 进行自定义操作
 */
@RequestMapping(value = "/CustomController")
@RestController
@Slf4j
public class CustomController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private SearchService searchService;

    @Autowired
    private ScrollService scrollService;

    @ApiOperation(value = "accounts.json 数据批量插入")
    @PostMapping(value = "/{index}/_bulk")
    public Response _bulk_accounts(
            @PathVariable(value = "index") String index) throws IOException {
        File file = AwareUtil.resourceLoader.getResource("classpath:data/accounts.json").getFile();
        String body = FileUtils.readFileToString(file, "UTF-8");
        String s = documentService._bulk(index, body);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "导出全部的查询结果")
    @PostMapping(value = "/{index}/_search/output")
    public Response _search(
            @PathVariable(value = "index") String index,
            @ApiParam(name = "scroll", value = "scroll的有效时间,允许为空(e.g. 1m 1d)")
            @RequestParam(value = "scroll", required = false) String scroll,
            @RequestBody String body) throws IOException {
        String result;
        if (StringUtils.isBlank(scroll)) {
            result = searchService._search(index, body);
            ESResponse esResponse = ESResponse.parse(result);
            
        } else {
            result = searchService._search(index, scroll, body);
        }

        return Response.Ok(JSONObject.parse(result));
    }
}
















