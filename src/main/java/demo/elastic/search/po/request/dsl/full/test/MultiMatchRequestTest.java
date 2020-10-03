package demo.elastic.search.po.request.dsl.full.test;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.full.MultiMatchRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
public class MultiMatchRequestTest {

    @Autowired
    private SearchService searchService;

    @Test
    public void testMatchAllRequest() {
        MultiMatchRequest request = MultiMatchRequest.builderRequest(Arrays.asList("F2_0088"), "Apple Inc.", MultiMatchRequest.MultiMatchType.most_fields, null);
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.match_multi_match_search("comstore_tb_object_0088", request);
        log.info("response:{}", response);
    }
}
