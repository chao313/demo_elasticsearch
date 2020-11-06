package demo.elastic.search.po.request.dsl.full;

import demo.elastic.search.feign.SearchFullTextService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class MatchPhraseRequestTests {

    @Resource
    private SearchFullTextService searchFullTextService;

    @Test
    public void match_phrase_search() {
        SearchSourceBuilder<MatchPhraseQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.matchPhraseQuery("name.firstname", "hai chao"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchFullTextService.match_phrase_search("index_bulk", request);
        log.info("response:{}", response);
    }
}
