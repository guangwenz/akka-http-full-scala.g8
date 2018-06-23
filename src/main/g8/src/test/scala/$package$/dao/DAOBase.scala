package $package$.dao

import org.scalatest._
import com.typesafe.config.ConfigFactory

trait DAOBase extends AsyncFlatSpec with Matchers {
  val config = ConfigFactory.load("application-test.conf")
  val db = new RDBRepository("db.testdb", config)
}
