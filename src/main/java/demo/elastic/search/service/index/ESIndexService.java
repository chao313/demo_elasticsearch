package demo.elastic.search.service.index;

import demo.elastic.search.service.ESService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * 这里是和index相关的基本操作(这里尽量和jar包里面的保持一致)
 */
@Slf4j
@Service
public class ESIndexService extends ESService {


    /**
     * 查询 Index 相关数据
     * private Map<String, MappingMetaData> mappings;
     * private Map<String, List<AliasMetaData>> aliases;
     * private Map<String, Settings> settings;
     * private Map<String, Settings> defaultSettings;
     *
     * @param hostname
     * @param port
     * @param scheme
     * @param options  请求参数
     * @param indices  变长参数，需要查询的index(全部就是_all)
     * @throws IOException 最终是生成url :   http://10.202.16.9:9200/comstore_tb_object_0088_v2?master_timeout=30s&ignore_throttled=false&ignore_unavailable=false&expand_wildcards=open%2Cclosed&allow_no_indices=false
     */
    public GetIndexResponse queryIndex(String hostname, int port, String scheme, RequestOptions options, String... indices) throws IOException {
        RestHighLevelClient client = this.createClient(hostname, port, scheme);
        GetIndexRequest getIndexRequest = new GetIndexRequest(indices);
        GetIndexResponse indexResponse = client.indices().get(getIndexRequest, options);
        return indexResponse;
    }


}


