package demo.elastic.search.controller.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.helper.DSLHelper;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.compound.BoolQuery;
import demo.elastic.search.po.request.dsl.term.FuzzyQuery;
import demo.elastic.search.service.RedisService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * helper使用
 */
@Slf4j
@RequestMapping(value = "/HelperController")
@RestController
public class HelperController {

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "根据ScrollId获取数据(分页数据)")
    @PostMapping(value = "/DSLHelper")
    public Response DSLHelper(@RequestBody DSLHelper dslHelper) throws JsonProcessingException {

        BoolQuery boolQuery = QueryBuilders.boolQuery();


        SearchSourceBuilder<BoolQuery, VoidAggs> request = new SearchSourceBuilder<>();

        dslHelper.getFilter().getExists().forEach(exists -> {
            boolQuery.filter(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getFilter().getTerm().forEach(term -> {
            boolQuery.filter(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getFilter().getTerms().forEach(terms -> {
            boolQuery.filter(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getFilter().getRange().forEach(range -> {
            boolQuery.filter(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getLte()));
        });
        dslHelper.getFilter().getRegexp().forEach(regexp -> {
            boolQuery.filter(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getFilter().getPrefix().forEach(prefix -> {
            boolQuery.filter(QueryBuilders.regexpQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getFilter().getWildcard().forEach(wildcard -> {
            boolQuery.filter(QueryBuilders.regexpQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getFilter().getFuzzy().forEach(fuzzy -> {
            boolQuery.filter(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getFilter().getIds().getValue() && dslHelper.getFilter().getIds().getValue().size() > 0) {
            boolQuery.filter(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }

        return Response.Ok(new JsonMapper().readTree(boolQuery.getRequestBody()));
    }
}
