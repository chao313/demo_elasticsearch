package demo.elastic.search.po.helper;

import lombok.Data;

import java.util.List;

@Data
public class DSLHelperPlus {
    private String index;
    private List<String> fields;
    private DSLHelper dslHelper;
}
