package demo.elastic.search.po.request.dsl.full.test;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.full.MatchPhrasePrefixRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MatchPhrasePrefixRequestTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void testMatchAllRequest() {
        MatchPhrasePrefixRequest request = MatchPhrasePrefixRequest.builderRequest("F2_0088", "Apple Inc.");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.match_phrase_prefix_search("comstore_tb_object_0088", request);
        log.info("response:{}", response);
    }
}
