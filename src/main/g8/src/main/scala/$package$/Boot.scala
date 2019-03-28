package $package$

import org.joda.time.DateTimeZone
import java.util.TimeZone
import org.slf4j.LoggerFactory

import kamon.Kamon
import kamon.prometheus.PrometheusReporter
import kamon.sigar.SigarProvisioner

import org.hyperic.sigar.Sigar

import scala.concurrent.{Future, ExecutionContext}
import akka.http.scaladsl.Http
import akka.actor.{ActorSystem, CoordinatedShutdown}
import akka.cluster.Cluster
import akka.management.cluster.bootstrap.ClusterBootstrap
import akka.management.scaladsl.AkkaManagement

import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}

import $package$.dao._
import $package$.common._

object Boot {

    /**
    * setup the default timezone for the JVM
    */
    def timeZone {
        val old = java.util.TimeZone.getDefault
        println(s"current system time zone is \$old")
        val newZ = TimeZone.getTimeZone("America/Los_Angeles")
        TimeZone.setDefault(newZ)
        DateTimeZone.setDefault(DateTimeZone.forTimeZone(newZ))
        println(s"System time zone changed to \${TimeZone.getDefault}")
        println(s"Joda time zone changed to \${DateTimeZone.getDefault}")
    }
    def main(args:Array[String]) {
        try {
            SigarProvisioner.provision()
            val sigar = new Sigar()
        } catch {
            case ex: Exception =>
            println("Can not load sigar native lib", ex)
        }

        timeZone
        Kamon.addReporter(new PrometheusReporter())

        implicit val actorSystemName: String =
          sys.props.getOrElse(
            "actorSystemName",
            throw new IllegalArgumentException("Actor system name must be defined by the actorSystemName property"))
        val config = AkkaConfig.config(actorSystemName)
        implicit val actorSystem: ActorSystem = ActorSystem(actorSystemName, config)
        implicit val materializer: ActorMaterializer = ActorMaterializer()

        val LOGGER = LoggerFactory.getLogger(actorSystemName)

        val rdbModule = new RDBRepository("db.rdb")
        val tdbModule = new TDBRepository("db.tdb")
        
        AkkaManagement(actorSystem).start()
        ClusterBootstrap(actorSystem).start()

        val futureBinding: Future[Http.ServerBinding] = Http().bindAndHandle(Routes.rootV1(), config.getString("http.interface"), config.getInt("http.port"))
        val output = s"Server online at http://\${config.getString("http.interface")}:\${config.getString("http.port")}/\n"
        println(output)
        LOGGER.info(output)

        CoordinatedShutdown(actorSystem).addJvmShutdownHook {
            AkkaManagement(actorSystem).stop()
            val msg = s"\$actorSystemName terminated, bye..."
            println(msg)
            LOGGER.info(msg)
        }
    }
}