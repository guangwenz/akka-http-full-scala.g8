package $package$

import collection.mutable.{LinkedHashMap, LinkedHashSet, Map => MutableMap}

package object utils{
    
    implicit class BigDecimalImplicits(input: BigDecimal) {
        def monetize: String = {
            input.setScale(2, BigDecimal.RoundingMode.HALF_UP).toString
        }
    }

    implicit class StringImplicits(input:String) {
        def money: BigDecimal = if(input.isEmpty) BigDecimal(0) else BigDecimal(input).setScale(2, BigDecimal.RoundingMode.HALF_UP)
    }

    implicit class GroupByOrderedImpl[A](val items: Traversable[A]) extends AnyVal {
        def groupByOrdered[K](f: A => K): MutableMap[K, LinkedHashSet[A]] = {
            val map = LinkedHashMap[K, LinkedHashSet[A]]().withDefault(_ => LinkedHashSet[A]())
            for (item <- items) {
                val key = f(item)
                map(key) = map(key) + item
            }
            map
        }
    }
}