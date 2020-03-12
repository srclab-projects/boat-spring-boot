package xyz.srclab.spring.boot.common.collection;

import org.apache.commons.collections4.IterableUtils;

import java.util.Collection;

public class IterableHelper {

    public static <E> Collection<E> castCollection(Iterable<E> iterable) {
        return iterable instanceof Collection ?
                (Collection<E>) iterable : IterableUtils.toList(iterable);
    }
}
