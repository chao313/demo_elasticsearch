package demo.elastic.search.po.request.aggs.bucket;


import com.fasterxml.jackson.annotation.JsonInclude;
import demo.elastic.search.po.request.Aggs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 直方图聚合
 *
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/search-aggregations-bucket-histogram-aggregation.html"></a>
 *
 * <pre>
 *  {
 *   "aggs": {
 *     "prices": {
 *       "histogram": {
 *         "field": "price",
 *         "interval": 50
 *       }
 *     }
 *   }
 * }
 * </pre>
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BucketHistogramAggs implements Aggs {

    private Map<String, HistogramBucketAggsMiddle> aggs = new HashMap<>();


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class HistogramBucketAggsMiddle {
        private HistogramBucketAggsParam histogram;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class HistogramBucketAggsParam {
        private String field;//字段名称
        private Integer interval;//间隔
        private Integer min_doc_count;//最小文件数
        private Boolean keyed;//key转换
        private Integer missing;//当key没有值是,指定值(用于补全) 默认是忽略
        private HistogramBucketAggsParamExtended_bounds extended_bounds;//指定直方图的开头和结尾
//        private Map<String, String> order;//排序 "order": { "_count": "asc" } 暂时略过
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class HistogramBucketAggsParamExtended_bounds {
        private Integer min;//最小
        private Integer max;//最大
    }


}