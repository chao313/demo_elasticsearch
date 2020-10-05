package demo.elastic.search.request;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.dsl.term.TermQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
@Slf4j
public class SearchSourceBuilderTest {

    @Resource
    SearchService searchService;


    /**
     * 测试searchSourceBuilder 构造请求体
     */
    @Test
    public void Test() {
        SearchSourceBuilder<TermQuery> searchSourceBuilder = new SearchSourceBuilder<>();
        searchSourceBuilder.from(0)
                .size(1)
                .source(Arrays.asList("age", "address"))
                .query(QueryBuilders.termQuery("age", "30"));
        log.info("请求body:{}", searchSourceBuilder.getRequestBody());
        String response = searchService._search("index_bulk", searchSourceBuilder);
        log.info("response:{}", response);
    }
}
