package demo.elastic.search.vo;

import demo.elastic.search.out.remove.compound.Body;
import lombok.Data;

import java.util.List;

@Data
public class SearchTermsRequest {
    private Body body;
    private List<String> values;
}
