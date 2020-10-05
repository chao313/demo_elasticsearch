package demo.elastic.search.client;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

@Slf4j
public class Localxxx extends InitBean {


    /**
     * 插入
     */
    @Test
    public void index() throws IOException {
        localRestHighLevelClient.restHighLevelClient.getLowLevelClient();
    }


    /**
     * https://blog.csdn.net/carlislelee/article/details/52598022
     */
    @Test
    public void search() throws IOException {
        Settings.Builder settings = Settings.builder();
        settings.put("cluster.name", "lzxsearch");
        TransportClient client = new PreBuiltTransportClient(settings.build())
                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.202.16.9"), 9302));


        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(INDEX);
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("F1_0088_count ").field("F1_0088");
        searchRequestBuilder.addAggregation(termsAggregationBuilder);
        SearchResponse response = searchRequestBuilder.execute().actionGet();
        log.info("response:{}", response);
    }

    /**
     * 插入
     */
    @Test
    public void xxx() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("_count").field("F1_0088");
//        termsAggregationBuilder.order(BucketOrder.aggregation("value2_count ", true));
        termsAggregationBuilder.order(Arrays.asList(BucketOrder.aggregation("_count", true)));
        searchSourceBuilder.aggregation(termsAggregationBuilder);
        SearchRequest request = new SearchRequest(INDEX);
        request.source(searchSourceBuilder);
        SearchResponse response = localRestHighLevelClient.search(request, RequestOptions.DEFAULT);
        log.info("info:{}", JSONObject.toJSON(response));
//        localRestHighLevelClient.restHighLevelClient.getLowLevelClient().performRequest();
    }

}
