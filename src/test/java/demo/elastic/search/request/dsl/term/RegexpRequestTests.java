package demo.elastic.search.request.dsl.term;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.term.RegexpQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class RegexpRequestTests {

    @Resource
    private SearchService searchService;

    @Test
    public void testRegexpRequest() {
        SearchSourceBuilder<RegexpQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.regexpQuery("city", "dant.*"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_regexp("index_bulk", request);
        log.info("response:{}", response);
    }

    @Test
    public void testRegexpRequest2() {
        SearchSourceBuilder<RegexpQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.regexpQuery("F1_0088", "[0-9]*"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_regexp("tb_object_0088", request);
        log.info("response:{}", response);
    }

    @Test
    public void testRegexpRequest3() {
        SearchSourceBuilder<RegexpQuery, VoidAggs> request = new SearchSourceBuilder<>();
        String regex = ".*";
        request.from(0).size(1).query(QueryBuilders.regexpQuery("F1_0088", regex));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_regexp("tb_object_0088", request);
        log.info("response:{}", response);
    }
}
