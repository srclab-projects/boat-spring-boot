package xyz.srclab.spring.boot.common.base;

public class Ref<T> {

    public static <T> Ref<T> create() {
        return new Ref<>();
    }

    public static <T> Ref<T> of(T object) {
        return new Ref<>(object);
    }

    private T object;

    public Ref() {
    }

    public Ref(T object) {
        this.object = object;
    }

    public T get() {
        return object;
    }

    public void set(T object) {
        this.object = object;
    }
}
