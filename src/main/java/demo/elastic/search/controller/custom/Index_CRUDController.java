package demo.elastic.search.controller.custom;

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


/**
 * Index CRUD 相关
 */
@RequestMapping(value = "/Index_CRUDController")
@RestController
public class Index_CRUDController {

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
                    name = CustomInterceptConfig.HEADER_KEY,
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
                    name = CustomInterceptConfig.HEADER_KEY,
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
                    name = CustomInterceptConfig.HEADER_KEY,
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

}
















