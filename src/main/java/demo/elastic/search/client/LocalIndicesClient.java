/*
 * Licensed to Elasticsearch under one or more contributor
 * license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright
 * ownership. Elasticsearch licenses this file to you under
 * the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package demo.elastic.search.client;

import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheRequest;
import org.elasticsearch.action.admin.indices.cache.clear.ClearIndicesCacheResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.flush.FlushRequest;
import org.elasticsearch.action.admin.indices.flush.FlushResponse;
import org.elasticsearch.action.admin.indices.flush.SyncedFlushRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeRequest;
import org.elasticsearch.action.admin.indices.forcemerge.ForceMergeResponse;
import org.elasticsearch.action.admin.indices.open.OpenIndexRequest;
import org.elasticsearch.action.admin.indices.open.OpenIndexResponse;
import org.elasticsearch.action.admin.indices.refresh.RefreshRequest;
import org.elasticsearch.action.admin.indices.refresh.RefreshResponse;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.admin.indices.template.delete.DeleteIndexTemplateRequest;
import org.elasticsearch.action.admin.indices.validate.query.ValidateQueryRequest;
import org.elasticsearch.action.admin.indices.validate.query.ValidateQueryResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.core.ShardsAcknowledgedResponse;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.client.indices.CloseIndexRequest;
import org.elasticsearch.client.indices.CloseIndexResponse;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.DeleteAliasRequest;
import org.elasticsearch.client.indices.FreezeIndexRequest;
import org.elasticsearch.client.indices.GetFieldMappingsRequest;
import org.elasticsearch.client.indices.GetFieldMappingsResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.client.indices.GetIndexResponse;
import org.elasticsearch.client.indices.GetIndexTemplatesRequest;
import org.elasticsearch.client.indices.GetIndexTemplatesResponse;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.elasticsearch.client.indices.GetMappingsResponse;
import org.elasticsearch.client.indices.IndexTemplatesExistRequest;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.client.indices.PutMappingRequest;
import org.elasticsearch.client.indices.ReloadAnalyzersRequest;
import org.elasticsearch.client.indices.ReloadAnalyzersResponse;
import org.elasticsearch.client.indices.ResizeRequest;
import org.elasticsearch.client.indices.ResizeResponse;
import org.elasticsearch.client.indices.UnfreezeIndexRequest;
import org.elasticsearch.client.indices.rollover.RolloverRequest;
import org.elasticsearch.client.indices.rollover.RolloverResponse;


import java.io.IOException;

/**
 * A wrapper for the {@link RestHighLevelClient} that provides methods for accessing the Indices API.
 * <p>
 * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices.html">Indices API on elastic.co</a>
 */
public final class LocalIndicesClient {
    private final IndicesClient indicesClient;


    public LocalIndicesClient(IndicesClient indicesClient) {
        this.indicesClient = indicesClient;//获取index的客户端
    }

    /**
     * Opens an index using the Open Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html">
     * Open Index API on elastic.co</a>
     * ----------------------------------------
     * 打开索引
     * ----------------------------------------
     *
     * @param openIndexRequest the request
     * @param options          the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public OpenIndexResponse open(OpenIndexRequest openIndexRequest, RequestOptions options) throws IOException {
        return indicesClient.open(openIndexRequest, options);
    }

    /**
     * Closes an index using the Close Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html">
     * Close Index API on elastic.co</a>
     * ----------------------------------------
     * 关闭索引
     * ----------------------------------------
     *
     * @param closeIndexRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public CloseIndexResponse close(CloseIndexRequest closeIndexRequest, RequestOptions options) throws IOException {
        return indicesClient.close(closeIndexRequest, options);
    }


    /**
     * Creates an index using the Create Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-create-index.html">
     * Create Index API on elastic.co</a>
     * ----------------------------------------
     * 创建一个index
     * ----------------------------------------
     *
     * @param createIndexRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public CreateIndexResponse create(CreateIndexRequest createIndexRequest,
                                      RequestOptions options) throws IOException {
        return indicesClient.create(createIndexRequest, options);
    }

    /**
     * Deletes an index using the Delete Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-delete-index.html">
     * Delete Index API on elastic.co</a>
     * ----------------------------------------
     * 删除一个index,使用删除API
     * ----------------------------------------
     *
     * @param deleteIndexRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public AcknowledgedResponse delete(DeleteIndexRequest deleteIndexRequest, RequestOptions options) throws IOException {
        return indicesClient.delete(deleteIndexRequest, options);
    }


    /**
     * Updates the mappings on an index using the Put Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html">
     * Put Mapping API on elastic.co</a>
     * ----------------------------------------
     * 更新一个index的mapping
     * ----------------------------------------
     *
     * @param putMappingRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public AcknowledgedResponse putMapping(PutMappingRequest putMappingRequest, RequestOptions options) throws IOException {
        return indicesClient.putMapping(putMappingRequest, options);
    }


    /**
     * Retrieves the mappings on an index or indices using the Get Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-mapping.html">
     * Get Mapping API on elastic.co</a>
     * ----------------------------------------
     * 获取Index的Mapping
     * ----------------------------------------
     *
     * @param getMappingsRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public GetMappingsResponse getMapping(GetMappingsRequest getMappingsRequest, RequestOptions options) throws IOException {
        return indicesClient.getMapping(getMappingsRequest, options);
    }

    /**
     * Updates aliases using the Index Aliases API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html">
     * Index Aliases API on elastic.co</a>
     * ----------------------------------------
     * 更新Index的别名
     * ----------------------------------------
     *
     * @param indicesAliasesRequest the request
     * @param options               the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public AcknowledgedResponse updateAliases(IndicesAliasesRequest indicesAliasesRequest, RequestOptions options) throws IOException {
        return indicesClient.updateAliases(indicesAliasesRequest, options);
    }


    /**
     * Checks if one or more aliases exist using the Aliases Exist API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html">
     * Indices Aliases API on elastic.co</a>
     * ----------------------------------------
     * 检查别名是否存在
     * ----------------------------------------
     *
     * @param getAliasesRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request
     */
    public boolean existsAlias(GetAliasesRequest getAliasesRequest, RequestOptions options) throws IOException {
        return indicesClient.existsAlias(getAliasesRequest, options);
    }

