package demo.elastic.search.util;

import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

public class DateUtil {
    public static String yyyyMMdd_HHmmss = "yyyyMMdd_HHmmss";

    public static String getNow() {
        return FastDateFormat.getInstance(yyyyMMdd_HHmmss).format(new Date());
    }
}
