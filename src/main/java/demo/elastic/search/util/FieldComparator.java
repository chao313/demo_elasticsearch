package demo.elastic.search.util;

import java.util.Comparator;

/**
 * 通用字段排序
 */
public class FieldComparator implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Integer key1 = null;
        if (o1.toString().matches("F\\d{1,}_\\d{1,}")) {
            key1 = Integer.valueOf(o1.toString().replace("F", "").replace("_", ""));
        }
        Integer key2 = null;
        if (o2.toString().matches("F\\d{1,}_\\d{1,}")) {
            key2 = Integer.valueOf(o2.toString().replace("F", "").replace("_", ""));
        }
        if (key1 != null && key2 != null) {
            return key1.compareTo(key2);
        } else {
            return o1.toString().compareTo(o2.toString());
        }
    }
}
