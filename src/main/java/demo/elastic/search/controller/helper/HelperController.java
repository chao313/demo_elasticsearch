package demo.elastic.search.controller.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.elastic.search.framework.Response;
import demo.elastic.search.po.helper.DSLHelper;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.compound.BoolQuery;
import demo.elastic.search.service.RedisService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        //must
        dslHelper.getMust().getExists().forEach(exists -> {
            boolQuery.must(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getMust().getTerm().forEach(term -> {
            boolQuery.must(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getMust().getTerms().forEach(terms -> {
            boolQuery.must(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getMust().getRange().forEach(range -> {
            boolQuery.must(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getLte()));
        });
        dslHelper.getMust().getRegexp().forEach(regexp -> {
            boolQuery.must(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getMust().getPrefix().forEach(prefix -> {
            boolQuery.must(QueryBuilders.regexpQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getMust().getWildcard().forEach(wildcard -> {
            boolQuery.must(QueryBuilders.regexpQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getMust().getFuzzy().forEach(fuzzy -> {
            boolQuery.must(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getMust().getIds().getValue() && dslHelper.getMust().getIds().getValue().size() > 0) {
            boolQuery.must(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        //must_not
        dslHelper.getMust_not().getExists().forEach(exists -> {
            boolQuery.must_not(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getMust_not().getTerm().forEach(term -> {
            boolQuery.must_not(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getMust_not().getTerms().forEach(terms -> {
            boolQuery.must_not(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getMust_not().getRange().forEach(range -> {
            boolQuery.must_not(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getLte()));
        });
        dslHelper.getMust_not().getRegexp().forEach(regexp -> {
            boolQuery.must_not(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getMust_not().getPrefix().forEach(prefix -> {
            boolQuery.must_not(QueryBuilders.regexpQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getMust_not().getWildcard().forEach(wildcard -> {
            boolQuery.must_not(QueryBuilders.regexpQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getMust_not().getFuzzy().forEach(fuzzy -> {
            boolQuery.must_not(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getMust_not().getIds().getValue() && dslHelper.getMust_not().getIds().getValue().size() > 0) {
            boolQuery.must_not(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }
        //should
        dslHelper.getShould().getExists().forEach(exists -> {
            boolQuery.should(QueryBuilders.existsQuery(exists.getField()));
        });
        dslHelper.getShould().getTerm().forEach(term -> {
            boolQuery.should(QueryBuilders.termQuery(term.getField(), term.getValue()));
        });
        dslHelper.getShould().getTerms().forEach(terms -> {
            boolQuery.should(QueryBuilders.termsQuery(terms.getField(), terms.getValue()));
        });
        dslHelper.getShould().getRange().forEach(range -> {
            boolQuery.should(QueryBuilders.rangeQuery(range.getField(), range.getGte(), range.getLte()));
        });
        dslHelper.getShould().getRegexp().forEach(regexp -> {
            boolQuery.should(QueryBuilders.regexpQuery(regexp.getField(), regexp.getValue()));
        });
        dslHelper.getShould().getPrefix().forEach(prefix -> {
            boolQuery.should(QueryBuilders.regexpQuery(prefix.getField(), prefix.getValue()));
        });
        dslHelper.getShould().getWildcard().forEach(wildcard -> {
            boolQuery.should(QueryBuilders.regexpQuery(wildcard.getField(), wildcard.getValue()));
        });

        dslHelper.getShould().getFuzzy().forEach(fuzzy -> {
            boolQuery.should(QueryBuilders.fuzzyQuery(fuzzy.getField(), fuzzy.getValue()));
        });
        if (null != dslHelper.getShould().getIds().getValue() && dslHelper.getShould().getIds().getValue().size() > 0) {
            boolQuery.should(QueryBuilders.idsQuery(dslHelper.getFilter().getIds().getValue()));
        }

        return Response.Ok(new JsonMapper().readTree(boolQuery.getRequestBody()));
    }
}
