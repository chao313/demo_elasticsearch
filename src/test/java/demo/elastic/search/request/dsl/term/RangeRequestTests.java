package demo.elastic.search.request.dsl.term;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.term.RangeRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class RangeRequestTests {

    @Autowired
    private SearchService searchService;

    /**
     * 测试int型
     */
    @Test
    public void testRangeRequestInt() {
        RangeRequest request = RangeRequest.builderRequest("F1_0088", "1026902501", "1026902501");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }

    /**
     * 测试Str型范围
     */
    @Test
    public void testRangeRequestStr() {
        RangeRequest request = RangeRequest.builderRequest("F2_0088", "北京字节跳动网络技术有限公司", "北京字节跳动网络技术有限公司");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }

    /**
     * 测试Str型大于
     */
    @Test
    public void testRangeRequestGteStr() {
        RangeRequest request = RangeRequest.builderGteRequest("F2_0088", "北京字节跳动网络技术有限公司");
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
