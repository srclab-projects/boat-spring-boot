package xyz.srclab.spring.boot.common.object;

public interface ObjectConverter {

    Object NO_CONVERSION = new Object();

    static ObjectConverterBuilder newBuilder() {
        return ObjectConverterBuilder.newBuilder();
    }

    boolean supportConvert(Object object, Class<?> type);

    Object convert(Object object, Class<?> type);
}
