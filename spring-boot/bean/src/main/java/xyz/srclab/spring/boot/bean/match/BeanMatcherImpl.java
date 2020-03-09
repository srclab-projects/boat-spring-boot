package xyz.srclab.spring.boot.bean.match;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.lang.Nullable;
import org.springframework.util.AntPathMatcher;
import xyz.srclab.spring.boot.common.collection.MapHelper;

import java.lang.annotation.Annotation;
import java.util.*;

public class BeanMatcherImpl implements BeanMatcher {

    private final BeanCondition condition;

    public BeanMatcherImpl(BeanCondition condition) {
        this.condition = condition;
    }

    @Override
    public Map<String, Object> match(ApplicationContext applicationContext) {
        return matchCondition(condition, applicationContext, null);
    }

    private Map<String, Object> matchCondition(
            BeanCondition condition,
            ApplicationContext applicationContext,
            @Nullable Map<String, Object> matchContext
    ) {
        if (condition instanceof BeanConditionBuilder.OfAnd) {
            return matchAnd((BeanConditionBuilder.OfAnd) condition, applicationContext, matchContext);
        }
        if (condition instanceof BeanConditionBuilder.OfOr) {
            return matchOr((BeanConditionBuilder.OfOr) condition, applicationContext, matchContext);
        }
        if (condition instanceof BeanConditionBuilder.OfNot) {
            return matchNot((BeanConditionBuilder.OfNot) condition, applicationContext, matchContext);
        }
        if (condition instanceof BeanConditionBuilder.OfCommon) {
            return matchCommon((BeanConditionBuilder.OfCommon) condition, applicationContext, matchContext);
        }
        throw new IllegalArgumentException("Unknown BeanCondition type: " + condition);
    }

    private Map<String, Object> matchAnd(
            BeanConditionBuilder.OfAnd ofAnd,
            ApplicationContext applicationContext,
            @Nullable Map<String, Object> matchContext
    ) {
        List<BeanCondition> conditions = ofAnd.getConditions();
        Map<String, Object> result = matchContext;
        for (BeanCondition beanCondition : conditions) {
            result = matchCondition(beanCondition, applicationContext, result);
        }
        return result == null ? Collections.emptyMap() : result;
    }

    private Map<String, Object> matchOr(
            BeanConditionBuilder.OfOr ofOr,
            ApplicationContext applicationContext,
            @Nullable Map<String, Object> matchContext
    ) {
        List<BeanCondition> conditions = ofOr.getConditions();
        Map<String, Object> result = new HashMap<>();
        for (BeanCondition beanCondition : conditions) {
            Map<String, Object> r = matchCondition(beanCondition, applicationContext, matchContext);
            result.putAll(r);
        }
        return result;
    }

    private Map<String, Object> matchNot(
            BeanConditionBuilder.OfNot ofNot,
            ApplicationContext applicationContext,
            @Nullable Map<String, Object> matchContext
    ) {
        if (matchContext == null) {
            Map<String, Object> notBeans = matchCondition(ofNot.not(), applicationContext, null);
            Map<String, Object> allBeans = applicationContext.getBeansOfType(Object.class);
            Map<String, Object> result = new HashMap<>(allBeans);
            MapHelper.removeAll(result, notBeans.keySet());
            return result;
        } else {
            Map<String, Object> notBeans = matchCondition(ofNot.not(), applicationContext, matchContext);
            Map<String, Object> result = new HashMap<>(matchContext);
            MapHelper.removeAll(result, notBeans.keySet());
            return result;
        }
    }

    private Map<String, Object> matchCommon(
            BeanConditionBuilder.OfCommon ofCommon,
            ApplicationContext applicationContext,
            @Nullable Map<String, Object> matchContext
    ) {
        return matchContext == null ?
                matchCommonOfApplicationContext(ofCommon, applicationContext)
                :
                matchCommonOfMatchContext(ofCommon, applicationContext, matchContext);
    }

    private Map<String, Object> matchCommonOfApplicationContext(
            BeanConditionBuilder.OfCommon ofCommon,
            ApplicationContext applicationContext
    ) {
        Map<String, Object> result = new HashMap<>();
        Collection<Class<?>> types = ofCommon.getTypes();
        Collection<Class<? extends Annotation>> annotations = ofCommon.getAnnotations();
        Collection<String> namePatterns = ofCommon.getNamePatterns();
        if (!types.isEmpty()) {
            for (Class<?> type : types) {
                result.putAll(applicationContext.getBeansOfType(type));
            }
        }
        if (!annotations.isEmpty()) {
            for (Class<? extends Annotation> annotation : annotations) {
                result.putAll(applicationContext.getBeansWithAnnotation(annotation));
            }
        }
        if (!namePatterns.isEmpty()) {
            String[] names = applicationContext.getBeanDefinitionNames();
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String name : names) {
                for (String namePattern : namePatterns) {
                    if (antPathMatcher.match(namePattern, name)) {
                        result.put(name, applicationContext.getBean(name));
                        break;
                    }
                }
            }
        }
        return result;
    }

    private Map<String, Object> matchCommonOfMatchContext(
            BeanConditionBuilder.OfCommon ofCommon,
            ApplicationContext applicationContext,
            Map<String, Object> matchContext
    ) {
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> context = new HashMap<>(matchContext);
        Collection<Class<?>> types = ofCommon.getTypes();
        Collection<Class<? extends Annotation>> annotations = ofCommon.getAnnotations();
        Collection<String> namePatterns = ofCommon.getNamePatterns();
        if (!types.isEmpty()) {
            for (Class<?> type : types) {
                Iterator<Map.Entry<String, Object>> it = context.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> entry = it.next();
                    if (type.isAssignableFrom(entry.getValue().getClass())) {
                        result.put(entry.getKey(), entry.getValue());
                        it.remove();
                    }
                    if (context.isEmpty()) {
                        return result;
                    }
                }
            }
        }
        if (!annotations.isEmpty()) {
            for (Class<? extends Annotation> annotation : annotations) {
                Iterator<Map.Entry<String, Object>> it = context.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry<String, Object> entry = it.next();
                    Annotation findResult = applicationContext.findAnnotationOnBean(entry.getKey(), annotation);
                    if (findResult != null) {
                        result.put(entry.getKey(), entry.getValue());
                        it.remove();
                    }
                    if (context.isEmpty()) {
                        return result;
                    }
                }
            }
        }
        if (!namePatterns.isEmpty()) {
            String[] names = context.keySet().toArray(ArrayUtils.EMPTY_STRING_ARRAY);
            AntPathMatcher antPathMatcher = new AntPathMatcher();
            for (String name : names) {
                for (String namePattern : namePatterns) {
                    if (antPathMatcher.match(namePattern, name)) {
                        result.put(name, applicationContext.getBean(name));
                        context.remove(name);
                        if (context.isEmpty()) {
                            return result;
                        }
                        break;
                    }
                }
            }
        }
        return result;
    }
}
