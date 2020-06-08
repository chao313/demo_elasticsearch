package demo.elasticSearch.client.test;

import com.alibaba.fastjson.JSONObject;
import demo.elasticSearch.util.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchAction;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.core.MainResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class LocalRestHighLevelClientTest extends InitBean {


    /**
     * 获取集群信息
     */
    @Test
    public void info() throws IOException {
        MainResponse info = localRestHighLevelClient.info(RequestOptions.DEFAULT);
        log.info("info:{}", JSONObject.toJSON(info));
    }

    /**
     * 插入
     */
    @Test
    public void index() throws IOException {
        IndexRequest indexRequest = new IndexRequest();
        indexRequest.index(INDEX);
        indexRequest.id(UUID.randomUUID().toString());
        indexRequest.source(MapUtil.$("key", "value", "key1", "value1", "key2", "value2"));
        IndexResponse indexResponse = localRestHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        log.info("info:{}", JSONObject.toJSON(indexResponse));
    }


    /**
     * https://blog.csdn.net/carlislelee/article/details/52598022
     */
//    @Test
//    public void search() throws IOException {
//        SearchRequestBuilder searchRequestBuilder =
//                new SearchRequestBuilder(localRestHighLevelClient.restHighLevelClient, SearchAction.INSTANCE);
//        TermsAggregationBuilder termsAggregationBuilder = AggregationBuilders.terms("player_count ").field("team");
//        searchRequestBuilder.addAggregation(termsAggregationBuilder);
//        SearchResponse response = searchRequestBuilder.execute().actionGet();
//        log.info("response:{}", response);
//    }

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
    }

}
