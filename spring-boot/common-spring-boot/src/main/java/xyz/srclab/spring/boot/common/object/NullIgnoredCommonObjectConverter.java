package xyz.srclab.spring.boot.common.object;

public class NullIgnoredCommonObjectConverter extends CommonObjectConverter {

    public static final NullIgnoredCommonObjectConverter INSTANCE = new NullIgnoredCommonObjectConverter();

    @Override
    public boolean supportConvert(Object object, Class<?> type) {
        return super.supportConvert(object, type);
    }

    @Override
    public Object convert(Object object, Class<?> type) {
        if (object == null) {
            return ObjectConverter.NO_CONVERSION;
        }
        return super.convert(object, type);
    }
}
