package demo.elastic.search.request.dsl.full;

import demo.elastic.search.feign.SearchFullTextService;
import demo.elastic.search.po.request.dsl.full.MatchRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MatchRequestTests {

    @Autowired
    private SearchFullTextService searchFullTextService;

    @Test
    public void testMatchAllRequest() {
        MatchRequest request = MatchRequest.builderRequest("F2_0088", "字节跳动");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchFullTextService.match_search("comstore_tb_object_0088", request);
        log.info("response:{}", response);
    }
}
