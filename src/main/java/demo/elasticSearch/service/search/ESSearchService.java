package demo.elasticSearch.service.search;

import com.alibaba.fastjson.JSON;
import demo.elasticSearch.service.DefaultESIndexService;
import demo.elasticSearch.service.ESService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 这里是和index相关的基本操作(这里尽量和jar包里面的保持一致)
 */
@Slf4j
@Service
public class ESSearchService extends ESService {

    public RestHighLevelClient client;

    public ESSearchService init(String hostname, int port, String scheme) {
        this.client = this.createClient(hostname, port, scheme);
        return this;
    }

    /**
     * 最底层，兼容全部的查询条件
     * 参数是  SearchRequest
     */
    public SearchResponse searchBase(RequestOptions options,
                                     SearchRequest searchRequest) throws IOException {
        SearchResponse searchResponse = this.client.search(searchRequest, options);
        return searchResponse;
    }


    /**
     * 最底层的上上一层 ，兼容全部的查询条件
     * 参数是 SearchSourceBuilder 包裹在 SearchSourceBuilder
     */
    public SearchResponse searchBase(RequestOptions options,
                                     SearchSourceBuilder searchSourceBuilder,
                                     String... indices) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indices);
        searchRequest.source(searchSourceBuilder);//指定查询的条件(这里集成了全部的查询条件)
        SearchResponse searchResponse = this.client.search(searchRequest, options);
        return searchResponse;
    }


    /**
     * 使用默认的查询参数来查询
     */
    public SearchResponse searchBaseDefaultOptions(SearchSourceBuilder searchSourceBuilder,
                                                   String... indices) throws IOException {
        SearchResponse searchResponse = this.searchBase(RequestOptions.DEFAULT, searchSourceBuilder, indices);
        return searchResponse;
    }


    /**
     *
     */
    public SearchResponse querySearch(RequestOptions options, String... indices) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indices);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();  // 默认配置
        sourceBuilder.query(QueryBuilders.termQuery("F2_0088", "MONEYTREE, INC.")); // 设置搜索，可以是任何类型的 QueryBuilder
        sourceBuilder.from(0); // 起始 index
        sourceBuilder.size(5); // 大小 size
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS)); // 设置搜索的超时时间
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = this.client.search(searchRequest, options);
        return searchResponse;
    }

    public static void main(String[] args) throws IOException {
        new ESSearchService().init("10.202.16.9", 9200, "http")
                .querySearch(RequestOptions.DEFAULT, "comstore_tb_object_0088_v2");

    }


}


