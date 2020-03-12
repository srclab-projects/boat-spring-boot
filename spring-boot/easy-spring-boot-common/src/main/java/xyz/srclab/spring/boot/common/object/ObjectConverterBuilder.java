package xyz.srclab.spring.boot.common.object;

import xyz.srclab.spring.boot.common.base.StringHelper;

import java.util.Arrays;
import java.util.LinkedList;

public class ObjectConverterBuilder {

    public static ObjectConverterBuilder newBuilder() {
        return new ObjectConverterBuilder();
    }

    private final LinkedList<ObjectConverter> converters = new LinkedList<>();

    public ObjectConverterBuilder() {
    }

    public ObjectConverterBuilder addConverter(ObjectConverter converter) {
        converters.addFirst(converter);
        return this;
    }

    public ObjectConverterBuilder addConverters(ObjectConverter... converters) {
        return addConverters(Arrays.asList(converters));
    }

    public ObjectConverterBuilder addConverters(Iterable<ObjectConverter> converters) {
        for (ObjectConverter converter : converters) {
            this.converters.addFirst(converter);
        }
        return this;
    }

    public ObjectConverter build() {
        return new ObjectConverterImpl(converters.toArray(new ObjectConverter[0]));
    }

    private static class ObjectConverterImpl implements ObjectConverter {

        private final ObjectConverter[] converters;

        private ObjectConverterImpl(ObjectConverter[] converters) {
            this.converters = converters;
        }

        @Override
        public boolean supportConvert(Object object, Class<?> type) {
            for (ObjectConverter converter : converters) {
                if (converter.supportConvert(object, type)) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public Object convert(Object object, Class<?> type) {
            for (ObjectConverter converter : converters) {
                if (converter.supportConvert(object, type)) {
                    return converter.convert(object, type);
                }
            }
            throw new UnsupportedOperationException(
                    StringHelper.fastFormat("Cannot convert object: {} to type: {}", object, type));
        }
    }
}
