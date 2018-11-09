package $package$

import scala.collection.mutable

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
        def groupByOrdered[K](f: A => K): mutable.Map[K, mutable.LinkedHashSet[A]] = {
            val map = mutable.LinkedHashMap[K, mutable.LinkedHashSet[A]]().withDefault(_ => mutable.LinkedHashSet[A]())
            for (item <- items) {
                val key = f(item)
                map(key) = map(key) + item
            }
            map
        }
    }
}
