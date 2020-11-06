package demo.elastic.search.po.request.dsl.full;

import demo.elastic.search.feign.SearchFullTextService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
@Slf4j
public class MultiMatchRequestTest {

    @Resource
    private SearchFullTextService searchFullTextService;

    /**
     * 同时检索多个字段
     */
    @Test
    public void testMatchAllRequest() {
        SearchSourceBuilder<MultiMatchQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(2).query(QueryBuilders.multiMatchQuery(Arrays.asList("name.firstname", "name.lastname"), "chao"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchFullTextService.match_multi_match_search("index_bulk", request);
        log.info("response:{}", response);
    }
}
