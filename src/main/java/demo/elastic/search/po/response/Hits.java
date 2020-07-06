package demo.elastic.search.po.response;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;


@Data
public class Hits {
    private Integer total;
    @JSONField(name = "max_score")
    private int maxScore;
    @JSONField(name = "hits")
    private List<InnerHits> hits;

}