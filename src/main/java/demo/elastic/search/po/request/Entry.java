package demo.elastic.search.po.request;

import java.util.Map;

/**
 * 辅助存放k,v
 *
 * @param <K>
 * @param <V>
 */
public class Entry<K, V> implements Map.Entry<K, V> {

    private K key;

    private V value;

    public void setKey(K key) {
        this.key = key;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public V setValue(V newValue) {
        V oldValue = value;
        value = newValue;
        return oldValue;
    }

}