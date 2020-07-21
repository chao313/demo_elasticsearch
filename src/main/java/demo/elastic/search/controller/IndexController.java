package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.feign.DocumentService;
import demo.elastic.search.feign.IndexService;
import demo.elastic.search.framework.Response;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch Document级别的使用
 */
@RequestMapping(value = "/IndexController")
@RestController
public class IndexController {

    @Resource
    private IndexService indexService;

    @ApiOperation(value = "创建或更新索引别名")
    @PutMapping(value = "/{index}/_alias/{alias}")
    public Response put_alias(
            @ApiParam(value = "（必需，字符串）索引名称的逗号分隔列表或通配符表达式，以添加到别名中;要将群集中的所有索引添加到别名，请使用值_all")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "（必需，字符串）要创建或更新的索引别名的名称")
            @PathVariable(value = "alias") String alias) {
        String s = indexService.put_alias(index, alias);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除现有索引别名")
    @DeleteMapping(value = "/{index}/_aliases/{alias}")
    public Response delete_alias(
            @ApiParam(value = "（必需，字符串）索引名称的逗号分隔列表或通配符表达式，以添加到别名中;要将群集中的所有索引添加到别名，请使用值_all")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "（必需，字符串）要创建或更新的索引别名的名称")
            @PathVariable(value = "alias") String alias) {
        String s = indexService.delete_alias(index, alias);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "判断别名是否存在")
    @RequestMapping(value = "/{index}/_aliases/{alias}", method = RequestMethod.GET)
    public Response exists_alias(
            @ApiParam(value = "（必需，字符串）索引名称的逗号分隔列表或通配符表达式，以添加到别名中;要将群集中的所有索引添加到别名，请使用值_all")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "（必需，字符串）要创建或更新的索引别名的名称")
            @PathVariable(value = "alias") String alias) {
        String s = indexService.exists_alias(index, alias);
        return Response.Ok(JSONObject.parse(s));
    }
}
