    /**
     * Gets one or more aliases using the Get Index Aliases API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html"> Indices Aliases API on
     * elastic.co</a>
     * ----------------------------------------
     * 获取一个或者多个别名
     * ----------------------------------------
     *
     * @param getAliasesRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public GetAliasesResponse getAlias(GetAliasesRequest getAliasesRequest, RequestOptions options) throws IOException {
        return indicesClient.getAlias(getAliasesRequest, options);
    }

    /**
     * Synchronously calls the delete alias api
     * ----------------------------------------
     * 同步调用删除API
     * ----------------------------------------
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     */
    public org.elasticsearch.client.core.AcknowledgedResponse deleteAlias(DeleteAliasRequest request,
                                                                          RequestOptions options) throws IOException {
        return indicesClient.deleteAlias(request, options);
    }

    /**
     * Retrieves the field mappings on an index or indices using the Get Field Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html">
     * Get Field Mapping API on elastic.co</a>
     * ----------------------------------------
     * 获取index的字段Mapping
     * ----------------------------------------
     *
     * @param getFieldMappingsRequest the request
     * @param options                 the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public GetFieldMappingsResponse getFieldMapping(GetFieldMappingsRequest getFieldMappingsRequest,
                                                    RequestOptions options) throws IOException {
        return indicesClient.getFieldMapping(getFieldMappingsRequest, options);
    }


    /**
     * Refresh one or more indices using the Refresh API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html"> Refresh API on elastic.co</a>
     * ----------------------------------------
     * 刷新一个或者多个 indices
     * ----------------------------------------
     *
     * @param refreshRequest the request
     * @param options        the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public RefreshResponse refresh(RefreshRequest refreshRequest, RequestOptions options) throws IOException {
        return indicesClient.refresh(refreshRequest, options);
    }


    /**
     * Flush one or more indices using the Flush API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html"> Flush API on elastic.co</a>
     * ----------------------------------------
     * 刷新（Flush）一个或者多个 indices
     * ----------------------------------------
     *
     * @param flushRequest the request
     * @param options      the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public FlushResponse flush(FlushRequest flushRequest, RequestOptions options) throws IOException {
        return indicesClient.flush(flushRequest, options);
    }


    /**
     * Retrieve the settings of one or more indices.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html">
     * Indices Get Settings API on elastic.co</a>
     * <p>
     * ----------------------------------------
     * 获取一个或者多个 indices 的settings
     * ----------------------------------------
     *
     * @param getSettingsRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public GetSettingsResponse getSettings(GetSettingsRequest getSettingsRequest, RequestOptions options) throws IOException {
        return indicesClient.getSettings(getSettingsRequest, options);
    }


    /**
     * Retrieve information about one or more indexes
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-index.html">
     * Indices Get Index API on elastic.co</a>
     * ----------------------------------------
     * 获取一个或者多个 indices的 information
     * ----------------------------------------
     *
     * @param getIndexRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public GetIndexResponse get(GetIndexRequest getIndexRequest, RequestOptions options) throws IOException {
        return indicesClient.get(getIndexRequest, options);
    }


    /**
     * Force merge one or more indices using the Force Merge API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html">
     * Force Merge API on elastic.co</a>
     * ----------------------------------------
     * 强制合并一个或者多个indices
     * ----------------------------------------
     *
     * @param forceMergeRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public ForceMergeResponse forcemerge(ForceMergeRequest forceMergeRequest, RequestOptions options) throws IOException {
        return indicesClient.forcemerge(forceMergeRequest, options);
    }


    /**
     * Clears the cache of one or more indices using the Clear Cache API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html">
     * Clear Cache API on elastic.co</a>
     * ----------------------------------------
     * 清除一个或者多个indices的缓存
     * ----------------------------------------
     *
     * @param clearIndicesCacheRequest the request
     * @param options                  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public ClearIndicesCacheResponse clearCache(ClearIndicesCacheRequest clearIndicesCacheRequest,
                                                RequestOptions options) throws IOException {
        return indicesClient.clearCache(clearIndicesCacheRequest, options);
    }


    /**
     * Checks if the index (indices) exists or not.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-exists.html">
     * Indices Exists API on elastic.co</a>
     * ----------------------------------------
     * 检查一个或者多个indices的是否存在
     * ----------------------------------------
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request
     */
    public boolean exists(GetIndexRequest request, RequestOptions options) throws IOException {
        return indicesClient.exists(request, options);
    }


    /**
     * Shrinks an index using the Shrink Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shrink-index.html">
     * Shrink Index API on elastic.co</a>
     * ----------------------------------------
     * 收缩一个Index
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public ResizeResponse shrink(ResizeRequest resizeRequest, RequestOptions options) throws IOException {
        return indicesClient.shrink(resizeRequest, options);
    }


    /**
     * Splits an index using the Split Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-split-index.html">
     * Split Index API on elastic.co</a>
     * ----------------------------------------
     * 拆分index
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public ResizeResponse split(ResizeRequest resizeRequest, RequestOptions options) throws IOException {
        return indicesClient.split(resizeRequest, options);
    }


    /**
     * Clones an index using the Clone Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clone-index.html">
     * Clone Index API on elastic.co</a>
     * ----------------------------------------
     * 克隆一个index
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public ResizeResponse clone(ResizeRequest resizeRequest, RequestOptions options) throws IOException {
        return indicesClient.clone(resizeRequest, options);
    }


    /**
     * Rolls over an index using the Rollover Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-rollover-index.html">
     * Rollover Index API on elastic.co</a>
     * ----------------------------------------
     * 滚动一个index
     * ----------------------------------------
     *
     * @param rolloverRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public RolloverResponse rollover(RolloverRequest rolloverRequest, RequestOptions options) throws IOException {
        return indicesClient.rollover(rolloverRequest, options);
    }


    /**
     * Updates specific index level settings using the Update Indices Settings API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html"> Update Indices Settings
     * API on elastic.co</a>
     * ----------------------------------------
     * 更新指定index的level
     * ----------------------------------------
     *
     * @param updateSettingsRequest the request
     * @param options               the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public AcknowledgedResponse putSettings(UpdateSettingsRequest updateSettingsRequest, RequestOptions options) throws IOException {
        return indicesClient.putSettings(updateSettingsRequest, options);
    }


    /**
     * Puts an index template using the Index Templates API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 设置index的template
     * ----------------------------------------
     *
     * @param putIndexTemplateRequest the request
     * @param options                 the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public AcknowledgedResponse putTemplate(
            PutIndexTemplateRequest putIndexTemplateRequest,
            RequestOptions options) throws IOException {
        return indicesClient.putTemplate(putIndexTemplateRequest, options);
    }


    /**
     * Validate a potentially expensive query without executing it.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html"> Validate Query API
     * on elastic.co</a>
     * ----------------------------------------
     * 在不执行查询的情况下验证可能代价昂贵的查询
     * ----------------------------------------
     *
     * @param validateQueryRequest the request
     * @param options              the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public ValidateQueryResponse validateQuery(ValidateQueryRequest validateQueryRequest,
                                               RequestOptions options) throws IOException {
        return indicesClient.validateQuery(validateQueryRequest, options);
    }


    /**
     * Gets index templates using the Index Templates API
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 获取index的templates
     * ----------------------------------------
     *
     * @param options                  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param getIndexTemplatesRequest the request
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public GetIndexTemplatesResponse getIndexTemplate(GetIndexTemplatesRequest getIndexTemplatesRequest,
                                                      RequestOptions options) throws IOException {
        return indicesClient.getIndexTemplate(getIndexTemplatesRequest, options);
    }


    /**
     * Uses the Index Templates API to determine if index templates exist
     * ----------------------------------------
     * 确定templates是否存在
     * ----------------------------------------
     *
     * @param indexTemplatesRequest the request
     * @param options               the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return true if any index templates in the request exist, false otherwise
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public boolean existsTemplate(IndexTemplatesExistRequest indexTemplatesRequest,
                                  RequestOptions options) throws IOException {
        return indicesClient.existsTemplate(indexTemplatesRequest, options);
    }


    /**
     * Calls the analyze API
     * ----------------------------------------
     * 调用分析API
     * ----------------------------------------
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-analyze.html">Analyze API on elastic.co</a>
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     */
    public AnalyzeResponse analyze(AnalyzeRequest request, RequestOptions options) throws IOException {
        return indicesClient.analyze(request, options);
    }


