package demo.elastic.search.controller;

import demo.elastic.search.config.Bootstrap;
import demo.elastic.search.feign.CatService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * 用于 ElasticSearch es使用
 */
@RequestMapping(value = "/CatController")
@RestController
public class CatController {

    @Resource
    private CatService catService;

    /**
     * 列出CAT的全部接口
     *
     * @return
     */
    @ApiOperation(value = "列出Cat的全部接口", notes = "列出CAT的全部接口")
    @GetMapping(value = "/_cat")
    public String cat(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost
    ) {
        String s = catService._cat();
        return s;
    }

    @GetMapping(value = "/_cat/allocation")
    public String _cat_allocation(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_allocation(Boolean.toString(v));
        return s;
    }


    @GetMapping(value = "/_cat/shards")
    public String _cat_shards(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_shards(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/master")
    public String _cat_master(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_master(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/nodes")
    public String _cat_nodes(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_nodes(Boolean.toString(v));
        return s;
    }


    @GetMapping(value = "/_cat/indices")
    public String _cat_indices(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_indices(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/segments")
    public String _cat_segments(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_segments(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/count")
    public String _cat_count(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_count(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/recovery")
    public String _cat_recovery(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_recovery(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/health")
    public String _cat_health(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_health(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/pending_tasks")
    public String _cat_pending_tasks(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_pending_tasks(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/aliases")
    public String _cat_aliases(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_aliases(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/thread_pool")
    public String _cat_thread_pool(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_thread_pool(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/plugins")
    public String _cat_plugins(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_plugins(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/fielddata")
    public String _cat_fielddata(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_fielddata(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/nodeattrs")
    public String _cat_nodeattrs(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_nodeattrs(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/repositories")
    public String _cat_repositories(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_repositories(Boolean.toString(v));
        return s;
    }

    @GetMapping(value = "/_cat/templates")
    public String _cat_templates(
            @ApiParam(value = "elasticSearchHost", allowableValues = Bootstrap.allowableValues)
            @RequestParam(name = "elasticSearchHost", defaultValue = "39.107.236.187:9200")
                    String elasticSearchHost,
            @ApiParam(value = "是否格式化")
            @RequestParam(name = "v", defaultValue = "true")
                    boolean v
    ) {
        String s = catService._cat_templates(Boolean.toString(v));
        return s;
    }


}
















