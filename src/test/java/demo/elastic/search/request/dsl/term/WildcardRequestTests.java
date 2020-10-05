package demo.elastic.search.request.dsl.term;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.WildcardRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class WildcardRequestTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testWildcardRequest() {
        WildcardRequest request = WildcardRequest.builderRequest("F2_0088", "北京字节跳动*");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
