package demo.elastic.search.remove;

import com.alibaba.fastjson.JSON;
import demo.elastic.search.service.search.ESSearchService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermsSetQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class TermQueryTest {

    @Test
    public void termQuerySingle() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termQuery("F2_0088", "MONEYTREE, INC."));
        SearchResponse searchResponse = new ESSearchService().init("10.202.16.9", 9200, "http")
                .searchBaseDefaultOptions(searchSourceBuilder, "comstore_tb_object_0088_v2");
        log.info("searchResponse:{}", JSON.toJSON(searchResponse.getHits()));
    }

    /**
     * 多条件 AND 精确查询
     * 使用了 QueryBuilders.boolQuery() 来组合
     * <p>
     * <p>
     * 这个中，must的两个条件都必须满足，should中的两个条件至少满足一个就可以。
     *
     * @throws IOException
     */
    @Test
    public void termQueryMultiAnd() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("F2_0088", "MONEYTREE, INC."))
                .must(QueryBuilders.termQuery("F26_0088", "1222502541"));
        searchSourceBuilder.query(queryBuilder);
        SearchResponse searchResponse = new ESSearchService().init("10.202.16.9", 9200, "http")
                .searchBaseDefaultOptions(searchSourceBuilder, "comstore_tb_object_0088_v2");
        log.info("searchResponse:{}", JSON.toJSON(searchResponse.getHits()));
    }

    /**
     * 多条件 OR 精确查询
     * <p>
     * 这个中，must的两个条件都必须满足，should中的两个条件至少满足一个就可以。
     *
     * @throws IOException
     */
    @Test
    public void termQueryMultiOR() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("F2_0088", "MONEYTREE, INC."))
                .should(QueryBuilders.termQuery("F2_0088", "FIFTH QUARTER INC."));
        searchSourceBuilder.query(queryBuilder);
        SearchResponse searchResponse = new ESSearchService().init("10.202.16.9", 9200, "http")
                .searchBaseDefaultOptions(searchSourceBuilder, "comstore_tb_object_0088_v2");
        log.info("searchResponse:{}", JSON.toJSON(searchResponse.getHits()));
    }

    /**
     * 多条件 OR 精确查询
     * <p>
     * 这个中，must的两个条件都必须满足，should中的两个条件至少满足一个就可以。
     *
     * @throws IOException
     */
    @Test
    public void xxx() throws IOException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        TermsSetQueryBuilder termsSetQueryBuilder = new TermsSetQueryBuilder("F23_0088", Arrays.asList("111"));
        termsSetQueryBuilder.setMinimumShouldMatchField("10%");
        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .must(termsSetQueryBuilder);


        searchSourceBuilder.query(queryBuilder);
        SearchResponse searchResponse = new ESSearchService().init("10.202.16.9", 9200, "http")
                .searchBaseDefaultOptions(searchSourceBuilder, "comstore_tb_object_0088_v2");
        log.info("searchResponse:{}", JSON.toJSON(searchResponse.getHits()));
    }

}
