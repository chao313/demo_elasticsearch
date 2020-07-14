/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.response;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import demo.elastic.search.po.response.buckets.BucketsRoot;
import lombok.Data;
import org.elasticsearch.search.aggregations.Aggregation;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ESResponse {
    private int took;
    @JSONField(name = "timed_out")
    private boolean timedOut;
    @JSONField(name = "_shards")
    private Shards shards;
    @JSONField(name = "hits")
    private Hits hits;
    @JSONField(name = "_scroll_id")
    private String scrollId;
    /**
     * 聚合 这里使用map来集合
     */
    @JSONField(name = "aggregations")
    private Map<String, BucketsRoot> aggregations;

    public static ESResponse parse(String root) {
        ESResponse esResponse = JSONObject.parseObject(root, ESResponse.class);
        return esResponse;
    }
}