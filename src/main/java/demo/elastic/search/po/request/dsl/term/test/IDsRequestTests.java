package demo.elastic.search.po.request.dsl.term.test;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.IDsRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
@Slf4j
public class IDsRequestTests {

    @Autowired
    private SearchService searchService;

    @Test
    public void testIDsRequest() {
        IDsRequest request = IDsRequest.builderRequest(Arrays.asList("eec973be-16fe-43e2-b5e7-94a3d1870327", "8a15b22b-6479-4c3e-a4d3-c4c70fa9afbf"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
