//package demo.elasticSearch.service.search.test;
//
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.Avg;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//import java.io.IOException;
//
//public class xxx {
//    public void aggregation(){
//        RestHighLevelClient client = elasticClient.getRestHighLevelClient();
//        SearchRequest searchRequest = new SearchRequest();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        TermsAggregationBuilder aggregation = AggregationBuilders.terms("by_sex")
//                .field("sex.keyword");   //text类型不能用于索引或排序，必须转成keyword类型
//        aggregation.subAggregation(AggregationBuilders.avg("avg_age")
//                .field("age"));  //avg_age 为子聚合名称，名称可随意
//        searchSourceBuilder.aggregation(aggregation);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse searchResponse = null;
//        try {
//            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Aggregations aggregations = searchResponse.getAggregations();
//        Terms byCompanyAggregation = aggregations.get("by_sex");
//        Terms.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("女性");
//        Avg averageAge = elasticBucket.getAggregations().get("avg_age");
//        double avg = averageAge.getValue();
//        System.out.println("女性平均年龄："+avg);
//    }
//}
