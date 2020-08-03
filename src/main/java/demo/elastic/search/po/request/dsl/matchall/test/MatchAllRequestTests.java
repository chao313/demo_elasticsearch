package demo.elastic.search.po.request.dsl.matchall.test;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.matchall.MatchAllRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MatchAllRequestTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testMatchAllRequest() {
        MatchAllRequest request = MatchAllRequest.builderRequest();
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.match_all_search("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
