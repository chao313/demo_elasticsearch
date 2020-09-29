package demo.elastic.search.feign.jaxrs;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;


public interface JAXRSService {

    @ApiOperation(value = "列出Cat的全部接口")
    @RequestMapping(value = "/_cat", method = RequestMethod.GET)
    String _cat();

    /**
     * 搜索
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search(@PathParam("index") @PathVariable(value = "index") String index,
                   @RequestBody String body);

    @ApiOperation(value = "返回集群的运行状况")
    @RequestMapping(value = "/_cat/health", method = RequestMethod.GET)
    String _cat_health(@RequestParam(value = "v") boolean v);

}
