package demo.elastic.search.service.search.test;

import demo.elastic.search.service.search.ESSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@Slf4j
public class RegexpQueryTest {

    /**
     * 测试正则 - 测试通过
     *
     * @throws IOException
     */
    @Test
    public void RegexpQuery() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.regexpQuery("F2_0088", "H.*"));
        SearchResponse searchResponse = new ESSearchService().init("9200.utools.club", 80, "http")
                .searchBaseDefaultOptions(searchSourceBuilder, "comstore_tb_object_0088_v2");
        Arrays.stream(searchResponse.getHits().getHits()).forEach(searchHit -> {
            log.info("_index:{}", searchHit.getIndex());
            log.info("_type:{}", searchHit.getType());
            log.info("_id:{}", searchHit.getId());
            log.info("_score:{}", searchHit.getScore());
            log.info("Source:{}", searchHit.getSourceAsString());
            log.info("Source:{}", searchHit.getSourceAsMap());
        });

        Arrays.stream(searchResponse.getHits().getHits()).forEach(searchHit -> {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            log.info("F2_0088:{}", sourceAsMap.get("F2_0088"));
        });
    }
/**
     * 测试正则 - 测试通过
     *
     * @throws IOException
     */
    @Test
    public void RegexpQuery2() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.regexpQuery("F2_0088", "SCHOOL OF URBAN AND WILDERNESS SURVIVAL, INC."));
        SearchResponse searchResponse = new ESSearchService().init("10.202.16.9", 9200, "http")
                .searchBaseDefaultOptions(searchSourceBuilder, "comstore_tb_object_0088_v2");
        Arrays.stream(searchResponse.getHits().getHits()).forEach(searchHit -> {
            log.info("_index:{}", searchHit.getIndex());
            log.info("_type:{}", searchHit.getType());
            log.info("_id:{}", searchHit.getId());
            log.info("_score:{}", searchHit.getScore());
            log.info("Source:{}", searchHit.getSourceAsString());
            log.info("Source:{}", searchHit.getSourceAsMap());
        });

        Arrays.stream(searchResponse.getHits().getHits()).forEach(searchHit -> {
            Map<String, Object> sourceAsMap = searchHit.getSourceAsMap();
            log.info("F2_0088:{}", sourceAsMap.get("F2_0088"));
        });
    }

}
