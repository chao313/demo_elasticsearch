//package demo.elastic.search.high;
//
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//public class xx {
//
//    /**
//     * <a href="https://blog.csdn.net/qq_26676207/article/details/81019677?utm_medium=distribute.pc_aggpage_search_result.none-task-blog-2~all~sobaiduend~default-5-81019677.nonecase&utm_term=es%20%E6%9F%A5%E7%9C%8B%E5%AE%A2%E6%88%B7%E7%AB%AF&spm=1000.2123.3001.4430"></>
//     */
//    public void xx() {
//
//        SearchRequest searchRequest = new SearchRequest("bank");
//        searchRequest.types("_doc");
//
//        // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
//        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//
//        //构造QueryBuilder
//            /*QueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("user", "kimchy")
//                    .fuzziness(Fuzziness.AUTO)
//                    .prefixLength(3)
//                    .maxExpansions(10);
//            sourceBuilder.query(matchQueryBuilder);*/
//
//        sourceBuilder.query(QueryBuilders.termQuery("age", 24));
//
//        QueryBuilders.boolQuery().must(QueryBuilders.termQuery("age", 24)).minimumShouldMatch()
//    }
//}
