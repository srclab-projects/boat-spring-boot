package xyz.srclab.spring.boot.bean.match;

import java.lang.annotation.Annotation;

public interface BeanCondition {

    static BeanConditionBuilder includeTypes(Class<?>... types) {
        return BeanConditionBuilder.newBuilder().includeTypes(types);
    }

    static BeanConditionBuilder includeTypes(Iterable<Class<?>> types) {
        return BeanConditionBuilder.newBuilder().includeTypes(types);
    }

    @SafeVarargs
    static BeanConditionBuilder includeAnnotations(Class<? extends Annotation>... annotations) {
        return BeanConditionBuilder.newBuilder().includeAnnotations(annotations);
    }

    static BeanConditionBuilder includeAnnotations(Iterable<Class<? extends Annotation>> annotations) {
        return BeanConditionBuilder.newBuilder().includeAnnotations(annotations);
    }

    static BeanConditionBuilder includeNamePattern(String... namePatterns) {
        return BeanConditionBuilder.newBuilder().includeNamePattern(namePatterns);
    }

    static BeanConditionBuilder includeNamePattern(Iterable<String> namePatterns) {
        return BeanConditionBuilder.newBuilder().includeNamePattern(namePatterns);
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