    /**
     * Synchronously calls the _freeze API
     * ----------------------------------------
     * 冻结索引
     * ----------------------------------------
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/freeze-index-api.html"> </a>
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     */
    public ShardsAcknowledgedResponse freeze(FreezeIndexRequest request, RequestOptions options) throws IOException {
        return indicesClient.freeze(request, options);
    }


    /**
     * Synchronously calls the _unfreeze API
     * ----------------------------------------
     * 解除冻结索引
     * ----------------------------------------
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     */
    public ShardsAcknowledgedResponse unfreeze(UnfreezeIndexRequest request, RequestOptions options) throws IOException {
        return indicesClient.unfreeze(request, options);
    }


    /**
     * Delete an index template using the Index Templates API
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 删除 index 的 template
     * ----------------------------------------
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @throws IOException in case there is a problem sending the request or parsing back the response
     */
    public AcknowledgedResponse deleteTemplate(DeleteIndexTemplateRequest request, RequestOptions options) throws IOException {
        return indicesClient.deleteTemplate(request, options);
    }

    /**
     * Synchronously calls the _reload_search_analyzers API
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     */
    public ReloadAnalyzersResponse reloadAnalyzers(ReloadAnalyzersRequest request, RequestOptions options) throws IOException {
        return indicesClient.reloadAnalyzers(request, options);
    }


