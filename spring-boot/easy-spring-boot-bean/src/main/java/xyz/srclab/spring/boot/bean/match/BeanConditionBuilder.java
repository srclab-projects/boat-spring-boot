package xyz.srclab.spring.boot.bean.match;

import xyz.srclab.spring.boot.common.collection.IterableHelper;

import java.lang.annotation.Annotation;
import java.util.*;

public class BeanConditionBuilder {

    public static BeanConditionBuilder newBuilder() {
        return new BeanConditionBuilder();
    }

    private Collection<Class<?>> includeTypes = new LinkedList<>();
    private Collection<Class<? extends Annotation>> includeAnnotations = new LinkedList<>();
    private Collection<String> includeNamePatterns = new LinkedList<>();
    private Collection<BeanExcludePredicate> excludeConditions = new LinkedList<>();

    public BeanConditionBuilder() {
    }

    public BeanConditionBuilder includeTypes(Class<?>... types) {
        return includeTypes(Arrays.asList(types));
    }

    public BeanConditionBuilder includeTypes(Iterable<Class<?>> types) {
        this.includeTypes.addAll(IterableHelper.castCollection(types));
        return this;
    }

    public BeanConditionBuilder includeAnnotations(Class<? extends Annotation>... annotations) {
        return includeAnnotations(Arrays.asList(annotations));
    }

    public BeanConditionBuilder includeAnnotations(Iterable<Class<? extends Annotation>> annotations) {
        this.includeAnnotations.addAll(IterableHelper.castCollection(annotations));
        return this;
    }

    public BeanConditionBuilder includeNamePattern(String... namePatterns) {
        return includeNamePattern(Arrays.asList(namePatterns));
    }

    public BeanConditionBuilder includeNamePattern(Iterable<String> namePatterns) {
        this.includeNamePatterns.addAll(IterableHelper.castCollection(namePatterns));
        return this;
    }

    public BeanConditionBuilder excludeConditions(BeanExcludePredicate... excludePredicates) {
        return excludeConditions(Arrays.asList(excludePredicates));
    }

    public BeanConditionBuilder excludeConditions(Iterable<BeanExcludePredicate> excludePredicates) {
        this.excludeConditions.addAll(IterableHelper.castCollection(excludePredicates));
        return this;
    }

    public BeanCondition build() {
        return new OfCommon(this);
    }

    static class OfAnd implements BeanCondition {

        private final List<BeanCondition> conditions = new LinkedList<>();

        OfAnd(BeanCondition first, BeanCondition second) {
            addCondition(first);
            addCondition(second);
        }

        @Override
        public BeanCondition and(BeanCondition beanCondition) {
            addCondition(beanCondition);
            return this;
        }

        public void addCondition(BeanCondition beanCondition) {
            this.conditions.add(beanCondition);
        }

        public List<BeanCondition> getConditions() {
            return conditions;
        }
    }

    static class OfOr implements BeanCondition {

        private final List<BeanCondition> conditions = new LinkedList<>();

        OfOr(BeanCondition first, BeanCondition second) {
            addCondition(first);
            addCondition(second);
        }

        @Override
        public BeanCondition or(BeanCondition beanCondition) {
            addCondition(beanCondition);
            return this;
        }

        public void addCondition(BeanCondition beanCondition) {
            this.conditions.add(beanCondition);
        }

        public List<BeanCondition> getConditions() {
            return conditions;
        }
    }

    static class OfNot implements BeanCondition {

        private final BeanCondition originalCondition;

        public OfNot(BeanCondition originalCondition) {
            this.originalCondition = originalCondition;
        }

        @Override
        public BeanCondition not() {
            return getOriginalCondition();
        }

        public BeanCondition getOriginalCondition() {
            return originalCondition;
        }
    }

    static class OfCommon implements BeanCondition {

        private final Collection<Class<?>> includeTypes;
        private final Collection<Class<? extends Annotation>> includeAnnotations;
        private final Collection<String> includeNamePatterns;
        private final Collection<BeanExcludePredicate> excludeConditions;

        private OfCommon(BeanConditionBuilder builder) {
            this.includeTypes = Collections.unmodifiableCollection(builder.includeTypes);
            this.includeAnnotations = Collections.unmodifiableCollection(builder.includeAnnotations);
            this.includeNamePatterns = Collections.unmodifiableCollection(builder.includeNamePatterns);
            this.excludeConditions = Collections.unmodifiableCollection(builder.excludeConditions);
            ;
        }

        public Collection<Class<?>> getIncludeTypes() {
            return includeTypes;
        }

        public Collection<Class<? extends Annotation>> getIncludeAnnotations() {
            return includeAnnotations;
        }

        public Collection<String> getIncludeNamePatterns() {
            return includeNamePatterns;
        }

        public Collection<BeanExcludePredicate> getExcludeConditions() {
            return excludeConditions;
        }
    }
}
