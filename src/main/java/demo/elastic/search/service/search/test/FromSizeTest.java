package demo.elastic.search.service.search.test;

import com.alibaba.fastjson.JSON;
import demo.elastic.search.service.search.ESSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
public class FromSizeTest {

    /**
     * 最大只能是10000 和设置相关
     *
     * @throws IOException
     */
    @Test
    public void FromSize() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.from(1000);
        searchSourceBuilder.size(1000);
        SearchResponse searchResponse = new ESSearchService().init("9200.utools.club", 80, "http")
                .searchBaseDefaultOptions(searchSourceBuilder, "comstore_tb_object_0088_v2");
        log.info("searchResponse:{}", JSON.toJSON(searchResponse.getHits()));
    }


}
