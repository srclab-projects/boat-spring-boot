package xyz.srclab.spring.boot.test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface BeanProcessMarker {

    Map<Object, Map<Object, Object>> markers = new ConcurrentHashMap<>();

    default void mark(Object key) {
        markers.computeIfAbsent(this, k -> new HashMap<>()).put(key, generateMark(key));
    }

    default void mark(Object key, Object value) {
        markers.computeIfAbsent(this, k -> new HashMap<>()).put(key, value);
    }

    default Object getActualMark(Object key) {
        return markers.computeIfAbsent(this, k -> new HashMap<>()).get(key);
    }

    default Object generateMark(Object key) {
        return "default - " + getClass();
    }

    default void printMarkInfo() {
        System.out.println(markers.computeIfAbsent(this, k -> new HashMap<>()));
    }
}
