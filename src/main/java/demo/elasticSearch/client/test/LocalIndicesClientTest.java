package demo.elasticSearch.client.test;

import demo.elasticSearch.client.LocalIndicesClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;


@Slf4j
public class LocalIndicesClientTest {
    static LocalIndicesClient localIndicesClient;

    @BeforeAll
    public static void init() {
//        String hostname = "10.202.16.9";
        String hostname = "172.17.0.1";
        int port = 9200;
        String scheme = "http";
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, scheme)
                )
        );
        localIndicesClient = new LocalIndicesClient(restHighLevelClient.indices());
    }

    @Test
    public void testCreate() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("xxxx");
        localIndicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
        log.info("创建index成功");
    }
}
