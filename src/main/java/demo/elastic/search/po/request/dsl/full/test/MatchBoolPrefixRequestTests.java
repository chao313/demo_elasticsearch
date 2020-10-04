package demo.elastic.search.po.request.dsl.full.test;

import demo.elastic.search.feign.FullTextService;
import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.full.MatchBoolPrefixRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class MatchBoolPrefixRequestTests {

    @Autowired
    private FullTextService fullTextService;

    @Test
    public void testMatchBoolPrefixRequest() {
        MatchBoolPrefixRequest request = MatchBoolPrefixRequest.builderRequest("F2_0088", "字节跳动");
        log.info("请求body:{}", request.getRequestBody());
        String response = fullTextService.match_bool_prefix_search("comstore_tb_object_0088", request);
        log.info("response:{}", response);
    }
}
