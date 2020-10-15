package demo.elastic.search.controller.origin;

import com.alibaba.fastjson.JSONObject;
import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.IndexService;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.request.index.CreateIndex;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch Index级别的使用
 */
@RequestMapping(value = "/origin/IndexController")
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
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}", method = RequestMethod.PUT)
    public Response create(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index,
                           @RequestBody CreateIndex body) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService.create(index, body);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "返回索引的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}", method = RequestMethod.GET)
    public Response get(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService.get(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}", method = RequestMethod.DELETE)
    public Response delete(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService.delete(index);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "关闭index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_close", method = RequestMethod.POST)
    public Response close(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._close(index);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "打开index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_open", method = RequestMethod.POST)
    public Response _open(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._open(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "冻结index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_freeze", method = RequestMethod.POST)
    public Response _freeze(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._freeze(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "解冻index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_unfreeze", method = RequestMethod.POST)
    public Response _unfreeze(@ApiParam(value = "索引名称") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._unfreeze(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看index的状态")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_stats/{index-metric}", method = RequestMethod.GET)
    public Response _stats(
            @ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)")
            @PathVariable(value = "index") String index,
            @ApiParam(value = "度量", defaultValue = "_all", allowableValues = "_all,completion,fielddata,flush,get,indexing,merge,query_cache,refresh,request_cache,search,segments,store,suggest,translog,warmer")
            @PathVariable(value = "index-metric") String index_metric) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._stats(index, index_metric);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "刷新index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_refresh", method = RequestMethod.GET)
    public Response _refresh(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._refresh(index);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "克隆index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{source_index}/_clone/{target_index}", method = RequestMethod.POST)
    public Response _clone(
            @ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
            @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._clone(source_index, target_index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "_flush index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_flush", method = RequestMethod.GET)
    public Response _flush(@ApiParam(value = "索引名称(要操作所有索引，请使用_all或*)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._flush(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "同步 flush index")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_flush/synced", method = RequestMethod.GET)
    public Response _flush_synced(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._flush_synced(index);
        return Response.Ok(JSONObject.parse(s));
    }


    @ApiOperation(value = "返回有关 索引分片中Lucene段的低级信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_segments", method = RequestMethod.GET)
    public Response _segments(@ApiParam(value = "(可选，字符串）索引名称的逗号分隔列表或通配符表达式，用于限制请求") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._segments(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "返回索引的设置信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_settings", method = RequestMethod.GET)
    public Response _settings(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._settings(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "返回索引的设置信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_settings/{setting}", method = RequestMethod.GET)
    public Response _settings(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index,
                              @ApiParam(value = "(可选，字符串)用于限制请求的设置名称的逗号分隔列表或通配符表达式") @PathVariable(value = "setting") String setting) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._settings(index, setting);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "返回有关正在进行和已完成的碎片恢复的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_recovery", method = RequestMethod.GET)
    public Response _recovery(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._recovery(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "返回一个或多个索引中有关副本分片的存储信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_shard_stores", method = RequestMethod.GET)
    public Response _shard_stores(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._shard_stores(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "使用更少的主碎片将现有索引缩减为新索引")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{source_index}/_shrink/{target_index}", method = RequestMethod.POST)
    public Response _shrink(
            @ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
            @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._shrink(source_index, target_index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "将现有索引拆分为具有更多主碎片的新索引")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{source_index}/_split/{target_index}", method = RequestMethod.POST)
    public Response _split(
            @ApiParam(value = "要克隆的源索引的名称") @PathVariable(value = "source_index") String source_index,
            @ApiParam(value = "要创建的目标索引的名称") @PathVariable(value = "target_index") String target_index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._split(source_index, target_index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "在一个或多个索引的分片上强制合并")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_forcemerge", method = RequestMethod.POST)
    public Response _forcemerge(@ApiParam(value = "索引名称(可以用,分隔,要操作所有索引，请使用_all)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._forcemerge(index);
        return Response.Ok(JSONObject.parse(s));
    }
}
















