package demo.elastic.search.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import demo.elastic.search.feign.IndexService;
import demo.elastic.search.feign.IndexServiceAlias;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.index.CreateIndex;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch Index级别的使用
 */
@RequestMapping(value = "/IndexController")
@RestController
public class IndexController {

    @Resource
    private IndexService indexService;

    @ApiOperation(value = "创建index", notes = "" +
            "{<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;\"settings\" : {<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;    \"number_of_shards\" : 1<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;},<br>" +
            "&nbsp;&nbsp;&nbsp; \"mappings\" : {<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"properties\" : {<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;    \"field1\" : { \"type\" : \"text\" }<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
            "&nbsp;&nbsp;&nbsp; },<br>" +
            "&nbsp;&nbsp;&nbsp;\"aliases\" : {<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"alias_1\" : {},<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"alias_2\" : {<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"filter\" : {<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;\"term\" : {\"user\" : \"kimchy\" }<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<br>" +
            "&nbsp;&nbsp;&nbsp;&nbsp;}<br>" +
            "&nbsp;&nbsp;&nbsp; }<br>" +
            "&nbsp; }")
    @RequestMapping(value = "/{index}", method = RequestMethod.PUT)
    public Response create(
            @ApiParam(value = "索引名称")
            @PathVariable(value = "index") String index,
            @RequestBody CreateIndex body) {
        String s = indexService.create(index, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除index")
    @RequestMapping(value = "/{index}", method = RequestMethod.DELETE)
    public Response delete(
            @ApiParam(value = "索引名称")
            @PathVariable(value = "index") String index) {
        String s = indexService.delete(index);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "关闭index")
    @RequestMapping(value = "/{index}/_close", method = RequestMethod.POST)
    public Response close(
            @ApiParam(value = "索引名称")
            @PathVariable(value = "index") String index) {
        String s = indexService._close(index);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "打开index")
    @RequestMapping(value = "/{index}/_open", method = RequestMethod.POST)
    public Response _open(
            @ApiParam(value = "索引名称")
            @PathVariable(value = "index") String index) {
        String s = indexService._open(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "冻结index")
    @RequestMapping(value = "/{index}/_freeze", method = RequestMethod.POST)
    public Response _freeze(
            @ApiParam(value = "索引名称")
            @PathVariable(value = "index") String index) {
        String s = indexService._freeze(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "解冻index")
    @RequestMapping(value = "/{index}/_unfreeze", method = RequestMethod.POST)
    public Response _unfreeze(
            @ApiParam(value = "索引名称")
            @PathVariable(value = "index") String index) {
        String s = indexService._unfreeze(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看index的状态")
    @RequestMapping(value = "/{index}/_stats/{index-metric}", method = RequestMethod.GET)
    public Response _stats(
            @ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "度量", defaultValue = "_all", allowableValues = "_all,completion,fielddata,flush,get,indexing,merge,query_cache,refresh,request_cache,search,segments,store,suggest,translog,warmer")
            @PathVariable(value = "index-metric") String index_metric) {
        String s = indexService._stats(index, index_metric);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "刷新index")
    @RequestMapping(value = "/{index}/_refresh", method = RequestMethod.GET)
    public Response _refresh(
            @ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
            @PathVariable(value = "index") String index) {
        String s = indexService._refresh(index);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "克隆index")
    @RequestMapping(value = "/{source_index}/_clone/{target_index}", method = RequestMethod.POST)
    public Response _clone(
            @ApiParam(value = "要克隆的源索引的名称")
            @PathVariable(value = "source_index") String source_index,
            @ApiParam(value = "要创建的目标索引的名称")
            @PathVariable(value = "target_index") String target_index) {
        String s = indexService._clone(source_index, target_index);
        return Response.Ok(JSONObject.parse(s));
    }
}
















