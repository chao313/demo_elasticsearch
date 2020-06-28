package demo.elastic.search.service;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

public class ESService {
    /**
     * 创建一个ES的客户端
     *
     * @param hostname e.g.  10.202.16.9
     * @param port     9200
     * @param scheme   http
     * @return
     */
    public RestHighLevelClient createClient(String hostname, int port, String scheme) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, scheme)
                )
        );
        return client;
    }
}
