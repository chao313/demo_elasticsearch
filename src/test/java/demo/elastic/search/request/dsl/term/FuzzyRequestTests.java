package demo.elastic.search.request.dsl.term;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.term.FuzzyQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class FuzzyRequestTests {

    @Resource
    private SearchService searchService;

    /**
     * 默认模糊检索
     */
    @Test
    public void testCommFuzzyRequest() {
        SearchSourceBuilder<FuzzyQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0)
                .size(1)
                .query(QueryBuilders.fuzzyQuery("city", "danta"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_fuzzy("index_bulk", request);
        log.info("response:{}", response);
    }

    @Test
    public void testFuzzyRequest() {
        SearchSourceBuilder<FuzzyQuery,VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.fuzzyQuery("city", "danta", "10"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_fuzzy("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
