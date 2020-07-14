/**
 * Copyright 2020 bejson.com
 */
package demo.elastic.search.po.response.buckets;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class Buckets {
    @JSONField(name = "doc_count")
    private int doc_count;
    @JSONField(name = "key")
    private String key;
}