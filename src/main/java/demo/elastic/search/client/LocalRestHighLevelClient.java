package demo.elastic.search.client;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse;
import org.elasticsearch.action.admin.cluster.storedscripts.DeleteStoredScriptRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptRequest;
import org.elasticsearch.action.admin.cluster.storedscripts.GetStoredScriptResponse;
import org.elasticsearch.action.admin.cluster.storedscripts.PutStoredScriptRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.explain.ExplainRequest;
import org.elasticsearch.action.explain.ExplainResponse;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesRequest;
import org.elasticsearch.action.fieldcaps.FieldCapabilitiesResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.*;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.client.core.*;
import org.elasticsearch.client.tasks.TaskSubmissionResponse;
import org.elasticsearch.common.CheckedConsumer;
import org.elasticsearch.index.rankeval.RankEvalRequest;
import org.elasticsearch.index.rankeval.RankEvalResponse;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.index.reindex.UpdateByQueryRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateRequest;
import org.elasticsearch.script.mustache.MultiSearchTemplateResponse;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * High level REST client that wraps an instance of the low level {@link RestClient} and allows to build requests and read responses. The
 * {@link RestClient} instance is internally built based on the provided {@link RestClientBuilder} and it gets closed automatically when
 * closing the {@link RestHighLevelClient} instance that wraps it.
 * <p>
 * -----------------------
 * 高级REST客户端，包裹了低级的 {@link RestClient} ， 并且允许构建一个请求和读取响应
 * {@link RestClient} 实例是 {@link RestClientBuilder}内部构建的，当高级REST关闭时，低级REST会自动关闭
 * -----------------------
 * <p>
 * In case an already existing instance of a low-level REST client needs to be provided, this class can be subclassed and the
 * {@link #LocalRestHighLevelClient(RestClient, CheckedConsumer, List)} constructor can be used.
 * <p>
 * -----------------------
 * 当已经存在一个低级REST客户端时，可以直接构造
 * -----------------------
 * <p>
 * This class can also be sub-classed to expose additional client methods that make use of endpoints added to Elasticsearch through plugins,
 * or to add support for custom response sections, again added to Elasticsearch through plugins.
 * <p>
 * -----------------------
 * 这个class可以被继承去拓展附加的客户端方法
 * -----------------------
 * <p>
 * The majority of the methods in this class come in two flavors, a blocking and an asynchronous version (e.g.
 * {@link #search(SearchRequest, RequestOptions)} and {@link #searchAsync(SearchRequest, RequestOptions, ActionListener)}, where the later
 * takes an implementation of an {@link ActionListener} as an argument that needs to implement methods that handle successful responses and
 * failure scenarios. Most of the blocking calls can throw an {@link IOException} or an unchecked {@link ElasticsearchException} in the
 * following cases:
 *
 * <ul>
 * <li>an {@link IOException} is usually thrown in case of failing to parse the REST response in the high-level REST client, the request
 * times out or similar cases where there is no response coming back from the Elasticsearch server</li>
 * <li>an {@link ElasticsearchException} is usually thrown in case where the server returns a 4xx or 5xx error code. The high-level client
 * then tries to parse the response body error details into a generic ElasticsearchException and suppresses the original
 * {@link ResponseException}</li>
 * </ul>
 * -----------------------
 * 多数的方法有两种风格 同步和异步
 * -----------------------
 */
public class LocalRestHighLevelClient {

    public RestHighLevelClient restHighLevelClient;


    public LocalRestHighLevelClient(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }


    /**
     * Returns the low-level client that the current high-level client instance is using to perform requests
     * -----------------------
     * 返回当前的低级客户端
     * -----------------------
     */
    public final RestClient getLowLevelClient() {
        return restHighLevelClient.getLowLevelClient();
    }


    /**
     * Provides an {@link IndicesClient} which can be used to access the Indices API.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/indices.html">Indices API on elastic.co</a>
     * -----------------------
     * 提供 {@link IndicesClient}去访问 Indices 的API
     * -----------------------
     */
    public final IndicesClient indices() {
        return restHighLevelClient.indices();
    }

    /**
     * Provides a {@link ClusterClient} which can be used to access the Cluster API.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/cluster.html">Cluster API on elastic.co</a>
     * -----------------------
     * 提供 {@link ClusterClient}去访问 cluster 的API
     * -----------------------
     */
    public final ClusterClient cluster() {
        return restHighLevelClient.cluster();
    }

    /**
     * Provides a {@link IngestClient} which can be used to access the Ingest API.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/ingest.html">Ingest API on elastic.co</a>
     * -----------------------
     * 提供 {@link IngestClient}去访问 Ingest 的API （摄取？）
     * -----------------------
     */
    public final IngestClient ingest() {
        return restHighLevelClient.ingest();
    }

    /**
     * Provides a {@link SnapshotClient} which can be used to access the Snapshot API.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-snapshots.html">Snapshot API on elastic.co</a>
     * -----------------------
     * 提供 {@link SnapshotClient}去访问 Snapshot 的API （快照）
     * -----------------------
     */
    public final SnapshotClient snapshot() {
        return restHighLevelClient.snapshot();
    }

    /**
     * Provides methods for accessing the Elastic Licensed Rollup APIs that
     * are shipped with the default distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/rollup-apis.html">
     * Watcher APIs on elastic.co</a> for more information.
     * -----------------------
     * 提供 {@link RollupClient}去访问 Rollup 的API （汇总？）
     * 所有API将会是404如果违反 OSS发行版本？？？
     * -----------------------
     */
    public RollupClient rollup() {
        return restHighLevelClient.rollup();
    }

    /**
     * Provides methods for accessing the Elastic Licensed CCR APIs that
     * are shipped with the Elastic Stack distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/ccr-api.html">
     * CCR APIs on elastic.co</a> for more information.
     *
     * @return the client wrapper for making CCR API calls
     */
    public final CcrClient ccr() {
        return restHighLevelClient.ccr();
    }

    /**
     * Provides a {@link TasksClient} which can be used to access the Tasks API.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/tasks.html">Task Management API on elastic.co</a>
     * -----------------------
     * 提供 {@link TasksClient}去访问 Tasks 的API （任务管理，这个是新功能）
     * -----------------------
     */
    public final TasksClient tasks() {
        return restHighLevelClient.tasks();
    }

    /**
     * Provides methods for accessing the Elastic Licensed X-Pack Info
     * and Usage APIs that are shipped with the default distribution of
     * Elasticsearch. All of these APIs will 404 if run against the OSS
     * distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/info-api.html">
     * Info APIs on elastic.co</a> for more information.
     * -----------------------
     * 提供 {@link XPackClient}去访问 X-Pack 的API （信息API）
     * -----------------------
     */
    public final XPackClient xpack() {
        return restHighLevelClient.xpack();
    }

    /**
     * Provides methods for accessing the Elastic Licensed Watcher APIs that
     * are shipped with the default distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasti  csearch/reference/current/watcher-api.html">
     * Watcher APIs on elastic.co</a> for more information.
     * -----------------------
     * 提供 {@link WatcherClient}去访问WatcherClient 的API （观察者API）
     * -----------------------
     */
    public WatcherClient watcher() {
        return restHighLevelClient.watcher();
    }

    /**
     * Provides methods for accessing the Elastic Licensed Graph explore API that
     * is shipped with the default distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/graph-explore-api.html">
     * Graph API on elastic.co</a> for more information.
     * -----------------------
     * 提供 {@link GraphClient}去访问 GraphClient 的 The Graph explore API （探索API???）
     * -----------------------
     */
    public GraphClient graph() {
        return restHighLevelClient.graph();
    }

    /**
     * Provides methods for accessing the Elastic Licensed Licensing APIs that
     * are shipped with the default distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/licensing-apis.html">
     * Licensing APIs on elastic.co</a> for more information.
     * -----------------------
     * 提供 {@link LicenseClient}去访问 LicenseClient 的 API （许可API）
     * -----------------------
     */
    public LicenseClient license() {
        return restHighLevelClient.license();
    }

    /**
     * A wrapper for the {@link RestHighLevelClient} that provides methods for
     * accessing the Elastic Index Lifecycle APIs.
     * <p>
     * See the <a href="http://FILL-ME-IN-WE-HAVE-NO-DOCS-YET.com"> X-Pack APIs
     * on elastic.co</a> for more information.
     * -----------------------
     * 提供 {@link IndexLifecycleClient}去访问  API （index的生命周期API）
     * -----------------------
     */
    public IndexLifecycleClient indexLifecycle() {
        return restHighLevelClient.indexLifecycle();
    }

    /**
     * Provides methods for accessing the Elastic Licensed Migration APIs that
     * are shipped with the default distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/migration-api.html">
     * Migration APIs on elastic.co</a> for more information.
     * -----------------------
     * 提供 {@link MigrationClient}去访问  Migration API （迁移API）
     * -----------------------
     */
    public MigrationClient migration() {
        return restHighLevelClient.migration();
    }

    /**
     * Provides methods for accessing the Elastic Licensed Machine Learning APIs that
     * are shipped with the Elastic Stack distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/ml-apis.html">
     * Machine Learning APIs on elastic.co</a> for more information.
     *
     * @return the client wrapper for making Machine Learning API calls
     * -----------------------
     * 提供 {@link MachineLearningClient}去访问  Machine Learning API （机器学习API??）
     * -----------------------
     */
    public MachineLearningClient machineLearning() {
        return restHighLevelClient.machineLearning();
    }

    /**
     * Provides methods for accessing the Elastic Licensed Security APIs that
     * are shipped with the Elastic Stack distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/security-api.html">
     * Security APIs on elastic.co</a> for more information.
     *
     * @return the client wrapper for making Security API calls
     * -----------------------
     * 提供 {@link SecurityClient}去访问  Security API （安全API）
     * -----------------------
     */
    public SecurityClient security() {
        return restHighLevelClient.security();
    }

    /**
     * Provides methods for accessing the Elastic Licensed Data Frame APIs that
     * are shipped with the Elastic Stack distribution of Elasticsearch. All of
     * these APIs will 404 if run against the OSS distribution of Elasticsearch.
     * <p>
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/transform-apis.html">
     * Transform APIs on elastic.co</a> for more information.
     *
     * @return the client wrapper for making Data Frame API calls
     * -----------------------
     * 提供 {@link TransformClient}去访问  Data Frame API （转换API?）
     * -----------------------
     */
    public TransformClient transform() {
        return restHighLevelClient.transform();
    }

    public EnrichClient enrich() {
        return restHighLevelClient.enrich();
    }

    /**
     * Executes a bulk request using the Bulk API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html">Bulk API on elastic.co</a>
     *
     * @param bulkRequest the request
     * @param options     the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * -----------------------
     * 执行批量请求
     * -----------------------
     */
    public final BulkResponse bulk(BulkRequest bulkRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.bulk(bulkRequest, options);
    }

    /**
     * Asynchronously executes a bulk request using the Bulk API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-bulk.html">Bulk API on elastic.co</a>
     *
     * @param bulkRequest the request
     * @param options     the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener    the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable bulkAsync(BulkRequest bulkRequest, RequestOptions options, ActionListener<BulkResponse> listener) {
        return restHighLevelClient.bulkAsync(bulkRequest, options, listener);
    }

    /**
     * Executes a reindex request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html">Reindex API on elastic.co</a>
     *
     * @param reindexRequest the request
     * @param options        the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * -----------------------
     * 重新索引API(似乎是把文档从一个index复制到另一个index)
     * -----------------------
     */
    public final BulkByScrollResponse reindex(ReindexRequest reindexRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.reindex(reindexRequest, options);
    }

    /**
     * Submits a reindex task.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html">Reindex API on elastic.co</a>
     *
     * @param reindexRequest the request
     * @param options        the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the submission response
     */
    public final TaskSubmissionResponse submitReindexTask(ReindexRequest reindexRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.submitReindexTask(reindexRequest, options);
    }

    /**
     * Asynchronously executes a reindex request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html">Reindex API on elastic.co</a>
     *
     * @param reindexRequest the request
     * @param options        the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener       the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable reindexAsync(ReindexRequest reindexRequest, RequestOptions options,
                                          ActionListener<BulkByScrollResponse> listener) {
        return restHighLevelClient.reindexAsync(reindexRequest, options, listener);
    }

    /**
     * Executes a update by query request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html">
     * Update By Query API on elastic.co</a>
     *
     * @param updateByQueryRequest the request
     * @param options              the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final BulkByScrollResponse updateByQuery(UpdateByQueryRequest updateByQueryRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.updateByQuery(updateByQueryRequest, options);
    }

    /**
     * Asynchronously executes an update by query request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html">
     * Update By Query API on elastic.co</a>
     *
     * @param updateByQueryRequest the request
     * @param options              the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener             the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable updateByQueryAsync(UpdateByQueryRequest updateByQueryRequest, RequestOptions options,
                                                ActionListener<BulkByScrollResponse> listener) {
        return restHighLevelClient.updateByQueryAsync(updateByQueryRequest, options, listener);
    }

    /**
     * Executes a delete by query request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete-by-query.html">
     * Delete By Query API on elastic.co</a>
     *
     * @param deleteByQueryRequest the request
     * @param options              the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final BulkByScrollResponse deleteByQuery(DeleteByQueryRequest deleteByQueryRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.deleteByQuery(deleteByQueryRequest, options);
    }

    /**
     * Submits a delete by query task
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete-by-query.html">
     * Delete By Query API on elastic.co</a>
     *
     * @param deleteByQueryRequest the request
     * @param options              the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the submission response
     */
    public final TaskSubmissionResponse submitDeleteByQueryTask(DeleteByQueryRequest deleteByQueryRequest,
                                                                RequestOptions options) throws IOException {
        return restHighLevelClient.submitDeleteByQueryTask(deleteByQueryRequest, options);
    }

    /**
     * Asynchronously executes a delete by query request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete-by-query.html">
     * Delete By Query API on elastic.co</a>
     *
     * @param deleteByQueryRequest the request
     * @param options              the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener             the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable deleteByQueryAsync(DeleteByQueryRequest deleteByQueryRequest, RequestOptions options,
                                                ActionListener<BulkByScrollResponse> listener) {
        return restHighLevelClient.deleteByQueryAsync(deleteByQueryRequest, options, listener);
    }

    /**
     * Executes a delete by query rethrottle request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete-by-query.html">
     * Delete By Query API on elastic.co</a>
     *
     * @param rethrottleRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final ListTasksResponse deleteByQueryRethrottle(RethrottleRequest rethrottleRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.deleteByQueryRethrottle(rethrottleRequest, options);
    }

    /**
     * Asynchronously execute an delete by query rethrottle request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete-by-query.html">
     * Delete By Query API on elastic.co</a>
     *
     * @param rethrottleRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable deleteByQueryRethrottleAsync(RethrottleRequest rethrottleRequest, RequestOptions options,
                                                          ActionListener<ListTasksResponse> listener) {
        return restHighLevelClient.deleteByQueryRethrottleAsync(rethrottleRequest, options, listener);
    }

    /**
     * Executes a update by query rethrottle request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html">
     * Update By Query API on elastic.co</a>
     *
     * @param rethrottleRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final ListTasksResponse updateByQueryRethrottle(RethrottleRequest rethrottleRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.updateByQueryRethrottle(rethrottleRequest, options);
    }

    /**
     * Asynchronously execute an update by query rethrottle request.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update-by-query.html">
     * Update By Query API on elastic.co</a>
     *
     * @param rethrottleRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable updateByQueryRethrottleAsync(RethrottleRequest rethrottleRequest, RequestOptions options,
                                                          ActionListener<ListTasksResponse> listener) {
        return restHighLevelClient.updateByQueryRethrottleAsync(rethrottleRequest, options, listener);
    }

    /**
     * Executes a reindex rethrottling request.
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html#docs-reindex-rethrottle">
     * Reindex rethrottling API on elastic.co</a>
     *
     * @param rethrottleRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final ListTasksResponse reindexRethrottle(RethrottleRequest rethrottleRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.reindexRethrottle(rethrottleRequest, options);
    }

    /**
     * Executes a reindex rethrottling request.
     * See the <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-reindex.html#docs-reindex-rethrottle">
     * Reindex rethrottling API on elastic.co</a>
     *
     * @param rethrottleRequest the request
     * @param options           the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener          the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable reindexRethrottleAsync(RethrottleRequest rethrottleRequest, RequestOptions options,
                                                    ActionListener<ListTasksResponse> listener) {
        return restHighLevelClient.reindexRethrottleAsync(rethrottleRequest, options, listener);
    }

    /**
     * Pings the remote Elasticsearch cluster and returns true if the ping succeeded, false otherwise
     *
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return <code>true</code> if the ping succeeded, false otherwise
     */
    public final boolean ping(RequestOptions options) throws IOException {
        return restHighLevelClient.ping(options);
    }

    /**
     * Get the cluster info otherwise provided when sending an HTTP request to '/'
     *
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * -----------------------
     * 获取集群信息
     * <pre>
     * {
     *     "clusterUuid":"XQ_2CWorR--HKQBUFWJ9Rw",
     *     "nodeName":"localhost.localdomain",
     *     "clusterName":"elasticsearch",
     *     "tagline":"You Know, for Search",
     *     "version":{
     *         "number":"7.6.2",
     *         "buildFlavor":"default",
     *         "buildHash":"ef48eb35cf30adf4db14086e8aabd07ef6fb113f",
     *         "minimumIndexCompatibilityVersion":"6.0.0-beta1",
     *         "minimumWireCompatibilityVersion":"6.8.0",
     *         "buildType":"tar",
     *         "luceneVersion":"8.4.0",
     *         "buildDate":"2020-03-26T06:34:37.794943Z",
     *         "snapshot":false
     *     }
     * }
     * </pre>
     * -----------------------
     */
    public final MainResponse info(RequestOptions options) throws IOException {
        return restHighLevelClient.info(options);
    }

    /**
     * Retrieves a document by id using the Get API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html">Get API on elastic.co</a>
     *
     * @param getRequest the request
     * @param options    the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final GetResponse get(GetRequest getRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.get(getRequest, options);
    }

    /**
     * Asynchronously retrieves a document by id using the Get API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html">Get API on elastic.co</a>
     *
     * @param getRequest the request
     * @param options    the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener   the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable getAsync(GetRequest getRequest, RequestOptions options, ActionListener<GetResponse> listener) {
        return restHighLevelClient.getAsync(getRequest, options, listener);
    }

    /**
     * Retrieves multiple documents by id using the Multi Get API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html">Multi Get API on elastic.co</a>
     *
     * @param multiGetRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @deprecated use {@link #mget(MultiGetRequest, RequestOptions)} instead
     */
    @Deprecated
    public final MultiGetResponse multiGet(MultiGetRequest multiGetRequest, RequestOptions options) throws IOException {
        return mget(multiGetRequest, options);
    }


    /**
     * Retrieves multiple documents by id using the Multi Get API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html">Multi Get API on elastic.co</a>
     *
     * @param multiGetRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final MultiGetResponse mget(MultiGetRequest multiGetRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.mget(multiGetRequest, options);
    }

    /**
     * Asynchronously retrieves multiple documents by id using the Multi Get API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html">Multi Get API on elastic.co</a>
     *
     * @param multiGetRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener        the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated use {@link #mgetAsync(MultiGetRequest, RequestOptions, ActionListener)} instead
     */
    @Deprecated
    public final Cancellable multiGetAsync(MultiGetRequest multiGetRequest, RequestOptions options,
                                           ActionListener<MultiGetResponse> listener) {
        return mgetAsync(multiGetRequest, options, listener);
    }

    /**
     * Asynchronously retrieves multiple documents by id using the Multi Get API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-get.html">Multi Get API on elastic.co</a>
     *
     * @param multiGetRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener        the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable mgetAsync(MultiGetRequest multiGetRequest, RequestOptions options,
                                       ActionListener<MultiGetResponse> listener) {
        return restHighLevelClient.mgetAsync(multiGetRequest, options, listener);
    }

    /**
     * Checks for the existence of a document. Returns true if it exists, false otherwise.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html">Get API on elastic.co</a>
     *
     * @param getRequest the request
     * @param options    the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return <code>true</code> if the document exists, <code>false</code> otherwise
     */
    public final boolean exists(GetRequest getRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.exists(getRequest, options);
    }

    /**
     * Asynchronously checks for the existence of a document. Returns true if it exists, false otherwise.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html">Get API on elastic.co</a>
     *
     * @param getRequest the request
     * @param options    the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener   the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable existsAsync(GetRequest getRequest, RequestOptions options, ActionListener<Boolean> listener) {
        return restHighLevelClient.existsAsync(getRequest, options, listener);
    }

    /**
     * Checks for the existence of a document with a "_source" field. Returns true if it exists, false otherwise.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#_source">Source exists API
     * on elastic.co</a>
     *
     * @param getRequest the request
     * @param options    the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return <code>true</code> if the document and _source field exists, <code>false</code> otherwise
     */
    public boolean existsSource(GetRequest getRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.existsSource(getRequest, options);
    }

    /**
     * Asynchronously checks for the existence of a document with a "_source" field. Returns true if it exists, false otherwise.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-get.html#_source">Source exists API
     * on elastic.co</a>
     *
     * @param getRequest the request
     * @param options    the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener   the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable existsSourceAsync(GetRequest getRequest, RequestOptions options, ActionListener<Boolean> listener) {
        return restHighLevelClient.existsSourceAsync(getRequest, options, listener);
    }

    /**
     * Index a document using the Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html">Index API on elastic.co</a>
     *
     * @param indexRequest the request
     * @param options      the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * ---------------------------------------------
     * 把JSON文档添加到指定index(插入)
     * <pre>
     * {
     *     "result":"CREATED",
     *     "fragment":false,
     *     "seqNo":0,
     *     "primaryTerm":1,
     *     "index":"index_tmp",
     *     "shardId":{
     *         "fragment":true,
     *         "indexName":"index_tmp",
     *         "index":{
     *             "fragment":false,
     *             "name":"index_tmp",
     *             "uUID":"_na_"
     *         },
     *         "id":-1
     *     },
     *     "id":"47c30a2c-8cd6-4be3-8386-2e163d14fbd8",
     *     "type":"_doc",
     *     "shardInfo":{
     *         "fragment":false,
     *         "total":2,
     *         "failures":[
     *
     *         ],
     *         "failed":0,
     *         "successful":1
     *     },
     *     "version":1
     * }
     * </pre>
     * ---------------------------------------------
     */
    public final IndexResponse index(IndexRequest indexRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.index(indexRequest, options);
    }

    /**
     * Asynchronously index a document using the Index API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-index_.html">Index API on elastic.co</a>
     *
     * @param indexRequest the request
     * @param options      the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener     the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable indexAsync(IndexRequest indexRequest, RequestOptions options, ActionListener<IndexResponse> listener) {
        return restHighLevelClient.indexAsync(indexRequest, options, listener);
    }

    /**
     * Executes a count request using the Count API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html">Count API on elastic.co</a>
     *
     * @param countRequest the request
     * @param options      the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final CountResponse count(CountRequest countRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.count(countRequest, options);
    }

    /**
     * Asynchronously executes a count request using the Count API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-count.html">Count API on elastic.co</a>
     *
     * @param countRequest the request
     * @param options      the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener     the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable countAsync(CountRequest countRequest, RequestOptions options, ActionListener<CountResponse> listener) {
        return restHighLevelClient.countAsync(countRequest, options, listener);
    }

    /**
     * Updates a document using the Update API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update.html">Update API on elastic.co</a>
     *
     * @param updateRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final UpdateResponse update(UpdateRequest updateRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.update(updateRequest, options);
    }

    /**
     * Asynchronously updates a document using the Update API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-update.html">Update API on elastic.co</a>
     *
     * @param updateRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable updateAsync(UpdateRequest updateRequest, RequestOptions options, ActionListener<UpdateResponse> listener) {
        return restHighLevelClient.updateAsync(updateRequest, options, listener);
    }

    /**
     * Deletes a document by id using the Delete API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete.html">Delete API on elastic.co</a>
     *
     * @param deleteRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final DeleteResponse delete(DeleteRequest deleteRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.delete(deleteRequest, options);
    }

    /**
     * Asynchronously deletes a document by id using the Delete API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-delete.html">Delete API on elastic.co</a>
     *
     * @param deleteRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable deleteAsync(DeleteRequest deleteRequest, RequestOptions
            options, ActionListener<DeleteResponse> listener) {
        return restHighLevelClient.deleteAsync(deleteRequest, options, listener);
    }

    /**
     * Executes a search request using the Search API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-search.html">Search API on elastic.co</a>
     *
     * @param searchRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final SearchResponse search(SearchRequest searchRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.search(searchRequest, options);
    }

    /**
     * Asynchronously executes a search using the Search API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-search.html">Search API on elastic.co</a>
     *
     * @param searchRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable searchAsync(SearchRequest searchRequest, RequestOptions
            options, ActionListener<SearchResponse> listener) {
        return restHighLevelClient.searchAsync(searchRequest, options, listener);
    }

    /**
     * Executes a multi search using the msearch API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html">Multi search API on
     * elastic.co</a>
     *
     * @param multiSearchRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @deprecated use {@link #msearch(MultiSearchRequest, RequestOptions)} instead
     */
    @Deprecated
    public final MultiSearchResponse multiSearch(MultiSearchRequest multiSearchRequest, RequestOptions options) throws
            IOException {
        return msearch(multiSearchRequest, options);
    }

    /**
     * Executes a multi search using the msearch API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html">Multi search API on
     * elastic.co</a>
     *
     * @param multiSearchRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final MultiSearchResponse msearch(MultiSearchRequest multiSearchRequest, RequestOptions options) throws
            IOException {
        return restHighLevelClient.msearch(multiSearchRequest, options);
    }

    /**
     * Asynchronously executes a multi search using the msearch API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html">Multi search API on
     * elastic.co</a>
     *
     * @param searchRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated use {@link #msearchAsync(MultiSearchRequest, RequestOptions, ActionListener)} instead
     */
    @Deprecated
    public final Cancellable multiSearchAsync(MultiSearchRequest searchRequest, RequestOptions options,
                                              ActionListener<MultiSearchResponse> listener) {
        return msearchAsync(searchRequest, options, listener);
    }

    /**
     * Asynchronously executes a multi search using the msearch API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-multi-search.html">Multi search API on
     * elastic.co</a>
     *
     * @param searchRequest the request
     * @param options       the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener      the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable msearchAsync(MultiSearchRequest searchRequest, RequestOptions options,
                                          ActionListener<MultiSearchResponse> listener) {
        return restHighLevelClient.msearchAsync(searchRequest, options, listener);
    }

    /**
     * Executes a search using the Search Scroll API.
     * See <a
     * href="https://www.elastic.co/guide/en/elasticsearch/reference/master/search-request-body.html#request-body-search-scroll">Search
     * Scroll API on elastic.co</a>
     *
     * @param searchScrollRequest the request
     * @param options             the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     * @deprecated use {@link #scroll(SearchScrollRequest, RequestOptions)} instead
     */
    @Deprecated
    public final SearchResponse searchScroll(SearchScrollRequest searchScrollRequest, RequestOptions options) throws
            IOException {
        return scroll(searchScrollRequest, options);
    }

    /**
     * Executes a search using the Search Scroll API.
     * See <a
     * href="https://www.elastic.co/guide/en/elasticsearch/reference/master/search-request-body.html#request-body-search-scroll">Search
     * Scroll API on elastic.co</a>
     *
     * @param searchScrollRequest the request
     * @param options             the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final SearchResponse scroll(SearchScrollRequest searchScrollRequest, RequestOptions options) throws
            IOException {
        return restHighLevelClient.scroll(searchScrollRequest, options);
    }

    /**
     * Asynchronously executes a search using the Search Scroll API.
     * See <a
     * href="https://www.elastic.co/guide/en/elasticsearch/reference/master/search-request-body.html#request-body-search-scroll">Search
     * Scroll API on elastic.co</a>
     *
     * @param searchScrollRequest the request
     * @param options             the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener            the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     * @deprecated use {@link #scrollAsync(SearchScrollRequest, RequestOptions, ActionListener)} instead
     */
    @Deprecated
    public final Cancellable searchScrollAsync(SearchScrollRequest searchScrollRequest, RequestOptions options,
                                               ActionListener<SearchResponse> listener) {
        return scrollAsync(searchScrollRequest, options, listener);
    }

    /**
     * Asynchronously executes a search using the Search Scroll API.
     * See <a
     * href="https://www.elastic.co/guide/en/elasticsearch/reference/master/search-request-body.html#request-body-search-scroll">Search
     * Scroll API on elastic.co</a>
     *
     * @param searchScrollRequest the request
     * @param options             the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener            the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable scrollAsync(SearchScrollRequest searchScrollRequest, RequestOptions options,
                                         ActionListener<SearchResponse> listener) {
        return restHighLevelClient.scrollAsync(searchScrollRequest, options, listener);
    }

    /**
     * Clears one or more scroll ids using the Clear Scroll API.
     * See <a
     * href="https://www.elastic.co/guide/en/elasticsearch/reference/master/search-request-body.html#_clear_scroll_api">
     * Clear Scroll API on elastic.co</a>
     *
     * @param clearScrollRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final ClearScrollResponse clearScroll(ClearScrollRequest clearScrollRequest, RequestOptions options) throws
            IOException {
        return restHighLevelClient.clearScroll(clearScrollRequest, options);
    }

    /**
     * Asynchronously clears one or more scroll ids using the Clear Scroll API.
     * See <a
     * href="https://www.elastic.co/guide/en/elasticsearch/reference/master/search-request-body.html#_clear_scroll_api">
     * Clear Scroll API on elastic.co</a>
     *
     * @param clearScrollRequest the request
     * @param options            the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener           the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable clearScrollAsync(ClearScrollRequest clearScrollRequest, RequestOptions options,
                                              ActionListener<ClearScrollResponse> listener) {
        return restHighLevelClient.clearScrollAsync(clearScrollRequest, options, listener);
    }

    /**
     * Executes a request using the Search Template API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html">Search Template API
     * on elastic.co</a>.
     *
     * @param searchTemplateRequest the request
     * @param options               the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final SearchTemplateResponse searchTemplate(SearchTemplateRequest searchTemplateRequest,
                                                       RequestOptions options) throws IOException {
        return restHighLevelClient.searchTemplate(searchTemplateRequest, options);
    }

    /**
     * Asynchronously executes a request using the Search Template API.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-template.html">Search Template API
     * on elastic.co</a>.
     *
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable searchTemplateAsync(SearchTemplateRequest searchTemplateRequest, RequestOptions
            options, ActionListener<SearchTemplateResponse> listener) {
        return restHighLevelClient.searchTemplateAsync(searchTemplateRequest, options, listener);
    }

    /**
     * Executes a request using the Explain API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html">Explain API on elastic.co</a>
     *
     * @param explainRequest the request
     * @param options        the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final ExplainResponse explain(ExplainRequest explainRequest, RequestOptions options) throws IOException {
        return restHighLevelClient.explain(explainRequest, options);
    }

    /**
     * Asynchronously executes a request using the Explain API.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-explain.html">Explain API on elastic.co</a>
     *
     * @param explainRequest the request
     * @param options        the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener       the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable explainAsync(ExplainRequest explainRequest, RequestOptions
            options, ActionListener<ExplainResponse> listener) {
        return restHighLevelClient.explainAsync(explainRequest, options, listener);
    }


    /**
     * Calls the Term Vectors API
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-termvectors.html">Term Vectors API on
     * elastic.co</a>
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     */
    public final TermVectorsResponse termvectors(TermVectorsRequest request, RequestOptions options) throws
            IOException {
        return restHighLevelClient.termvectors(request, options);
    }

    /**
     * Asynchronously calls the Term Vectors API
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-termvectors.html">Term Vectors API on
     * elastic.co</a>
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable termvectorsAsync(TermVectorsRequest request, RequestOptions options,
                                              ActionListener<TermVectorsResponse> listener) {
        return restHighLevelClient.termvectorsAsync(request, options, listener);
    }


    /**
     * Calls the Multi Term Vectors API
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-termvectors.html">Multi Term Vectors API
     * on elastic.co</a>
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     */
    public final MultiTermVectorsResponse mtermvectors(MultiTermVectorsRequest request, RequestOptions options) throws
            IOException {
        return restHighLevelClient.mtermvectors(request, options);
    }


    /**
     * Asynchronously calls the Multi Term Vectors API
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/docs-multi-termvectors.html">Multi Term Vectors API
     * on elastic.co</a>
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable mtermvectorsAsync(MultiTermVectorsRequest request, RequestOptions options,
                                               ActionListener<MultiTermVectorsResponse> listener) {
        return restHighLevelClient.mtermvectorsAsync(request, options, listener);
    }


    /**
     * Executes a request using the Ranking Evaluation API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-rank-eval.html">Ranking Evaluation API
     * on elastic.co</a>
     *
     * @param rankEvalRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final RankEvalResponse rankEval(RankEvalRequest rankEvalRequest, RequestOptions options) throws
            IOException {
        return restHighLevelClient.rankEval(rankEvalRequest, options);
    }


    /**
     * Executes a request using the Multi Search Template API.
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/multi-search-template.html">Multi Search Template API
     * on elastic.co</a>.
     */
    public final MultiSearchTemplateResponse msearchTemplate(MultiSearchTemplateRequest multiSearchTemplateRequest,
                                                             RequestOptions options) throws IOException {
        return restHighLevelClient.msearchTemplate(multiSearchTemplateRequest, options);
    }

    /**
     * Asynchronously executes a request using the Multi Search Template API
     * <p>
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/multi-search-template.html">Multi Search Template API
     * on elastic.co</a>.
     *
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable msearchTemplateAsync(MultiSearchTemplateRequest multiSearchTemplateRequest,
                                                  RequestOptions options,
                                                  ActionListener<MultiSearchTemplateResponse> listener) {
        return restHighLevelClient.msearchTemplateAsync(multiSearchTemplateRequest, options, listener);
    }

    /**
     * Asynchronously executes a request using the Ranking Evaluation API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-rank-eval.html">Ranking Evaluation API
     * on elastic.co</a>
     *
     * @param rankEvalRequest the request
     * @param options         the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener        the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable rankEvalAsync(RankEvalRequest rankEvalRequest, RequestOptions options,
                                           ActionListener<RankEvalResponse> listener) {
        return restHighLevelClient.rankEvalAsync(rankEvalRequest, options, listener);
    }

    /**
     * Executes a request using the Field Capabilities API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-field-caps.html">Field Capabilities API
     * on elastic.co</a>.
     *
     * @param fieldCapabilitiesRequest the request
     * @param options                  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public final FieldCapabilitiesResponse fieldCaps(FieldCapabilitiesRequest fieldCapabilitiesRequest,
                                                     RequestOptions options) throws IOException {
        return restHighLevelClient.fieldCaps(fieldCapabilitiesRequest, options);
    }

    /**
     * Get stored script by id.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-scripting-using.html">
     * How to use scripts on elastic.co</a>
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public GetStoredScriptResponse getScript(GetStoredScriptRequest request, RequestOptions options) throws
            IOException {
        return restHighLevelClient.getScript(request, options);
    }

    /**
     * Asynchronously get stored script by id.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-scripting-using.html">
     * How to use scripts on elastic.co</a>
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable getScriptAsync(GetStoredScriptRequest request, RequestOptions options,
                                      ActionListener<GetStoredScriptResponse> listener) {
        return restHighLevelClient.getScriptAsync(request, options, listener);
    }

    /**
     * Delete stored script by id.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-scripting-using.html">
     * How to use scripts on elastic.co</a>
     *
     * @param request the request
     * @param options the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public AcknowledgedResponse deleteScript(DeleteStoredScriptRequest request, RequestOptions options) throws
            IOException {
        return restHighLevelClient.deleteScript(request, options);
    }

    /**
     * Asynchronously delete stored script by id.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-scripting-using.html">
     * How to use scripts on elastic.co</a>
     *
     * @param request  the request
     * @param options  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable deleteScriptAsync(DeleteStoredScriptRequest request, RequestOptions options,
                                         ActionListener<AcknowledgedResponse> listener) {
        return restHighLevelClient.deleteScriptAsync(request, options, listener);
    }

    /**
     * Puts an stored script using the Scripting API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-scripting-using.html"> Scripting API
     * on elastic.co</a>
     *
     * @param putStoredScriptRequest the request
     * @param options                the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @return the response
     */
    public AcknowledgedResponse putScript(PutStoredScriptRequest putStoredScriptRequest,
                                          RequestOptions options) throws IOException {
        return restHighLevelClient.putScript(putStoredScriptRequest, options);
    }

    /**
     * Asynchronously puts an stored script using the Scripting API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/modules-scripting-using.html"> Scripting API
     * on elastic.co</a>
     *
     * @param putStoredScriptRequest the request
     * @param options                the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener               the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public Cancellable putScriptAsync(PutStoredScriptRequest putStoredScriptRequest, RequestOptions options,
                                      ActionListener<AcknowledgedResponse> listener) {
        return restHighLevelClient.putScriptAsync(putStoredScriptRequest, options, listener);
    }

    /**
     * Asynchronously executes a request using the Field Capabilities API.
     * See <a href="https://www.elastic.co/guide/en/elasticsearch/reference/current/search-field-caps.html">Field Capabilities API
     * on elastic.co</a>.
     *
     * @param fieldCapabilitiesRequest the request
     * @param options                  the request options (e.g. headers), use {@link RequestOptions#DEFAULT} if nothing needs to be customized
     * @param listener                 the listener to be notified upon request completion
     * @return cancellable that may be used to cancel the request
     */
    public final Cancellable fieldCapsAsync(FieldCapabilitiesRequest fieldCapabilitiesRequest,
                                            RequestOptions options,
                                            ActionListener<FieldCapabilitiesResponse> listener) {
        return restHighLevelClient.fieldCapsAsync(fieldCapabilitiesRequest, options, listener);
    }

}
