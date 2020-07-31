package demo.elastic.search.po.request.dsl.term.test;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.ExistsRequest;
import demo.elastic.search.po.request.dsl.term.PrefixRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class PrefixRequestTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testPrefixRequest() {
        PrefixRequest request = PrefixRequest.builderRequest("F1_0088", "20");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