    /**
     * Asynchronously calls the delete alias api
     * ----------------------------------------
     * 异步调用删除API
     * ----------------------------------------
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable deleteAliasAsync(DeleteAliasRequest request, RequestOptions options,
                                        ActionListener<org.elasticsearch.client.core.AcknowledgedResponse> listener) {
        return indicesClient.deleteAliasAsync(request, options, listener);
    }

    /**
     * Asynchronously deletes an index using the Delete Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-delete-index.html">
     * Delete Index API on elastic.co</a>
     * ----------------------------------------
     * 异步删除一个index
     * ----------------------------------------
     *
     * @param deleteIndexRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener           the listener to be notified upon request completion 当请求完成时的监听器
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable deleteAsync(DeleteIndexRequest deleteIndexRequest, RequestOptions options,
                                   ActionListener<AcknowledgedResponse> listener) {
        return indicesClient.deleteAsync(deleteIndexRequest, options, listener);
    }

    /**
     * Asynchronously creates an index using the Create Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-create-index.html">
     * Create Index API on elastic.co</a>
     * ----------------------------------------
     * 异步创建一个index
     * ----------------------------------------
     *
     * @param createIndexRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener           the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable createAsync(CreateIndexRequest createIndexRequest,
                                   RequestOptions options,
                                   ActionListener<CreateIndexResponse> listener) {
        return indicesClient.createAsync(createIndexRequest, options, listener);
    }

    /**
     * Asynchronously updates the mappings on an index using the Put Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html">
     * Put Mapping API on elastic.co</a>
     * ----------------------------------------
     * 异步更新一个index的mapping
     * ----------------------------------------
     *
     * @param putMappingRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable putMappingAsync(PutMappingRequest putMappingRequest, RequestOptions options,
                                       ActionListener<AcknowledgedResponse> listener) {
        return indicesClient.putMappingAsync(putMappingRequest, options, listener);
    }

    /**
     * Asynchronously updates specific index level settings using the Update Indices Settings API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-update-settings.html"> Update Indices Settings
     * API on elastic.co</a>
     * ----------------------------------------
     * 异步更新指定index的level
     * ----------------------------------------
     *
     * @param updateSettingsRequest the request
     * @param options               the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener              the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable putSettingsAsync(UpdateSettingsRequest updateSettingsRequest, RequestOptions options,
                                        ActionListener<AcknowledgedResponse> listener) {
        return indicesClient.putSettingsAsync(updateSettingsRequest, options, listener);
    }

    /**
     * Uses the Index Templates API to determine if index templates exist
     * ----------------------------------------
     * 异步确定templates是否存在
     * ----------------------------------------
     *
     * @param indexTemplatesExistRequest the request
     * @param options                    the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                   the listener to be notified upon request completion. The listener will be called with the value {@code true}
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable existsTemplateAsync(IndexTemplatesExistRequest indexTemplatesExistRequest,
                                           RequestOptions options,
                                           ActionListener<Boolean> listener) {

        return indicesClient.existsTemplateAsync(indexTemplatesExistRequest, options, listener);
    }

    /**
     * Asynchronously retrieves the mappings on an index on indices using the Get Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-mapping.html">
     * Get Mapping API on elastic.co</a>
     * ----------------------------------------
     * 异步获取Index的Mapping
     * ----------------------------------------
     *
     * @param getMappingsRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener           the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable getMappingAsync(GetMappingsRequest getMappingsRequest, RequestOptions options,
                                       ActionListener<GetMappingsResponse> listener) {
        return indicesClient.getMappingAsync(getMappingsRequest, options, listener);
    }

    /**
     * Asynchronously updates aliases using the Index Aliases API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html">
     * Index Aliases API on elastic.co</a>
     * ----------------------------------------
     * 异步更新Index的别名
     * ----------------------------------------
     *
     * @param indicesAliasesRequest the request
     * @param options               the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener              the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable updateAliasesAsync(IndicesAliasesRequest indicesAliasesRequest, RequestOptions options,
                                          ActionListener<AcknowledgedResponse> listener) {
        return indicesClient.updateAliasesAsync(indicesAliasesRequest, options, listener);
    }

    /**
     * Asynchronously retrieves the field mappings on an index or indices using the Get Field Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html">
     * Get Field Mapping API on elastic.co</a>
     * ----------------------------------------
     * 异步获取index的字段Mapping
     * ----------------------------------------
     *
     * @param getFieldMappingsRequest the request
     * @param options                 the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable getFieldMappingAsync(GetFieldMappingsRequest getFieldMappingsRequest,
                                            RequestOptions options, ActionListener<GetFieldMappingsResponse> listener) {
        return indicesClient.getFieldMappingAsync(getFieldMappingsRequest, options, listener);
    }

    /**
     * Asynchronously opens an index using the Open Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html">
     * Open Index API on elastic.co</a>
     * ----------------------------------------
     * 异步打开索引
     * ----------------------------------------
     *
     * @param openIndexRequest the request
     * @param options          the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener         the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable openAsync(OpenIndexRequest openIndexRequest, RequestOptions options, ActionListener<OpenIndexResponse> listener) {
        return indicesClient.openAsync(openIndexRequest, options, listener);
    }

    /**
     * Asynchronously calls the analyze API
     * ----------------------------------------
     * 异步调用分析API
     * ----------------------------------------
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-analyze.html">Analyze API on elastic.co</a>
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable analyzeAsync(AnalyzeRequest request, RequestOptions options,
                                    ActionListener<AnalyzeResponse> listener) {
        return indicesClient.analyzeAsync(request, options, listener);
    }

    /**
     * Asynchronously gets index templates using the Index Templates API
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 异步获取index的templates
     * ----------------------------------------
     *
     * @param getIndexTemplatesRequest the request
     * @param options                  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                 the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable getIndexTemplateAsync(GetIndexTemplatesRequest getIndexTemplatesRequest, RequestOptions options,
                                             ActionListener<GetIndexTemplatesResponse> listener) {
        return indicesClient.getIndexTemplateAsync(getIndexTemplatesRequest, options, listener);
    }

    /**
     * Asynchronously closes an index using the Close Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-open-close.html">
     * Close Index API on elastic.co</a>
     * ----------------------------------------
     * 异步关闭索引
     * ----------------------------------------
     *
     * @param closeIndexRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable closeAsync(CloseIndexRequest closeIndexRequest, RequestOptions options,
                                  ActionListener<CloseIndexResponse> listener) {
        return indicesClient.closeAsync(closeIndexRequest, options, listener);
    }

    /**
     * Asynchronously checks if one or more aliases exist using the Aliases Exist API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html">
     * Indices Aliases API on elastic.co</a>
     * ----------------------------------------
     * 异步检查别名是否存在
     * ----------------------------------------
     *
     * @param getAliasesRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable existsAliasAsync(GetAliasesRequest getAliasesRequest, RequestOptions options, ActionListener<Boolean> listener) {
        return indicesClient.existsAliasAsync(getAliasesRequest, options, listener);
    }

    /**
     * Asynchronously refresh one or more indices using the Refresh API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-refresh.html"> Refresh API on elastic.co</a>
     * ----------------------------------------
     * 异步刷新一个或者多个 indices
     * ----------------------------------------
     *
     * @param refreshRequest the request
     * @param options        the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener       the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable refreshAsync(RefreshRequest refreshRequest, RequestOptions options, ActionListener<RefreshResponse> listener) {
        return indicesClient.refreshAsync(refreshRequest, options, listener);
    }

    /**
     * Asynchronously calls the _unfreeze API
     * ----------------------------------------
     * 异步解除冻结索引
     * ----------------------------------------
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable unfreezeAsync(UnfreezeIndexRequest request, RequestOptions options,
                                     ActionListener<ShardsAcknowledgedResponse> listener) {
        return indicesClient.unfreezeAsync(request, options, listener);
    }

    /**
     * Asynchronously calls the _reload_search_analyzers API
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable reloadAnalyzersAsync(ReloadAnalyzersRequest request, RequestOptions options,
                                            ActionListener<ReloadAnalyzersResponse> listener) {
        return indicesClient.reloadAnalyzersAsync(request, options, listener);
    }

    /**
     * Asynchronously flush one or more indices using the Flush API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-flush.html"> Flush API on elastic.co</a>
     * ----------------------------------------
     * 异步刷新（Flush）一个或者多个 indices
     * ----------------------------------------
     *
     * @param flushRequest the request
     * @param options      the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener     the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable flushAsync(FlushRequest flushRequest, RequestOptions options, ActionListener<FlushResponse> listener) {
        return indicesClient.flushAsync(flushRequest, options, listener);
    }

    /**
     * Asynchronously delete an index template using the Index Templates API
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 异步删除 index 的 template
     * ----------------------------------------
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable deleteTemplateAsync(DeleteIndexTemplateRequest request, RequestOptions options,
                                           ActionListener<AcknowledgedResponse> listener) {
        return indicesClient.deleteTemplateAsync(request, options, listener);
    }

    /**
     * Asynchronously retrieve the settings of one or more indices.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-settings.html">
     * Indices Get Settings API on elastic.co</a>
     * ----------------------------------------
     * 异步获取一个或者多个 indices 的settings
     * ----------------------------------------
     *
     * @param getSettingsRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener           the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable getSettingsAsync(GetSettingsRequest getSettingsRequest, RequestOptions options,
                                        ActionListener<GetSettingsResponse> listener) {
        return indicesClient.getSettingsAsync(getSettingsRequest, options, listener);
    }

    /**
     * Retrieve information about one or more indexes
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-index.html">
     * Indices Get Index API on elastic.co</a>
     * ----------------------------------------
     * 异步获取一个或者多个 indices的 information
     * ----------------------------------------
     *
     * @param getIndexRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener        the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable getAsync(GetIndexRequest getIndexRequest, RequestOptions options,
                                ActionListener<GetIndexResponse> listener) {
        return indicesClient.getAsync(getIndexRequest, options, listener);
    }

    /**
     * Asynchronously force merge one or more indices using the Force Merge API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html">
     * Force Merge API on elastic.co</a>
     * ----------------------------------------
     * 异步强制合并一个或者多个indices
     * ----------------------------------------
     *
     * @param forceMergeRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable forcemergeAsync(ForceMergeRequest forceMergeRequest, RequestOptions options,
                                       ActionListener<ForceMergeResponse> listener) {
        return indicesClient.forcemergeAsync(forceMergeRequest, options, listener);
    }

    /**
     * Asynchronously clears the cache of one or more indices using the Clear Cache API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clearcache.html">
     * Clear Cache API on elastic.co</a>
     * ----------------------------------------
     * 异步清除一个或者多个indices的缓存
     * ----------------------------------------
     *
     * @param clearIndicesCacheRequest the request
     * @param options                  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                 the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable clearCacheAsync(ClearIndicesCacheRequest clearIndicesCacheRequest, RequestOptions options,
                                       ActionListener<ClearIndicesCacheResponse> listener) {
        return indicesClient.clearCacheAsync(clearIndicesCacheRequest, options, listener);
    }

    /**
     * Asynchronously shrinks an index using the Shrink index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shrink-index.html">
     * Shrink Index API on elastic.co</a>
     * ----------------------------------------
     * 异步收缩一个Index
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable shrinkAsync(ResizeRequest resizeRequest, RequestOptions options, ActionListener<ResizeResponse> listener) {
        return indicesClient.shrinkAsync(resizeRequest, options, listener);
    }


    /**
     * Asynchronously splits an index using the Split Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-split-index.html">
     * Split Index API on elastic.co</a>
     * ----------------------------------------
     * 异步拆分index
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable splitAsync(ResizeRequest resizeRequest, RequestOptions options, ActionListener<ResizeResponse> listener) {
        return indicesClient.splitAsync(resizeRequest, options, listener);
    }

    /**
     * Asynchronously clones an index using the Clone Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clone-index.html">
     * Clone Index API on elastic.co</a>
     * ----------------------------------------
     * 异步克隆一个index
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable cloneAsync(ResizeRequest resizeRequest, RequestOptions options, ActionListener<ResizeResponse> listener) {
        return indicesClient.cloneAsync(resizeRequest, options, listener);
    }

    /**
     * Asynchronously puts an index template using the Index Templates API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 异步设置index的template
     * ----------------------------------------
     *
     * @param putIndexTemplateRequest the request
     * @param options                 the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable putTemplateAsync(PutIndexTemplateRequest putIndexTemplateRequest,
                                        RequestOptions options, ActionListener<AcknowledgedResponse> listener) {
        return indicesClient.putTemplateAsync(putIndexTemplateRequest, options, listener);
    }

    /**
     * Asynchronously checks if the index (indices) exists or not.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-exists.html">
     * Indices Exists API on elastic.co</a>
     * ----------------------------------------
     * 异步检查一个或者多个indices的是否存在
     * ----------------------------------------
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable existsAsync(GetIndexRequest request, RequestOptions options, ActionListener<Boolean> listener) {
        return indicesClient.existsAsync(request, options, listener);
    }

    /**
     * Asynchronously rolls over an index using the Rollover Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-rollover-index.html">
     * Rollover Index API on elastic.co</a>
     * ----------------------------------------
     * 异步滚动一个index
     * ----------------------------------------
     *
     * @param rolloverRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener        the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable rolloverAsync(RolloverRequest rolloverRequest, RequestOptions options, ActionListener<RolloverResponse> listener) {
        return indicesClient.rolloverAsync(rolloverRequest, options, listener);
    }

    /**
     * Asynchronously gets one or more aliases using the Get Index Aliases API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-aliases.html"> Indices Aliases API on
     * elastic.co</a>
     * ----------------------------------------
     * 异步获取一个或者多个别名
     * ----------------------------------------
     *
     * @param getAliasesRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable getAliasAsync(GetAliasesRequest getAliasesRequest, RequestOptions options,
                                     ActionListener<GetAliasesResponse> listener) {
        return indicesClient.getAliasAsync(getAliasesRequest, options, listener);
    }

    /**
     * Asynchronously validate a potentially expensive query without executing it.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-validate.html"> Validate Query API
     * on elastic.co</a>
     * ----------------------------------------
     * 异步在不执行查询的情况下验证可能代价昂贵的查询
     * ----------------------------------------
     *
     * @param validateQueryRequest the request
     * @param options              the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener             the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable validateQueryAsync(ValidateQueryRequest validateQueryRequest, RequestOptions options,
                                          ActionListener<ValidateQueryResponse> listener) {
        return indicesClient.validateQueryAsync(validateQueryRequest, options, listener);
    }

    /**
     * Asynchronously calls the _freeze API
     * ----------------------------------------
     * 异步冻结索引
     * ----------------------------------------
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable freezeAsync(FreezeIndexRequest request, RequestOptions options,
                                   ActionListener<ShardsAcknowledgedResponse> listener) {
        return indicesClient.freezeAsync(request, options, listener);
    }


    /**
     * Creates an index using the Create Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-create-index.html">
     * Create Index API on elastic.co</a>
     * ----------------------------------------
     * 创建一个index
     * ----------------------------------------
     *
     * @param createIndexRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated This method uses an old request object which still refers to types, a deprecated feature. The
     * method {@link #create(CreateIndexRequest, RequestOptions)} should be used instead, which accepts a new
     * request object.
     * <p>
     * ----------------------------------------
     * 弃用:这个是老的请求对象(仍然指向类型,不推荐)
     * ----------------------------------------
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.create.CreateIndexResponse create(
            org.elasticsearch.action.admin.indices.create.CreateIndexRequest createIndexRequest,
            RequestOptions options) throws IOException {
        return indicesClient.create(createIndexRequest, options);
    }


    /**
     * Asynchronously creates an index using the Create Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-create-index.html">
     * Create Index API on elastic.co</a>
     * ----------------------------------------
     * 异步创建一个index
     * 参考 {@link #create(org.elasticsearch.action.admin.indices.create.CreateIndexRequest, RequestOptions)}
     * ----------------------------------------
     *
     * @param createIndexRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener           the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated This method uses an old request object which still refers to types, a deprecated feature. The
     * method {@link #createAsync(CreateIndexRequest, RequestOptions, ActionListener)} should be used instead,
     * which accepts a new request object.
     */
    @Deprecated
    public Cancellable createAsync(org.elasticsearch.action.admin.indices.create.CreateIndexRequest createIndexRequest,
                                   RequestOptions options,
                                   ActionListener<org.elasticsearch.action.admin.indices.create.CreateIndexResponse> listener) {
        return indicesClient.createAsync(createIndexRequest, options, listener);
    }

