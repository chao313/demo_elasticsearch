package demo.elastic.search.po.request.dsl.term.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.TermRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class TermRequestTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testTermRequest() throws JsonProcessingException {
        TermRequest termRequest = TermRequest.builderRequest("F1_0088", "1310480964");
        log.info("请求body:{}", termRequest.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", termRequest);
        log.info("response:{}", response);
    }
}
