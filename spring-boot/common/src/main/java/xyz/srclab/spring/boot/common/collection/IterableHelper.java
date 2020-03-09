package xyz.srclab.spring.boot.common.collection;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.SetUtils;

import java.util.Collection;
import java.util.Set;

public class IterableHelper {

    public static <E> Collection<E> castCollection(Iterable<E> iterable) {
        return iterable instanceof Collection ?
                (Collection<E>) iterable : IterableUtils.toList(iterable);
    }
}
