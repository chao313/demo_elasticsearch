package demo.elasticSearch.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用于 ElasticSearch es使用
 */
@RequestMapping(value = "/ElasticSearchController")
@RestController
public class ElasticSearchController {

    /**
     * es的使用
     *
     * @return
     */
    @ApiOperation(value = "创建生产者", notes = "使用工厂来生成")
    @GetMapping(value = "/createProducer")
    public String createProducer(


    ) {

        return "";
    }
}
















