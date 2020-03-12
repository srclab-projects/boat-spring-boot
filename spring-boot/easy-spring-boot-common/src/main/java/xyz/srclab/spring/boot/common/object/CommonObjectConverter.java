package xyz.srclab.spring.boot.common.object;

import org.apache.commons.beanutils.ConvertUtilsBean;

public class CommonObjectConverter implements ObjectConverter {

    public static final CommonObjectConverter INSTANCE = new CommonObjectConverter();

    private final ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean();

    @Override
    public boolean supportConvert(Object object, Class<?> type) {
        return true;
    }

    @Override
    public Object convert(Object object, Class<?> type) {
        return convertUtilsBean.convert(object, type);
    }
}
