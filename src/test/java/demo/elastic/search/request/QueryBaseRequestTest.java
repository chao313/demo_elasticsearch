//package demo.elastic.search.request;
//
//import demo.elastic.search.feign.SearchService;
//import demo.elastic.search.po.request.QueryBaseRequest;
//import demo.elastic.search.po.request.dsl.compound.BoolRequest;
//import demo.elastic.search.po.request.dsl.term.RegexpRequest;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//
//@SpringBootTest
//@Slf4j
//public class QueryBaseRequestTest {
//
//    @Autowired
//    SearchService searchService;
//
//
//    @Test
//    public void testBoolMustAndMustNotRequest() {
//        BoolRequest.BoolQuery boolQuery = BoolRequest.builderQuery(Arrays.asList(RegexpRequest.builderQuery("F2_0088", "北京字节跳动网络技术有限公司")), null, null, null);
//        QueryBaseRequest request = QueryBaseRequest.builderRequest(boolQuery);
//        log.info("请求body:{}", request.getRequestBody());
//        String response = searchService._search("tb_object_0088", request);
//        log.info("response:{}", response);
//    }
//}
