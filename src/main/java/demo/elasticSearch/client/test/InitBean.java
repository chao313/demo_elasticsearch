package demo.elasticSearch.client.test;

import demo.elasticSearch.client.LocalIndicesClient;
import demo.elasticSearch.client.LocalRestHighLevelClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.jupiter.api.BeforeAll;

@Slf4j
public class InitBean {

    static LocalIndicesClient localIndicesClient;

    static LocalRestHighLevelClient localRestHighLevelClient;

    //    public static final String INDEX = "index_tmp";
    public static final String INDEX = "comstore_tb_object_0088";
    public static final String ALIAS = "aliasTmp";

    @BeforeAll
    public static void init() {
        String hostname = "10.202.16.9";
//        String hostname = "172.17.0.1";
//        String hostname = "192.168.0.105";
//        String hostname = "39.107.236.187";
        int port = 9200;
        String scheme = "http";
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(hostname, port, scheme)
                )
        );
        InitBean.localIndicesClient = new LocalIndicesClient(restHighLevelClient.indices());
        InitBean.localRestHighLevelClient = new LocalRestHighLevelClient(restHighLevelClient);
    }
}
