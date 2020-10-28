package demo.elastic.search.controller.custom;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.MappingService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.thread.ThreadLocalFeign;
import demo.elastic.search.util.FieldComparator;
import demo.elastic.search.util.JSONUtil;
import demo.elastic.search.util.StringToJson;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * 索引 mapping相关
 */
@RequestMapping(value = "/Index_MappingController")
@RestController
@Slf4j
public class Index_MappingController {

    @ApiOperation(value = "查看指定index的全部的mapping")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_mapping")
    public Response get(@PathVariable(value = "index") String index) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        String s = mappingService.get(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看指定index的全部的mapping(格式化，直接获取properties)")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_mapping/compatible")
    public Response get_compatible(@PathVariable(value = "index") String index) throws JsonProcessingException {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        String s = mappingService.get(index);
        JSONObject properties = JSONUtil.getByKey(JSON.parseObject(JSONObject.parse(s).toString()), "properties");
        JsonNode sortJson = StringToJson.getSortJson(properties);
        return Response.Ok(sortJson);
    }

    /**
     * 批量获取index的属性
     * -> index->[field1,field2...]
     */
    @PostMapping(value = "/_mapping/compatible/list")
    public Response get_compatible(@RequestBody List<String> indexs) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        StringBuilder indexPath = new StringBuilder();
        indexs.forEach(index -> {
            indexPath.append(index).append(",");
        });
        indexPath.substring(0, indexPath.length());
        String s = mappingService.get(indexPath.toString());
        JSONObject root = JSON.parseObject(JSONObject.parse(s).toString());
        Map<String, Collection<String>> map = new HashMap<>();
        root.forEach((key, obj) -> {
            if (obj instanceof JSONObject) {
                JSONObject properties = JSONUtil.getByKey((JSONObject) obj, "properties");
                Set<String> keySet = properties.keySet();
                Set<String> sortSet = new TreeSet<String>(new FieldComparator());
                sortSet.addAll(keySet);
                map.put(key, sortSet);
            }
        });

        return Response.Ok(map);
    }

    @ApiOperation(value = "查看指定index的指定字段的mapping")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_mapping/field/{fieldName}")
    public Response get(
            @PathVariable(value = "index") String index,
            @PathVariable(value = "fieldName") String fieldName) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        String s = mappingService.get(index, fieldName);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "添加新的字段 v6", notes = "```\n" +
            "{\n" +
            "    \"properties\":{\n" +
            "        \"email\":{\n" +
            "            \"type\":\"string\"\n" +
            "        }\n" +
            "    }\n" +
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
    @PutMapping(value = "/{index}/_mapping")
    public Response put(@PathVariable(value = "index") String index,
                        @RequestBody String body) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        String s = mappingService.put(index, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "添加新的字段 v2.3", notes = "```\n" +
            "{\n" +
            "    \"properties\":{\n" +
            "        \"email\":{\n" +
            "            \"type\":\"string\"\n" +
            "        }\n" +
            "    }\n" +
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
    @PutMapping(value = "/{index}/_mapping/{type}")
    public Response put(@PathVariable(value = "index") String index,
                        @PathVariable(value = "type") String type,
                        @RequestBody String body) {
        MappingService mappingService = ThreadLocalFeign.getFeignService(MappingService.class);
        String s = mappingService.put(index, type, body);
        return Response.Ok(JSONObject.parse(s));
    }

}
















