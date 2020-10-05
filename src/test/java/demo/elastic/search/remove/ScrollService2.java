package demo.elastic.search.remove;

import demo.elastic.search.service.search.ESSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
public class ScrollService2 {


    /**
     * 最大只能是10000 和设置相关
     *
     * @param indices      查询的es index
     * @param queryBuilder 查询构造器
     * @param size         滚动查询每次的大小
     * @param seconds      设置查询有效时间
     * @param hostname     10.202.16.9
     * @param port         9200
     * @param scheme       http
     * @param consumer
     * @throws IOException
     */
    public void ScrollIdSearch(
            List<String> indices,
            QueryBuilder queryBuilder,
            int size,
            long seconds,
            String hostname,
            int port,
            String scheme,
            Consumer<SearchHit[]> consumer

    ) throws IOException {
        SearchRequest searchRequest = new SearchRequest(indices.toArray(new String[indices.size()]));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.size(size); //创建搜索请求及其对应的搜索源生成器。还可以选择设置大小来控制一次检索多少个结果。
        searchSourceBuilder.query(queryBuilder);//具体的查询条件
        searchRequest.source(searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);
        searchRequest.scroll(TimeValue.timeValueSeconds(seconds)); //设置查询结果的保存时间
        ESSearchService esSearchService = new ESSearchService().init(hostname, port, scheme);
        SearchResponse searchResponse = esSearchService.searchBase(RequestOptions.DEFAULT, searchRequest);
        consumer.accept(searchResponse.getHits().getHits());
        SearchResponse searchScrollResponse = null;
        do {
            SearchScrollRequest scrollRequest = new SearchScrollRequest(searchResponse.getScrollId()); //通过设置所需的scroll id和scroll间隔来创建搜索scroll请求
            scrollRequest.scroll(TimeValue.timeValueSeconds(30));
            searchScrollResponse = esSearchService.client.scroll(scrollRequest, RequestOptions.DEFAULT);
            consumer.accept(searchScrollResponse.getHits().getHits());
        } while (searchScrollResponse.getHits().getHits().length > 0);

    }

}
