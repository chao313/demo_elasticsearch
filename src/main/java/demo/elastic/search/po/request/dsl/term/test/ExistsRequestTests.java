package demo.elastic.search.po.request.dsl.term.test;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.ExistsRequest;
import demo.elastic.search.po.request.dsl.term.TermsRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
public class ExistsRequestTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testExistsRequest() {
        ExistsRequest request = ExistsRequest.builderRequest("F26_0088");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
