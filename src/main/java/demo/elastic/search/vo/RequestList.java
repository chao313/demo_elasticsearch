package demo.elastic.search.vo;

import java.util.ArrayList;

public class RequestList<T> extends ArrayList<T> {
    public static final Integer initialCapacity = 1000000;
    public RequestList() {
        super(initialCapacity);
    }
}
