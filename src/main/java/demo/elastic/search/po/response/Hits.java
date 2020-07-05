package demo.elastic.search.po.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;


@Data
public class Hits {

    @JSONField(serialize = false)
    private Integer total;
    private int max_score;
    @JSONField(name = "hits")
    private List<InnerHits> hits;

}