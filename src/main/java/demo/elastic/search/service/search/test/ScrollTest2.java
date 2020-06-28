package demo.elastic.search.service.search.test;

import demo.elastic.search.service.search.ScrollService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class ScrollTest2 {

    public List<String> F26_0088Values = Arrays.asList(
            "1222502470",
            "1222502471",
            "1222502472",
            "1222502473",
            "1222502474",
            "1222502475",
            "1222502476",
            "1222502477",
            "1222502478",
            "1222502479",
            "1222502480",
            "1222502481",
            "1222502484",
            "1222502485",
            "1222502487",
            "1222502489",
            "1222502541",
            "1222502542",
            "1222502543",
            "1222502544",
            "1222502545",
            "1222502546",
            "1222502549",
            "1222502550",
            "1222502552",
            "1222502553",
            "1222502554",
            "1222502555",
            "1222502556",
            "1222502557",
            "1223792922",
            "1223792923",
            "1223792930",
            "1223792931",
            "1223792932",
            "1223792933",
            "1223792934",
            "1223792935",
            "1223792936",
            "1223792937",
            "1223792938",
            "1223792939",
            "1223792940",
            "1223792941",
            "1223792942",
            "1223792944",
            "1223792945",
            "1223792946",
            "1223792947",
            "1223792948",
            "1223792949",
            "1223792952");


    @Test
    public void ScrollIdSearch() throws IOException {

        List<String> indices = Arrays.asList("comstore_tb_object_0088");
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        F26_0088Values.forEach(value -> {
            boolQueryBuilder.must(QueryBuilders.termQuery("F26_0088", "1223793027"));
        });
        int size = 1000;
        long seconds = 1000;
        String hostname = "10.202.16.9";
        int port = 9200;
        String scheme = "http";

        new ScrollService().ScrollIdSearch(indices, boolQueryBuilder, size, seconds, hostname, port, scheme, new Consumer<SearchHit[]>() {
            @Override
            public void accept(SearchHit[] searchHits) {
                Arrays.stream(searchHits).forEach(searchHit -> {
                    //todo 新闻查询
                });

            }
        });
    }

}
