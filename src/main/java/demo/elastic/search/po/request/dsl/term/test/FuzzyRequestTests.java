package demo.elastic.search.po.request.dsl.term.test;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.FuzzyRequest;
import demo.elastic.search.po.request.dsl.term.WildcardRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class FuzzyRequestTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testCommFuzzyRequest() {
        FuzzyRequest request = FuzzyRequest.builderRequest("F2_0088", "北京字节跳动网络技术有限公司");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }

    @Test
    public void testFuzzyRequest() {
        FuzzyRequest request = FuzzyRequest.builderRequest("F2_0088", "北京字节跳动网络技术有限公司", "10");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
