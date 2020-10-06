package demo.elastic.search.request.dsl.full;

import demo.elastic.search.feign.SearchFullTextService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.dsl.full.MatchPhrasePrefixQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class MatchPhrasePrefixRequestTest {

    @Resource
    private SearchFullTextService searchFullTextService;

    @Test
    public void testMatchAllRequest() {
        SearchSourceBuilder<MatchPhrasePrefixQuery> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.matchPhrasePrefixQuery("city", "dant"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchFullTextService.match_phrase_prefix_search("index_bulk", request);
        log.info("response:{}", response);
    }
}
