package xyz.srclab.spring.boot.common.bean;

public class BeanHelper {

    private static final BeanOperator beanOperator = CommonBeanOperator.INSTANCE;

    private static final BeanOperator beanOperatorIgnoreNull = BeanOperator.newBuilder()
            .setCopyPropertyStrategy(CommonBeanOperatorStrategy.CopyPropertyIgnoreNull.INSTANCE)
            .build();

    public static void copyProperties(Object source, Object dest) {
        beanOperator.copyProperties(source, dest);
    }

    public static void copyPropertiesIgnoreNull(Object source, Object dest) {
        beanOperatorIgnoreNull.copyProperties(source, dest);
    }
}
