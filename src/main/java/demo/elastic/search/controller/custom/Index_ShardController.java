package demo.elastic.search.controller.custom;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.config.web.CustomInterceptConfig;
import demo.elastic.search.feign.CatService;
import demo.elastic.search.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 索引 分片相关
 */
@RequestMapping(value = "/Index_ShardController")
@RestController
public class Index_ShardController {

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回分片信息", notes = "返回节点包含哪些分片,是主分片还是复制分片,doc的数量,使用的磁盘空间")
    @GetMapping(value = "/_cat/shards/{index}")
    public String _cat_shards(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                              @PathVariable(value = "index") String index,
                              @ApiParam(value = "要显示的以逗号分隔的列名称列表(index,shard,prirep...)") @RequestParam(value = "h") String h) {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        return catService._cat_shards_index(v, index, h);
    }
}
















