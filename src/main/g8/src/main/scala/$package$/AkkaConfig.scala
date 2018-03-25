package $package$

import scala.collection.JavaConversions._
import com.typesafe.config.{Config, ConfigFactory, ConfigValueFactory}

object AkkaConfig {
    def config(actorSystemName: String): Config = { 
        var overrides = ConfigFactory.empty() withFallback ConfigFactory.load()
        sys.env.get("HOST_NAME").map{ bindHost =>
            println(s"binding to host \$bindHost")
            overrides = overrides.withValue("akka.remote.netty.tcp.hostname", ConfigValueFactory.fromAnyRef(bindHost))
            overrides = overrides.withValue("akka.management.http.hostname", ConfigValueFactory.fromAnyRef(bindHost))
            overrides = overrides.withValue("http.interface", ConfigValueFactory.fromAnyRef(bindHost))
        }
        sys.env.get("AKKA_SERVICE_NAME").map{ serviceName =>
            println(s"configuring service name for DNS discovery \$serviceName")         
            overrides = overrides.withValue("akka.management.cluster.bootstrap.contact-point-discovery.service-name", ConfigValueFactory.fromAnyRef(serviceName))
        }
        sys.env.get("AKKA_REMOTING_BIND_PORT").map{ remoteBindPort =>
            overrides = overrides.withValue("akka.remote.netty.tcp.port", ConfigValueFactory.fromAnyRef(remoteBindPort))
        }
        sys.env.get("AKKA_SEED_NODES").map{seeds =>
            val seedNodes = seeds.split(",").map(i=>s"akka.tcp://\$actorSystemName@\$i").toList
            val value=ConfigValueFactory.fromIterable(seedNodes)
            overrides = overrides.withValue("akka.cluster.seed-nodes", value)
        }
        println("akka.remote.netty.tcp.hostname", overrides.getString("akka.remote.netty.tcp.hostname"))
        println("akka.remote.netty.tcp.port", overrides.getString("akka.remote.netty.tcp.port"))
        println("akka.cluster.seed-nodes", overrides.getList("akka.cluster.seed-nodes"))
        println("db.tdb.url", overrides.getString("db.tdb.url"))
        println("slick.db.host", overrides.getString("slick.db.host"))
        overrides
    }
}