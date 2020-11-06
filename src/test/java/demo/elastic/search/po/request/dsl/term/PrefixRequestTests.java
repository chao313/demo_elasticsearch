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
public class PrefixRequestTests {

    @Resource
    private SearchService searchService;

    @Test
    public void testPrefixRequest() {
        SearchSourceBuilder<PrefixQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.prefixQuery("email", "josienelson"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_prefix("index_bulk", request);
        log.info("response:{}", response);
    }
}
