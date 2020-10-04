package demo.elastic.search.po.request.dsl.full.test;

import demo.elastic.search.feign.SearchFullTextService;
import demo.elastic.search.po.request.dsl.full.MatchPhraseRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MatchPhraseRequestTests {

    @Autowired
    private SearchFullTextService searchFullTextService;

    @Test
    public void testMatchAllRequest() {
        MatchPhraseRequest request = MatchPhraseRequest.builderRequest("F2_0088", "Apple Inc.");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchFullTextService.match_phrase_search("comstore_tb_object_0088", request);
        log.info("response:{}", response);
    }
}
