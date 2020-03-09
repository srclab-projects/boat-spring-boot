package xyz.srclab.spring.boot.bean.test.scan

import org.springframework.beans.factory.config.BeanDefinitionHolder
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.type.classreading.MetadataReader
import org.springframework.core.type.classreading.MetadataReaderFactory
import org.springframework.core.type.filter.TypeFilter
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import other.scan.test.SomeClass
import other.scan.test.SomeComponent
import other.scan.test.SomeInterface
import xyz.srclab.spring.boot.bean.scan.BeanScanner
import xyz.srclab.spring.boot.bean.test.config.Config

@SpringBootTest(classes = [Config::class])
class ScanTest : AbstractTestNGSpringContextTests() {

    @Test
    fun testBeanScanner() {
        val scanner1 = BeanScanner.newBuilder().includeAll().build()
        val names1 = holdersToClassNames(scanner1.scan("other.scan.test"))
        println("names1: $names1")
        Assert.assertEquals(
            names1, setOf(
                SomeComponent::class.java.name, SomeClass::class.java.name, SomeInterface::class.java.name
            )
        )

        val scanner2 = BeanScanner.newBuilder().useDefaultFilters(true).build()
        val names2 = holdersToClassNames(scanner2.scan("other.scan.test"))
        println("names2: $names2")
        Assert.assertEquals(
            names2, setOf(
                SomeComponent::class.java.name
            )
        )

        val scanner3 = BeanScanner.newBuilder().includeAll()
            .addExcludeFilter(TypeFilter { metadataReader: MetadataReader, metadataReaderFactory: MetadataReaderFactory ->
                metadataReader.classMetadata.className == SomeClass::class.java.name
            })
            .build()
        val names3 = holdersToClassNames(scanner3.scan("other.scan.test"))
        println("names3: $names3")
        Assert.assertEquals(
            names3, setOf(
                SomeComponent::class.java.name, SomeInterface::class.java.name
            )
        )

        val scanner4 = BeanScanner.newBuilder().includeAll()
            .addExcludeFilters(TypeFilter { metadataReader: MetadataReader, metadataReaderFactory: MetadataReaderFactory ->
                metadataReader.classMetadata.className == SomeClass::class.java.name
            }, TypeFilter { metadataReader: MetadataReader, metadataReaderFactory: MetadataReaderFactory ->
                metadataReader.classMetadata.className == SomeComponent::class.java.name
            })
            .build()
        val names4 = holdersToClassNames(scanner4.scan("other.scan.test"))
        println("names4: $names4")
        Assert.assertEquals(
            names4, setOf(
                SomeInterface::class.java.name
            )
        )
    }

    private fun holdersToClassNames(holders: Collection<BeanDefinitionHolder>): Set<String> {
        val names = mutableSetOf<String>()
        holders.forEach() { holder ->
            names.add(holder.beanDefinition.beanClassName ?: "null")
        }
        return names
    }
}