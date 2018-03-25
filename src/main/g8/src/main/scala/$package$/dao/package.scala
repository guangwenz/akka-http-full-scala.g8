package $package$

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import org.joda.time.LocalDateTime

import com.typesafe.config.{Config, ConfigFactory}

import $package$.common._

package object dao {
    /**
    * convert the local date time value from src time zone to dest time zone
    * @param value, the local date value to convert
    * @param src, source datetimezone
    * @param dest, dest datetimezone
    */
    def convertTimeZone(value: LocalDateTime, src: DateTimeZone, dest: DateTimeZone): LocalDateTime = {
        value.toDateTime(src).withZone(dest).toLocalDateTime
    }

    trait DbComponent {
        val profile: slick.jdbc.JdbcProfile
        import profile.api._
        val db: Database

        object CustomColumnTypes {
        }
    }
    private[dao] trait DbModule extends DbComponent
    
    class RDBRepository(val dbConfigKey: String, val config: Config = ConfigFactory.load()) extends DbModule {

        override val profile: slick.jdbc.JdbcProfile = slick.jdbc.$slickProfile$
        import profile.api._

        override val db = Database.forConfig(dbConfigKey, config)
    }

    class TDBRepository(val dbConfigKey: String, val config: Config = ConfigFactory.load()) extends DbModule {

        override val profile: slick.jdbc.JdbcProfile = slick.jdbc.$slickProfile$
        import profile.api._

        override val db = Database.forConfig(dbConfigKey, config)
    }
}