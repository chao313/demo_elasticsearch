package demo.elasticSearch.service.search.test;

import com.alibaba.fastjson.JSON;
import demo.elasticSearch.service.search.ESSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;

@Slf4j
public class ScrollTest {

    /**
     * 最大只能是10000 和设置相关
     *
     * @throws IOException
     *
     * InetAddress.getByName("10.200.3.167"), Integer.parseInt("9300")))
     * //                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.200.5.226"), Integer.parseInt("9300")))
     * //                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.200.5.166"), Integer.parseInt("9300")));
     *
     * 10.200.3.167
     * 10.200.5.226
     * 10.200.5.166
     */
    @Test
    public void ScrollIdSearch() throws IOException {
        SearchRequest searchRequest = new SearchRequest("comstore_tb_object_0088_v2");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(1000); //创建搜索请求及其对应的搜索源生成器。还可以选择设置大小来控制一次检索多少个结果。
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueSeconds(10L)); //设置scroll间隔
        ESSearchService esSearchService = new ESSearchService().init("9200.utools.club", 80, "http");
        SearchResponse searchResponse = esSearchService.searchBase(RequestOptions.DEFAULT, searchRequest);
        log.info("ScrollId:{}", JSON.toJSON(searchResponse.getScrollId()));

        SearchResponse searchScrollResponse = null;
        do {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(searchResponse.getScrollId()); //通过设置所需的scroll id和scroll间隔来创建搜索scroll请求
            scrollRequest.scroll(TimeValue.timeValueSeconds(30));
            searchScrollResponse = esSearchService.client.scroll(scrollRequest, RequestOptions.DEFAULT);
            log.info("scroll 取出结果:{}", searchScrollResponse.getHits().getHits());
            log.info("scroll id结果:{}", searchScrollResponse.getScrollId());
        } while (searchScrollResponse.getHits().getHits().length > 0);

    }


}
