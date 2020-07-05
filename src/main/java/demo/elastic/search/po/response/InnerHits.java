package demo.elastic.search.po.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Map;


@Data
public class InnerHits {
    private String _index;
    private String _type;
    @JSONField(name = "_source")
    private Map<String, Object> _source;
    private String _id;
    private int _score;
}