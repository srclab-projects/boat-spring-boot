package xyz.srclab.spring.boot.common.collection;

import java.util.Collection;
import java.util.Map;

public class MapHelper {

    public static <K, V> void removeAll(Map<K, V> map, Collection<K> keys) {
        for (K key : keys) {
            map.remove(key);
        }
    }
}
