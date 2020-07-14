package demo.elastic.search.vo;

import demo.elastic.search.po.Body;
import lombok.Data;

import java.util.List;

@Data
public class SearchTermsRequest {
    private Body body;
    private List<String> values;
}
