package demo.elasticSearch.service;

import demo.elasticSearch.service.index.ESIndexService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 这里是拓展版，是ElasticSearch的加强版
 */
@Slf4j
@Service
public class DefaultESIndexService extends ESIndexService {
    /**
     *
     */
    public GetIndexResponse queryAllIndex(String hostname, int port, String scheme) throws IOException {
        GetIndexResponse indexResponse = this.queryIndex(hostname, port, scheme, RequestOptions.DEFAULT, "_all");
        return indexResponse;

    }

    /**
     *
     */
    public GetIndexResponse queryDefaultIndex(String hostname, int port, String scheme, String... indices) throws IOException {
        GetIndexResponse indexResponse = this.queryIndex(hostname, port, scheme, RequestOptions.DEFAULT, indices);
        return indexResponse;

    }


    public static void main(String[] args) throws IOException {
//        new ElasticSearchIndexService().queryAllIndex("10.202.16.9", 9200, "http");
        new DefaultESIndexService().queryDefaultIndex("10.202.16.9", 9200, "http", "comstore_tb_object_0088_v2");
    }
}


