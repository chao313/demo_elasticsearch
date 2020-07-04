package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.AwareUtil;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;


/**
 * 进行自定义操作
 */
@RequestMapping(value = "/CustomController")
@RestController
public class CustomController {

    @Autowired
    private DocumentService documentService;

    @ApiOperation(value = "accounts.json 数据批量插入")
    @PostMapping(value = "/{index}/_bulk")
    public Response _bulk_accounts(
            @PathVariable(value = "index") String index) throws IOException {
        File file = AwareUtil.resourceLoader.getResource("classpath:data/accounts.json").getFile();
        String body = FileUtils.readFileToString(file, "UTF-8");
        String s = documentService._bulk(index, body);
        return Response.Ok(JSONObject.parse(s));
    }


}
















