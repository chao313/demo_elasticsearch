package demo.elastic.search.client;

import demo.elastic.search.util.MapUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.indices.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;


@Slf4j
public class LocalIndicesClientTest extends InitBean {


    @Test
    public void close() throws IOException {
        CloseIndexRequest request = new CloseIndexRequest(INDEX);
        localIndicesClient.close(request, RequestOptions.DEFAULT);
        log.info("关闭index成功");
    }

    @Test
    public void open() throws IOException {
        OpenIndexRequest request = new OpenIndexRequest(INDEX);
        localIndicesClient.open(request, RequestOptions.DEFAULT);
        log.info("打开index成功");
    }

    /**
     * 创建index
     *
     * @throws IOException
     */
    @Test
    public void testCreate() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest(INDEX);
        localIndicesClient.create(createIndexRequest, RequestOptions.DEFAULT);
        log.info("创建index成功");
    }

    /**
     * 删除index
     *
     * @throws IOException
     */
    @Test
    public void testDelete() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest(INDEX);
        localIndicesClient.delete(request, RequestOptions.DEFAULT);
        log.info("删除index成功");
    }

    /**
     * 获取setting
     *
     * @throws IOException
     */
    @Test
    public void getSettings() throws IOException {
        GetSettingsRequest request = new GetSettingsRequest().indices(INDEX);
        GetSettingsResponse settings = localIndicesClient.getSettings(request, RequestOptions.DEFAULT);
        log.info("success:{}", settings);
    }

    /**
     * 获取setting
     *
     * @throws IOException
     */
    @Test
    public void putSettings() throws IOException {
        UpdateSettingsRequest request = new UpdateSettingsRequest().indices(INDEX);
        request.settings(MapUtil.$("number_of_shards", "2"));
        localIndicesClient.putSettings(request, RequestOptions.DEFAULT);
        log.info("success:{}");
    }

    /**
     * 添加Mapping ????????
     *
     * @throws IOException
     */
    @Test
    public void putMapping() throws IOException {
        PutMappingRequest request = new PutMappingRequest(INDEX);
        request.source(MapUtil.$("test", "_doc"));
        localIndicesClient.putMapping(request, RequestOptions.DEFAULT);
        log.info("删除index成功");
    }

    /**
     * 获取 Mapping
     *
     * @throws IOException
     */
    @Test
    public void getMapping() throws IOException {
        GetMappingsRequest request = new GetMappingsRequest().indices(INDEX);
        GetMappingsResponse mapping = localIndicesClient.getMapping(request, RequestOptions.DEFAULT);
        log.info("成功:{}", mapping);
    }

    /**
     * 更新别名
     */
    @Test
    public void updateAliases() throws IOException {
        IndicesAliasesRequest request = new IndicesAliasesRequest().addAliasAction(new
                IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD).alias(ALIAS).indices(INDEX));
        AcknowledgedResponse acknowledgedResponse = localIndicesClient.updateAliases(request, RequestOptions.DEFAULT);
        log.info("成功:{}", acknowledgedResponse);
    }

    /**
     * 删除别名
     */
    @Test
    public void deleteAlias() throws IOException {
        DeleteAliasRequest request = new DeleteAliasRequest(INDEX, ALIAS);
        org.elasticsearch.client.core.AcknowledgedResponse acknowledgedResponse
                = localIndicesClient.deleteAlias(request, RequestOptions.DEFAULT);
        log.info("成功:{}", acknowledgedResponse);
    }


    /**
     * 获取别名 ???
     */
    @Test
    public void getAlias() throws IOException {
        GetAliasesRequest request = new GetAliasesRequest(INDEX);
        GetAliasesResponse alias = localIndicesClient.getAlias(request, RequestOptions.DEFAULT);
        log.info("成功:{}", alias);
    }

    /**
     * 判断别名是否存在 ???
     */
    @Test
    public void existsAlias() throws IOException {
        GetAliasesRequest request = new GetAliasesRequest(INDEX).aliases(ALIAS);
        boolean b = localIndicesClient.existsAlias(request, RequestOptions.DEFAULT);
        log.info("成功:{}", b);
    }


    /**
     * ???
     */
    @Test
    public void getFieldMapping() throws IOException {
        GetFieldMappingsRequest request = new GetFieldMappingsRequest().indices(INDEX);
        GetFieldMappingsResponse fieldMapping = localIndicesClient.getFieldMapping(request, RequestOptions.DEFAULT);
        log.info("成功:{}", fieldMapping);
    }


}