    /**
     * Updates the mappings on an index using the Put Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html">
     * Put Mapping API on elastic.co</a>
     * ----------------------------------------
     * 更新一个index的mapping
     * 已经弃用
     * ----------------------------------------
     *
     * @param putMappingRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated This method uses an old request object which still refers to types, a deprecated feature. The method
     * {@link #putMapping(PutMappingRequest, RequestOptions)} should be used instead, which accepts a new request object.
     * ----------------------------------------
     * 弃用:这个是老的请求对象(仍然指向类型,不推荐)
     * ----------------------------------------
     */
    @Deprecated
    public AcknowledgedResponse putMapping(org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest putMappingRequest,
                                           RequestOptions options) throws IOException {
        return indicesClient.putMapping(putMappingRequest, options);
    }


    /**
     * Asynchronously updates the mappings on an index using the Put Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-put-mapping.html">
     * Put Mapping API on elastic.co</a>
     * ----------------------------------------
     * 异步更新一个index的mapping
     * 已经弃用
     * ----------------------------------------
     *
     * @param putMappingRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     *                          当请求完成时会调用监听器
     * @return cancellable that may be used to cancel the request
     * @deprecated This method uses an old request object which still refers to types, a deprecated feature. The
     * method {@link #putMappingAsync(PutMappingRequest, RequestOptions, ActionListener)} should be used instead,
     * which accepts a new request object
     * ----------------------------------------
     * 弃用:这个是老的请求对象(仍然指向类型,不推荐)
     * ----------------------------------------
     */
    @Deprecated
    public Cancellable putMappingAsync(org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest putMappingRequest,
                                       RequestOptions options,
                                       ActionListener<AcknowledgedResponse> listener) {
        return indicesClient.putMappingAsync(putMappingRequest, options, listener);
    }

