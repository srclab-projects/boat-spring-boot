package xyz.srclab.spring.boot.bean.test.match

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.bean.match.BeanCondition
import xyz.srclab.spring.boot.bean.match.BeanExcludePredicate
import xyz.srclab.spring.boot.bean.test.component.*
import xyz.srclab.spring.boot.bean.test.config.Config
import javax.annotation.Resource

@SpringBootTest(classes = [Config::class])
class MatchTest : AbstractTestNGSpringContextTests() {

    @Autowired
    private lateinit var context: ApplicationContext

    @Resource
    private lateinit var testService1: TestService1

    @Resource
    private lateinit var testService2: TestService2

    @Resource
    private lateinit var testService3: TestService3

    @Resource
    private lateinit var testService4: TestService4

    @Resource
    private lateinit var testService5: TestService5

    @Test
    fun testMatch() {
        val condition1 = BeanCondition
            .includeTypes(
                TestService1::class.java,
                TestService2::class.java,
                TestService3::class.java,
                TestService4::class.java,
                TestService5::class.java
            )
            .excludeConditions(BeanExcludePredicate { _, bean ->
                TestService4::class.java == bean.javaClass || TestService5::class.java == bean.javaClass
            })
            .build()
        val match1 = condition1.matcher().match(context)
        println("match1: $match1")
        Assert.assertEqualsDeep(
            match1, mapOf(
                "testService1" to testService1,
                "testService2" to testService2,
                "testService3" to testService3
            )
        )

        val condition2 = BeanCondition
            .includeAnnotations(TestAnnotation2::class.java)
            .build()
        val match2 = condition2.matcher().match(context)
        println("match2: $match2")
        Assert.assertEqualsDeep(
            match2, mapOf(
                "testService2" to testService2,
                "testService3" to testService3,
                "testService4" to testService4
            )
        )

        val condition3 = BeanCondition
            .includeNamePattern("*Service5*")
            .build()
        val match3 = condition3.matcher().match(context)
        println("match3: $match3")
        Assert.assertEqualsDeep(
            match3, mapOf(
                "testService5" to testService5
            )
        )

        val condition4 = condition1.and(condition2)
        val match4 = condition4.matcher().match(context)
        println("match4: $match4")
        Assert.assertEqualsDeep(
            match4, mapOf(
                "testService2" to testService2,
                "testService3" to testService3
            )
        )

        val condition5 = condition1.or(condition2)
        val match5 = condition5.matcher().match(context)
        println("match5: $match5")
        Assert.assertEqualsDeep(
            match5, mapOf(
                "testService1" to testService1,
                "testService2" to testService2,
                "testService3" to testService3,
                "testService4" to testService4
            )
        )

        val condition6 = condition5.not().and(condition3)
        val match6 = condition6.matcher().match(context)
        println("match6: $match6")
        Assert.assertEqualsDeep(
            match6, mapOf(
                "testService5" to testService5
            )
        )

        val condition7 = condition1.or(condition2).and(condition4).not().and(condition5)
        val match7 = condition7.matcher().match(context)
        println("match7: $match7")
        Assert.assertEqualsDeep(
            match7, mapOf(
                "testService1" to testService1,
                "testService4" to testService4
            )
        )

        val condition8 = condition1.and(condition2.not())
        val match8 = condition8.matcher().match(context)
        println("match8: $match8")
        Assert.assertEqualsDeep(
            match8, mapOf(
                "testService1" to testService1
            )
        )

        val condition9 = condition8.not().and(condition5)
        val match9 = condition9.matcher().match(context)
        println("match9: $match9")
        Assert.assertEqualsDeep(
            match9, mapOf(
                "testService2" to testService2,
                "testService3" to testService3,
                "testService4" to testService4
            )
        )
    }
}