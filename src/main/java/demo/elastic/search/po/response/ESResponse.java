/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.response;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

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

    public static ESResponse parse(String root) {
        ESResponse esResponse = JSONObject.parseObject(root, ESResponse.class);
        return esResponse;
    }
}