    /**
     * Retrieves the mappings on an index or indices using the Get Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-mapping.html">
     * Get Mapping API on elastic.co</a>
     * <p>
     * ----------------------------------------
     * 获取Index的Mapping（已经弃用）
     * ----------------------------------------
     *
     * @param getMappingsRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated This method uses old request and response objects which still refer to types, a deprecated
     * feature. The method {@link #getMapping(GetMappingsRequest, RequestOptions)} should be used instead, which
     * accepts a new request object.
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse getMapping(
            org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest getMappingsRequest,
            RequestOptions options) throws IOException {
        return indicesClient.getMapping(getMappingsRequest, options);
    }

    /**
     * Asynchronously retrieves the mappings on an index on indices using the Get Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-mapping.html">
     * Get Mapping API on elastic.co</a>
     * ----------------------------------------
     * 异步获取Index的Mapping（已经弃用）
     * ----------------------------------------
     *
     * @param getMappingsRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener           the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated This method uses old request and response objects which still refer to types, a deprecated feature.
     * The method {@link #getMapping(GetMappingsRequest, RequestOptions)} should be used instead, which accepts a new
     * request object.
     */
    @Deprecated
    public Cancellable getMappingAsync(org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest getMappingsRequest,
                                       RequestOptions options,
                                       ActionListener<org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse> listener) {
        return indicesClient.getMappingAsync(getMappingsRequest, options, listener);
    }

    /**
     * Retrieves the field mappings on an index or indices using the Get Field Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html">
     * Get Field Mapping API on elastic.co</a>
     * ----------------------------------------
     * 获取index的字段Mapping（已经弃用）
     * ----------------------------------------
     *
     * @param getFieldMappingsRequest the request
     * @param options                 the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated This method uses old request and response objects which still refer to types, a deprecated feature.
     * The method {@link #getFieldMapping(GetFieldMappingsRequest, RequestOptions)} should be used instead, which
     * accepts a new request object.
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse getFieldMapping(
            org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsRequest getFieldMappingsRequest,
            RequestOptions options) throws IOException {
        return indicesClient.getFieldMapping(getFieldMappingsRequest, options);
    }

    /**
     * Asynchronously retrieves the field mappings on an index on indices using the Get Field Mapping API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-field-mapping.html">
     * Get Field Mapping API on elastic.co</a>
     * ----------------------------------------
     * 异步获取index的字段Mapping（已经弃用）
     * ----------------------------------------
     *
     * @param getFieldMappingsRequest the request
     * @param options                 the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated This method uses old request and response objects which still refer to types, a deprecated feature.
     * The method {@link #getFieldMappingAsync(GetFieldMappingsRequest, RequestOptions, ActionListener)} should be
     * used instead, which accepts a new request object.
     */
    @Deprecated
    public Cancellable getFieldMappingAsync(
            org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsRequest getFieldMappingsRequest,
            RequestOptions options,
            ActionListener<org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse> listener) {
        return indicesClient.getFieldMappingAsync(getFieldMappingsRequest, options, listener);
    }

    /**
     * Initiate a synced flush manually using the synced flush API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/master/indices-synced-flush-api.html">
     * Synced flush API on elastic.co</a>
     * ----------------------------------------
     * 同步刷新API（已经弃用 ->{@link #flush(FlushRequest, RequestOptions)} 替代）
     * ----------------------------------------
     *
     * @param syncedFlushRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated synced flush is deprecated and will be removed in 8.0.
     * Use {@link #flush(FlushRequest, RequestOptions)} instead.
     */
    @Deprecated
    public SyncedFlushResponse flushSynced(SyncedFlushRequest syncedFlushRequest, RequestOptions options) throws IOException {
        return indicesClient.flushSynced(syncedFlushRequest, options);
    }

    /**
     * Asynchronously initiate a synced flush manually using the synced flush API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/master/indices-synced-flush-api.html">
     * Synced flush API on elastic.co</a>
     * ----------------------------------------
     * 异步刷新API（已经弃用 ->{@link #flushAsync(FlushRequest, RequestOptions, ActionListener)} 替代）
     * ----------------------------------------
     *
     * @param syncedFlushRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener           the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated synced flush is deprecated and will be removed in 8.0.
     * Use {@link #flushAsync(FlushRequest, RequestOptions, ActionListener)} instead.
     */
    @Deprecated
    public Cancellable flushSyncedAsync(SyncedFlushRequest syncedFlushRequest, RequestOptions options,
                                        ActionListener<SyncedFlushResponse> listener) {
        return indicesClient.flushSyncedAsync(syncedFlushRequest, options, listener);
    }

