package demo.elastic.search;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.dsl.compound.BoolRequest;
import demo.elastic.search.po.request.dsl.term.TermRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@Slf4j
class DemoApplicationTests {
    @Autowired
    SearchService searchService;

    @Test
    void contextLoads() {
        xx();
    }


    @Test
    public void xx() {
        BoolRequest boolRequest = new BoolRequest();
        TermRequest.TermQuery termQuery = TermRequest.builderQuery("F1_0088", "1310480964");
        boolRequest.getQuery().getBool().getMust().add(termQuery);
        String response = searchService.DSL_bool_search("tb_object_0088", boolRequest);
        log.info("response:{}", response);
    }
}
