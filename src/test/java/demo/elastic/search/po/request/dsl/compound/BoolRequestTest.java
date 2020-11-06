package demo.elastic.search.po.request.dsl.compound;

import demo.elastic.search.feign.SearchService;
import demo.elastic.search.po.request.QueryBuilders;
import demo.elastic.search.po.request.SearchSourceBuilder;
import demo.elastic.search.po.request.aggs.VoidAggs;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class BoolRequestTest {

    @Resource
    SearchService searchService;

//    /**
//     * <pre>
//     * {
//     *                 "_index":"tb_object_0088_v2",
//     *                 "_type":"tb_object_0088",
//     *                 "_id":"{6CE55C9D-F8CB-4A1E-9EC1-A5D0BF8D7897}",
//     *                 "_score":17.231459,
//     *                 "_source":{
//     *                     "F24_0088":"百度地图(116.19889859806655,39.94587074231478);",
//     *                     "F21_0088":"总局",
//     *                     "F6_0088":"110000450211646",
//     *                     "F9_0088":"53361C9AB879D58C2934602502A6FE79",
//     *                     "F10_0088":"30000",
//     *                     "F19_0088":"开业",
//     *                     "F13_0088":"北京市石景山区实兴大街30号院3号楼2层B-0035房间",
//     *                     "F16_0088":"研发、设计计算机软硬件、网络技术、通讯技术及产品；）",
//     *                     "F15_0088":"20320724",
//     *                     "F18_0088":"20200529",
//     *                     "F23_0088":"0303010105",
//     *                     "F3_0088":"20160615",
//     *                     "F12_0088":"20120725",
//     *                     "F2_0088":"北京字节跳动网络技术有限公司",
//     *                     "F5_0088":"91110107599635562F",
//     *                     "F14_0088":"20120725",
//     *                     "SEQID":"10673181",
//     *                     "F11_0088":"美元",
//     *                     "F7_0088":"有限责任公司(台港澳法人独资)",
//     *                     "F4_0088":"20200613",
//     *                     "F22_0088":"WqM1CKMf2r",
//     *                     "F1_0088":"1006851239",
//     *                     "OB_OBJECT_ID":"{6CE55C9D-F8CB-4A1E-9EC1-A5D0BF8D7897}",
//     *                     "F8_0088":"张一鸣",
//     *                     "F17_0088":"北京市工商行政管理局石景山分局",
//     *                     "F25_0088":"298010000",
//     *                     "RP_GEN_DATETIME":"2020-06-13 08:10:47"
//     *                 }
//     * }
//     * </pre>
//     * 测试must
//     */
//    @Test
//    public void testBoolMustRequest() {
//        BoolRequest request = new BoolRequest();
//        request.getQuery().getBool().getMust().add(ExistsRequest.builderQuery("F8_0088"));
//        request.getQuery().getBool().getMust().add(FuzzyRequest.builderQuery("F11_0088", "美元"));
//        request.getQuery().getBool().getMust().add(IDsRequest.builderQuery(Arrays.asList("{6CE55C9D-F8CB-4A1E-9EC1-A5D0BF8D7897}")));
//        request.getQuery().getBool().getMust().add(PrefixRequest.builderQuery("F8_0088", "张一"));
//        request.getQuery().getBool().getMust().add(RangeRequest.builderQuery("F8_0088", "张一鸣", "张一鸣"));
//        request.getQuery().getBool().getMust().add(RegexpRequest.builderQuery("F8_0088", "张一鸣"));
//        request.getQuery().getBool().getMust().add(TermRequest.builderQuery("F2_0088", "北京字节跳动网络技术有限公司"));
//        request.getQuery().getBool().getMust().add(TermsRequest.builderQuery("F8_0088", Arrays.asList("张一鸣")));
//        request.getQuery().getBool().getMust().add(WildcardRequest.builderQuery("F8_0088", "张?鸣"));
//        log.info("请求body:{}", request.getRequestBody());
//        String response = searchService.DSL_search_bool("tb_object_0088", request);
//        log.info("response:{}", response);
//    }

//    /**
//     * 测试must和must_not
//     */
//    @Test
//    public void testBoolMustAndMustNotRequest() {
//        BoolRequest request = new BoolRequest();
////        request.getQuery().getBool().getMust().add(RegexpRequest.builderQuery("F8_0088", "张一鸣"));
////        request.getQuery().getBool().getMust_not().add(RegexpRequest.builderQuery("F2_0088", "北京字节跳动网络技术有限公司"));
//        request.getQuery().getBool().getShould().add(RegexpRequest.builderQuery("F2_0088", "上海字节跳动网络技术有限公司"));
//        request.getQuery().getBool().getShould().add(IDsRequest.builderQuery(Arrays.asList("{6CE55C9D-F8CB-4A1E-9EC1-A5D0BF8D7897}")));
//        log.info("请求body:{}", request.getRequestBody());
//        String response = searchService.DSL_search_bool("tb_object_0088", request);
//        log.info("response:{}", response);
//    }

    /**
     * 测试 must
     */
    @Test
    public void testQueryBuilderBoolMusts() {

        BoolQuery boolQuery = QueryBuilders.boolQuery()
                .must(QueryBuilders.existsQuery("age"))
                .must(QueryBuilders.termQuery("age", "35"));

        SearchSourceBuilder<BoolQuery,VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(boolQuery);

        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_bool("index_bulk", request);
        log.info("response:{}", response);
    }

    /**
     * 测试 filter
     */
    @Test
    public void testQueryBuilderBoolFilter() {

        BoolQuery boolQuery = QueryBuilders.boolQuery()
                .filter(QueryBuilders.existsQuery("age"))
                .filter(QueryBuilders.termQuery("age", "35"));

        SearchSourceBuilder<BoolQuery,VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(boolQuery);


        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_bool("index_bulk", request);
        log.info("response:{}", response);
    }

    /**
     * 测试 must_not
     */
    @Test
    public void testQueryBuilderBoolMustNot() {


        BoolQuery boolQuery = QueryBuilders.boolQuery()
                .must_not(QueryBuilders.termQuery("age", "35"))
                .must_not(QueryBuilders.termQuery("age", "36"))
                .must_not(QueryBuilders.termQuery("age", "33"))
                .must_not(QueryBuilders.termQuery("gender", "f"));//索引中是小写

        SearchSourceBuilder<BoolQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(boolQuery);

        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_bool("index_bulk", request);
        log.info("response:{}", response);
    }

    /**
     * 测试 should
     */
    @Test
    public void testQueryBuilderBoolShould() {
        BoolQuery boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("age", "1"))
                .should(QueryBuilders.termQuery("age", "2"));

        SearchSourceBuilder<BoolQuery,VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(boolQuery);

        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_bool("index_bulk", request);
        log.info("response:{}", response);
    }

    /**
     * 测试 should + minimumShouldMatch
     */
    @Test
    public void testQueryBuilderBoolMustAndShould() {
        BoolQuery boolQuery = QueryBuilders.boolQuery()
                .should(QueryBuilders.termQuery("age", "1"))
                .should(QueryBuilders.termQuery("age", "2"))
                .must(QueryBuilders.termQuery("gender", "m"))
                .minimumShouldMatch(1);

        SearchSourceBuilder<BoolQuery,VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(boolQuery);

        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_bool("index_bulk", request);
        log.info("response:{}", response);
    }

    /**
     * 测试 should + minimumShouldMatch(复杂)
     */
    @Test
    public void testQueryBuilderBoolMustAndShouldMore() {
        BoolQuery boolRequest1 = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("age", "1"));
        BoolQuery boolRequest2 = QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("age", "2"));

        BoolQuery boolQuery = QueryBuilders.boolQuery().should(boolRequest1).should(boolRequest2).minimumShouldMatch(1);

        SearchSourceBuilder<BoolQuery,VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(boolQuery);

        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_bool("index_bulk", request);
        log.info("response:{}", response);
    }


}
