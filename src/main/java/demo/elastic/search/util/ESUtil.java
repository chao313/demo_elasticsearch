package demo.elastic.search.util;


import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

public class ESUtil {

    public final static String DEV_TB_OBJECT_0088 = "comstore_tb_object_0088";
    public final static String TB_OBJECT_0088 = "tb_object_0088";
    public final static String TB_OBJECT_6070 = "tb_object_6070";


    public static void main(String[] args) {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("10.202.16.9", 9200, "http")
                )
        );
        GetIndexRequest request = new GetIndexRequest();
        request.indices("comstore_tb_object_6317_v2");
        boolean exists = false;
        try {
            exists = client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (exists) {
            System.out.println("test索引库存在");
        } else {
            System.out.println("test索引库不存在");
        }
        //关闭高级客户端实例，以便它所使用的所有资源以及底层 的http客户端实例及其线程得到正确释放
        try {
            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
