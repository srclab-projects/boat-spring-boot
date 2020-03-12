package xyz.srclab.spring.boot.common.object;

public interface ObjectConverter {

    static ObjectConverterBuilder newBuilder() {
        return ObjectConverterBuilder.newBuilder();
    }

    boolean supportConvert(Object object, Class<?> type);

    Object convert(Object object, Class<?> type);
}
