package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.po.Body;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 解析查询使用
 */
@RequestMapping(value = "/SearchParseController")
@RestController
@Slf4j
public class SearchParseController {

    @ApiOperation(value = "解析成标准DSL查询语句")
    @PostMapping(value = "/parse")
    public JSONObject _search(
            @RequestBody Body body) {
        log.info("解析的json:{}", body.parse());
        return JSONObject.parseObject(body.parse());
    }
}
