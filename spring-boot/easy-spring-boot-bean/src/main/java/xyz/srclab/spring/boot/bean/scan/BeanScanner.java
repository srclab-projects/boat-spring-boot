package xyz.srclab.spring.boot.bean.scan;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import xyz.srclab.spring.boot.common.collection.IterableHelper;

import java.util.Set;

public interface BeanScanner {

    static BeanScannerBuilder newBuilder() {
        return BeanScannerBuilder.newBuilder();
    }

    Set<BeanDefinitionHolder> scan(String... basePackages);

    default Set<BeanDefinitionHolder> scan(Iterable<String> basePackages) {
        return scan(IterableHelper.castCollection(basePackages).toArray(new String[0]));
    }
}
