package demo.elastic.search.request.dsl.term;

import com.fasterxml.jackson.core.JsonProcessingException;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.dsl.term.TermQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class TermRequestTests {

    @Resource
    private SearchService searchService;

    @Test
    public void testTermRequest() throws JsonProcessingException {
        SearchSourceBuilder<TermQuery> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.termQuery("city", "dante"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_term("index_bulk", request);
        log.info("response:{}", response);
    }
}
