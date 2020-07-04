/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.response;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ESResponse {
    private int took;
    private boolean timed_out;
    @JSONField(name = "_shards")
    private Shards _shards;
    @JSONField(name = "hits")
    private Hits hits;

    public static ESResponse parse(String root) {
        ESResponse esResponse = JSONObject.parseObject(root, ESResponse.class);
        return esResponse;
    }
}