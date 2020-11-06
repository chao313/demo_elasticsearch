package demo.elastic.search.po.request.script;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import demo.elastic.search.po.request.dsl.term.ExistsQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class ScriptRequestTests {

    @Resource
    private SearchService searchService;

    /**
     * 脚本测试
     */
    @Test
    public void testScriptRequest() {
        SearchSourceBuilder<ScriptQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(ScriptQuery.builderQuery("doc[age]"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService._search("index_bulk", request);
        log.info("response:{}", response);
    }
}
