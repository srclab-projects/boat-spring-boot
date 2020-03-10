package xyz.srclab.spring.boot.common.base;

import xyz.srclab.spring.boot.common.time.TimeHelper;

import java.util.function.Supplier;

/**
 * Caches a computed value, particularly in cases the value is optional and has a heavy process to get.
 *
 * @param <T> computed value
 */
public class Computed<T> implements Supplier<T> {

    private final Supplier<T> supplier;
    private final long timeout;

    private long lastComputedTime = 0;
    private T cache;

    public Computed(Supplier<T> supplier) {
        this(0, supplier);
    }

    public Computed(long timeout, Supplier<T> supplier) {
        this.timeout = timeout;
        this.supplier = supplier;
    }

    @Override
    public T get() {
        if (timeout <= 0) {
            return lastComputedTime > 0 ? cache : refreshAndGet();
        }
        long now = TimeHelper.nowMillis();
        return (lastComputedTime > 0 && lastComputedTime + timeout >= now) ? cache : refreshAndGet();
    }

    public T refreshAndGet() {
        refresh();
        return cache;
    }

    public void refresh() {
        this.cache = supplier.get();
        this.lastComputedTime = TimeHelper.nowMillis();
    }
}
