package demo.elastic.search.po.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;


@Data
public class InnerHits {
    @JSONField(name = "_index")
    private String index;
    @JSONField(name = "_type")
    private String type;
    @JSONField(name = "_source")
    private Map<String, Object> source;
    @JSONField(name = "id")
    private String _id;
    @JSONField(name = "score")
    private int _score;
}