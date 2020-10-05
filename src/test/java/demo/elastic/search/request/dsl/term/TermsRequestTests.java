package demo.elastic.search.request.dsl.term;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.TermsRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
public class TermsRequestTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testTermsRequest() {
        TermsRequest request = TermsRequest.builderRequest("F1_0088", Arrays.asList("1310480964", "1310481034"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
