package demo.elastic.search.po.request.dsl.term;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class RangeRequestTests {

    @Resource
    private SearchService searchService;

    /**
     * 测试int型
     */
    @Test
    public void testRangeRequestInt() {
        SearchSourceBuilder<RangeQuery,VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.rangeQuery("age", "34", "34"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_range("index_bulk", request);
        log.info("response:{}", response);
    }

    /**
     * 测试Str型范围
     */
    @Test
    public void testRangeRequestStr() {
        SearchSourceBuilder<RangeQuery,VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.rangeQuery("employer", "Fitcore", "Mixers"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_range("index_bulk", request);
        log.info("response:{}", response);
    }

    /**
     * 测试Str型大于
     */
    @Test
    public void testRangeRequestGteStr() {
        SearchSourceBuilder<RangeQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.rangeGteQuery("employer", "Fitcore"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_range("index_bulk", request);
        log.info("response:{}", response);
    }
}