    /**
     * Retrieve information about one or more indexes
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-index.html">
     * Indices Get Index API on elastic.co</a>
     * ----------------------------------------
     * 获取一个或者多个 indices的 information （已经弃用）
     * ----------------------------------------
     *
     * @param getIndexRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated This method uses an old request object which still refers to types, a deprecated feature. The method
     * {@link #get(GetIndexRequest, RequestOptions)} should be used instead, which accepts a new request object.
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.get.GetIndexResponse get(
            org.elasticsearch.action.admin.indices.get.GetIndexRequest getIndexRequest, RequestOptions options) throws IOException {
        return indicesClient.get(getIndexRequest, options);
    }

    /**
     * Retrieve information about one or more indexes
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-get-index.html">
     * Indices Get Index API on elastic.co</a>
     * ----------------------------------------
     * 异步获取一个或者多个 indices的 information（已经弃用）
     * ----------------------------------------
     *
     * @param getIndexRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener        the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated This method uses an old request object which still refers to types, a deprecated feature. The method
     * {@link #getAsync(GetIndexRequest, RequestOptions, ActionListener)} should be used instead, which accepts a new request object.
     */
    @Deprecated
    public Cancellable getAsync(org.elasticsearch.action.admin.indices.get.GetIndexRequest getIndexRequest, RequestOptions options,
                                ActionListener<org.elasticsearch.action.admin.indices.get.GetIndexResponse> listener) {
        return indicesClient.getAsync(getIndexRequest, options, listener);
    }

    /**
     * Force merge one or more indices using the Force Merge API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html">
     * Force Merge API on elastic.co</a>
     * ----------------------------------------
     * 强制合并一个或者多个indices（已经弃用 {@link #forcemerge(ForceMergeRequest, RequestOptions)} ）
     * ----------------------------------------
     *
     * @param forceMergeRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated use {@link #forcemerge(ForceMergeRequest, RequestOptions)} instead
     */
    @Deprecated
    public ForceMergeResponse forceMerge(ForceMergeRequest forceMergeRequest, RequestOptions options) throws IOException {
        return forcemerge(forceMergeRequest, options);
    }

    /**
     * Asynchronously force merge one or more indices using the Force Merge API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-forcemerge.html">
     * Force Merge API on elastic.co</a>
     * ----------------------------------------
     * 异步强制合并一个或者多个indices（已经弃用 {@link #forcemergeAsync(ForceMergeRequest, RequestOptions, ActionListener)}）
     * ----------------------------------------
     *
     * @param forceMergeRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated use {@link #forcemergeAsync(ForceMergeRequest, RequestOptions, ActionListener)} instead
     */
    @Deprecated
    public Cancellable forceMergeAsync(ForceMergeRequest forceMergeRequest, RequestOptions options,
                                       ActionListener<ForceMergeResponse> listener) {
        return forcemergeAsync(forceMergeRequest, options, listener);
    }

    /**
     * Checks if the index (indices) exists or not.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-exists.html">
     * Indices Exists API on elastic.co</a>
     * ----------------------------------------
     * 检查一个或者多个indices的是否存在（已经弃用 {@link #exists(GetIndexRequest, RequestOptions)} ）
     * ----------------------------------------
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request
     * @deprecated This method uses an old request object which still refers to types, a deprecated feature. The method
     * {@link #exists(GetIndexRequest, RequestOptions)} should be used instead, which accepts a new request object.
     */
    @Deprecated
    public boolean exists(org.elasticsearch.action.admin.indices.get.GetIndexRequest request, RequestOptions options) throws IOException {
        return indicesClient.exists(request, options);
    }

    /**
     * Asynchronously checks if the index (indices) exists or not.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-exists.html">
     * Indices Exists API on elastic.co</a>
     * ----------------------------------------
     * 异步检查一个或者多个indices的是否存在（已经弃用 {@link #existsAsync(GetIndexRequest, RequestOptions, ActionListener)} ）
     * ----------------------------------------
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated This method uses an old request object which still refers to types, a deprecated feature. The method
     * {@link #existsAsync(GetIndexRequest, RequestOptions, ActionListener)} should be used instead, which accepts a new request object.
     */
    @Deprecated
    public Cancellable existsAsync(org.elasticsearch.action.admin.indices.get.GetIndexRequest request, RequestOptions options,
                                   ActionListener<Boolean> listener) {
        return indicesClient.existsAsync(request, options, listener);
    }

    /**
     * Shrinks an index using the Shrink Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shrink-index.html">
     * Shrink Index API on elastic.co</a>
     * ----------------------------------------
     * 收缩一个Index（已经弃用 {@link #shrink(ResizeRequest, RequestOptions)}）
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated use {@link #shrink(ResizeRequest, RequestOptions)}
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.shrink.ResizeResponse shrink(
            org.elasticsearch.action.admin.indices.shrink.ResizeRequest resizeRequest, RequestOptions options) throws IOException {
        return indicesClient.shrink(resizeRequest, options);
    }

    /**
     * Asynchronously shrinks an index using the Shrink index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-shrink-index.html">
     * Shrink Index API on elastic.co</a>
     * ----------------------------------------
     * 异步收缩一个Index（已经弃用 {@link #shrinkAsync(ResizeRequest, RequestOptions, ActionListener)}）
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated use {@link #shrinkAsync(ResizeRequest, RequestOptions, ActionListener)}
     */
    @Deprecated
    public Cancellable shrinkAsync(org.elasticsearch.action.admin.indices.shrink.ResizeRequest resizeRequest, RequestOptions options,
                                   ActionListener<org.elasticsearch.action.admin.indices.shrink.ResizeResponse> listener) {
        return indicesClient.shrinkAsync(resizeRequest, options, listener);
    }

    /**
     * Splits an index using the Split Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-split-index.html">
     * Split Index API on elastic.co</a>
     * ----------------------------------------
     * 拆分index（已经弃用  {@link #split(ResizeRequest, RequestOptions)}）
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated use {@link #split(ResizeRequest, RequestOptions)}
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.shrink.ResizeResponse split(
            org.elasticsearch.action.admin.indices.shrink.ResizeRequest resizeRequest, RequestOptions options) throws IOException {
        return indicesClient.split(resizeRequest, options);
    }

    /**
     * Asynchronously splits an index using the Split Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-split-index.html">
     * Split Index API on elastic.co</a>
     * ----------------------------------------
     * 异步拆分index（已经弃用 {@link #splitAsync(ResizeRequest, RequestOptions, ActionListener)}）
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated use {@link #splitAsync(ResizeRequest, RequestOptions, ActionListener)}
     */
    @Deprecated
    public Cancellable splitAsync(org.elasticsearch.action.admin.indices.shrink.ResizeRequest resizeRequest, RequestOptions options,
                                  ActionListener<org.elasticsearch.action.admin.indices.shrink.ResizeResponse> listener) {
        return indicesClient.splitAsync(resizeRequest, options, listener);
    }

