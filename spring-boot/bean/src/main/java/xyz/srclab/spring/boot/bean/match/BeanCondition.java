package xyz.srclab.spring.boot.bean.match;

public interface BeanCondition {

    static BeanConditionBuilder newBuilder() {
        return BeanConditionBuilder.newBuilder();
    }

    default BeanMatcher matcher() {
        return new BeanMatcherImpl(this);
    }

    default BeanCondition and(BeanCondition beanCondition) {
        return new BeanConditionBuilder.OfAnd(this, beanCondition);
    }

    default BeanCondition or(BeanCondition beanCondition) {
        return new BeanConditionBuilder.OfOr(this, beanCondition);
    }

    default BeanCondition not() {
        return new BeanConditionBuilder.OfNot(this);
    }
}
