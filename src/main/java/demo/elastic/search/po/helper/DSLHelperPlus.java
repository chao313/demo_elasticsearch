package demo.elastic.search.po.helper;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class DSLHelperPlus {
    private String index;
    private List<String> fields;
    private DSLHelper dslHelper;
    private List<Map<String, String>> sort = new ArrayList<>();//存放排序
}