    /**
     * Clones an index using the Clone Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clone-index.html">
     * Clone Index API on elastic.co</a>
     * ----------------------------------------
     * 克隆一个index（已经弃用 {@link #clone(ResizeRequest, RequestOptions)} ）
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated use {@link #clone(ResizeRequest, RequestOptions)}
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.shrink.ResizeResponse clone(
            org.elasticsearch.action.admin.indices.shrink.ResizeRequest resizeRequest, RequestOptions options) throws IOException {
        return indicesClient.clone(resizeRequest, options);
    }

    /**
     * Asynchronously clones an index using the Clone Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-clone-index.html">
     * Clone Index API on elastic.co</a>
     * ----------------------------------------
     * 克隆一个index（已经弃用 {@link #cloneAsync(ResizeRequest, RequestOptions, ActionListener)}）
     * ----------------------------------------
     *
     * @param resizeRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated use {@link #cloneAsync(ResizeRequest, RequestOptions, ActionListener)}
     */
    @Deprecated
    public Cancellable cloneAsync(org.elasticsearch.action.admin.indices.shrink.ResizeRequest resizeRequest, RequestOptions options,
                                  ActionListener<org.elasticsearch.action.admin.indices.shrink.ResizeResponse> listener) {
        return indicesClient.cloneAsync(resizeRequest, options, listener);
    }

    /**
     * Rolls over an index using the Rollover Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-rollover-index.html">
     * Rollover Index API on elastic.co</a>
     * ----------------------------------------
     * 滚动一个index（已经弃用 {@link #rollover(RolloverRequest, RequestOptions)} ）
     * ----------------------------------------
     *
     * @param rolloverRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated This method uses deprecated request and response objects.
     * The method {@link #rollover(RolloverRequest, RequestOptions)} should be used instead, which accepts a new request object.
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.rollover.RolloverResponse rollover(
            org.elasticsearch.action.admin.indices.rollover.RolloverRequest rolloverRequest,
            RequestOptions options) throws IOException {
        return indicesClient.rollover(rolloverRequest, options);
    }

    /**
     * Asynchronously rolls over an index using the Rollover Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-rollover-index.html">
     * Rollover Index API on elastic.co</a>
     * ----------------------------------------
     * 异步滚动一个index（已经弃用 {@link #rollover(RolloverRequest, RequestOptions)} ）
     * ----------------------------------------
     *
     * @param rolloverRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener        the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated This method uses deprecated request and response objects.
     * The method {@link #rolloverAsync(RolloverRequest, RequestOptions, ActionListener)} should be used instead, which
     * accepts a new request object.
     */
    @Deprecated
    public Cancellable rolloverAsync(org.elasticsearch.action.admin.indices.rollover.RolloverRequest rolloverRequest,
                                     RequestOptions options,
                                     ActionListener<org.elasticsearch.action.admin.indices.rollover.RolloverResponse> listener) {
        return indicesClient.rolloverAsync(rolloverRequest, options, listener);
    }

    /**
     * Puts an index template using the Index Templates API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 设置index的template（已经弃用 {@link #putTemplate(PutIndexTemplateRequest, RequestOptions)}）
     * ----------------------------------------
     *
     * @param putIndexTemplateRequest the request
     * @param options                 the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated This old form of request allows types in mappings. Use {@link #putTemplate(PutIndexTemplateRequest, RequestOptions)}
     * instead which introduces a new request object without types.
     */
    @Deprecated
    public AcknowledgedResponse putTemplate(
            org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest putIndexTemplateRequest,
            RequestOptions options) throws IOException {
        return indicesClient.putTemplate(putIndexTemplateRequest, options);
    }

    /**
     * Asynchronously puts an index template using the Index Templates API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 异步设置index的template （已经弃用 {@link #putTemplateAsync(PutIndexTemplateRequest, RequestOptions, ActionListener)}）
     * ----------------------------------------
     *
     * @param putIndexTemplateRequest the request
     * @param options                 the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated This old form of request allows types in mappings.
     * Use {@link #putTemplateAsync(PutIndexTemplateRequest, RequestOptions, ActionListener)}
     * instead which introduces a new request object without types.
     */
    @Deprecated
    public Cancellable putTemplateAsync(
            org.elasticsearch.action.admin.indices.template.put.PutIndexTemplateRequest putIndexTemplateRequest,
            RequestOptions options, ActionListener<AcknowledgedResponse> listener) {
        return indicesClient.putTemplateAsync(putIndexTemplateRequest, options, listener);
    }

    /**
     * Gets index templates using the Index Templates API. The mappings will be returned in a legacy deprecated format, where the
     * mapping definition is nested under the type name.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 获取index的templates（已经弃用 #getIndexTemplate(GetIndexTemplatesRequest, RequestOptions)} ）
     * ----------------------------------------
     *
     * @param getIndexTemplatesRequest the request
     * @param options                  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @throws IOException in case there is a problem sending the request or parsing back the response
     * @deprecated This method uses an old response object which still refers to types, a deprecated feature. Use
     * {@link #getIndexTemplate(GetIndexTemplatesRequest, RequestOptions)} instead which returns a new response object
     */
    @Deprecated
    public org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse getTemplate(
            GetIndexTemplatesRequest getIndexTemplatesRequest, RequestOptions options) throws IOException {
        return indicesClient.getTemplate(getIndexTemplatesRequest, options);
    }

    /**
     * Asynchronously gets index templates using the Index Templates API. The mappings will be returned in a legacy deprecated format,
     * where the mapping definition is nested under the type name.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices-templates.html"> Index Templates API
     * on elastic.co</a>
     * ----------------------------------------
     * 异步获取index的templates（已经弃用  {@link #getIndexTemplateAsync(GetIndexTemplatesRequest, RequestOptions, ActionListener)}）
     * ----------------------------------------
     *
     * @param getIndexTemplatesRequest the request
     * @param options                  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                 the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated This method uses an old response object which still refers to types, a deprecated feature. Use
     * {@link #getIndexTemplateAsync(GetIndexTemplatesRequest, RequestOptions, ActionListener)} instead which returns a new response object
     */
    @Deprecated
    public Cancellable getTemplateAsync(GetIndexTemplatesRequest getIndexTemplatesRequest, RequestOptions options,
                                        ActionListener<org.elasticsearch.action.admin.indices.template.get.GetIndexTemplatesResponse> listener) {
        return indicesClient.getTemplateAsync(getIndexTemplatesRequest, options, listener);
    }

}
