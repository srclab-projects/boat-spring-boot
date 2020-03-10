package xyz.srclab.spring.boot.common.test.bean

import org.testng.Assert
import org.testng.annotations.Test
import xyz.srclab.spring.boot.common.`object`.CommonObjectConverter
import xyz.srclab.spring.boot.common.`object`.ObjectConverter
import xyz.srclab.spring.boot.common.bean.BeanHelper
import xyz.srclab.spring.boot.common.bean.BeanOperator
import xyz.srclab.spring.boot.common.bean.CommonBeanResolver
import xyz.srclab.spring.boot.common.test.model.Bar
import xyz.srclab.spring.boot.common.test.model.Foo
import java.util.*

object BeanOperatorTest {

    @Test
    fun testCommon() {
        val foo1 = Foo()
        foo1.p1 = "foo1.p1"
        val foo2 = Foo()
        foo2.p1 = "foo2.p1"
        BeanHelper.copyProperties(foo1, foo2)
        println(foo2.p1)
        Assert.assertEquals(foo2.p1, foo1.p1)

        val map1 = HashMap<String, String>()
        map1["p1"] = "map1.p1"
        val map2 = HashMap<String, String>()
        map2["p1"] = "map2.p1"
        BeanHelper.copyProperties(map1, map2)
        println(map2["p1"])
        Assert.assertEquals(map2["p1"], map1["p1"])

        BeanHelper.copyProperties(foo1, map2)
        println(map2["p1"])
        Assert.assertEquals(map2["p1"], foo1.p1)

        BeanHelper.copyProperties(map1, foo2)
        println(foo2.p1)
        Assert.assertEquals(foo2.p1, map1["p1"])

        val bar = Bar()
        bar.p2 = 66
        BeanHelper.copyProperties(foo1, bar)
        println(bar.p1)
        Assert.assertEquals(bar.p1, foo1.p1)
        println(bar.p2 as Any?)
        Assert.assertEquals(bar.p2 as Any?, 0)

        bar.p2 = 77
        BeanHelper.copyPropertiesIgnoreNull(foo1, bar)
        println(bar.p2 as Any?)
        Assert.assertEquals(bar.p2 as Any?, 77)

        foo1.p3 = bar
        Assert.assertThrows(IllegalArgumentException::class.java) {
            BeanHelper.copyProperties(foo1, bar)
        }
    }

    @Test
    fun testCustomer() {
        val pair = createFooAndBar()
        val foo = pair.first
        val bar = pair.second

        val beanOperator = BeanOperator.newBuilder()
            .setBeanResolver(CommonBeanResolver.INSTANCE)
            .setObjectConverter(
                ObjectConverter.newBuilder()
                    .addConverter(CommonObjectConverter.INSTANCE)
                    .addConverter(object : ObjectConverter {
                        override fun supportConvert(`object`: Any?, type: Class<*>?): Boolean =
                            type == Foo::class.java

                        override fun convert(`object`: Any?, type: Class<*>?): Any {
                            val f = Foo()
                            f.p1 = "new Foo!"
                            return f
                        }
                    })
                    .build()
            )
            .build()

        println("bar.p1 = ${bar.p1}")
        println("bar.p3.p1 = ${bar.p3?.p1}")
        beanOperator.copyProperties(foo, bar)
        println("after copyProperties bar.p1 = ${bar.p1}")
        println("after copyProperties bar.p3.p1 = ${bar.p3?.p1}")
        Assert.assertEquals(bar.p1, foo.p1)
        Assert.assertEquals(bar.p3?.p1, "new Foo!")
    }

    private fun createFooAndBar(): Pair<Foo, Bar> {
        val foo = Foo()
        val bar = Bar()
        foo.p1 = "f00.p1"
        foo.p2 = 22
        foo.p3 = bar
        bar.p1 = "bar.p1"
        bar.p2 = 33
        bar.p3 = foo
        return Pair(foo, bar)
    }
}