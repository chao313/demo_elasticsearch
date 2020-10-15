package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.feign.enums.FormatEnum;
import demo.elastic.search.framework.Response;
import demo.elastic.search.service.RedisService;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * 集群索引相关
 */
@RequestMapping(value = "/Cluster_IndexController")
@RestController
public class Cluster_IndexController {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisService redisService;

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE),
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_PAGE_HEADER_KEY,
                    value = CustomInterceptConfig.ES_PAGE_HEADER_KEY_DEFAULT,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = CustomInterceptConfig.ES_PAGE_HEADER_KEY_DEFAULT),
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY,
                    value = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY_DEFAULT,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = CustomInterceptConfig.ES_PAGE_SIZE_HEADER_KEY_DEFAULT),
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_FILTER_HEADER_KEY,
                    value = CustomInterceptConfig.ES_FILTER_HEADER_KEY_EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = "")

    })
    @ApiOperation(value = "列出index")
    @GetMapping(value = "/_cat/indices")
    public Object _cat_indices(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                               @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String source = catService._cat_indices(v, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(source));
        } else {
            return source;
        }
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "列出index")
    @GetMapping(value = "/_cat/indices/page")
    public Object _cat_indices_page(@ApiParam(value = "每页数量") @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String source = catService._cat_indices(true, FormatEnum.JSON);
        JSONArray jsonArray = JSONArray.parseArray(source);
        List<Object> objects = Arrays.asList(jsonArray.toArray());
        String uuid = UUID.randomUUID().toString();
        redisTemplate.opsForList().leftPushAll(uuid, objects);//存入list
        return redisService.getRecordByScrollId(uuid, 1, pageSize);
    }

}
















