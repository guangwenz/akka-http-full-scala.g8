import sbt._

object Dependencies {
  val slickVersion = "$slickVersion$"
  val slickJodaMapperVersion = "2.3.0"
  val akkaVersion = "$akkaVersion$"
  val akkaHttpVersion = "$akkaHttpVersion$"
  val akkaManagementVersion = "$akkaManagementVersion$"

  lazy val joda = "joda-time" % "joda-time" % "2.9.9"
  lazy val logback = "ch.qos.logback" % "logback-classic" % "1.2.3"
  lazy val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4"

  lazy val slick = Seq(
    "com.typesafe.slick" %% "slick" % slickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
    "com.typesafe.slick" %% "slick-testkit" % slickVersion % Test,
    "com.github.tototoshi" %% "slick-joda-mapper" % slickJodaMapperVersion
  )

  lazy val akkaHttp = Seq(
    "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion
  )

  lazy val akka = Seq(
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
    "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,

    //cluster metrics
    "io.kamon" % "sigar-loader" % "1.6.6-rev002",
    "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,

    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test
  )

  lazy val akkaMgr = Seq(
    "com.lightbend.akka.management" %% "akka-management" % akkaManagementVersion,
    "com.lightbend.akka.management" %% "akka-management-cluster-bootstrap" % akkaManagementVersion,
    "com.lightbend.akka.management" %% "akka-management-cluster-http" % akkaManagementVersion,
    "com.lightbend.akka.discovery" %% "akka-discovery-dns" % akkaManagementVersion,
    "com.lightbend.akka.discovery" %% "akka-discovery-kubernetes-api" % akkaManagementVersion
  )

  lazy val kamon = Seq(
    "io.kamon" %% "kamon-core" % "1.1.0",
    "io.kamon" %% "kamon-akka-2.5" % "1.0.1",
    "io.kamon" %% "kamon-akka-http-2.5" % "1.0.1",
    "io.kamon" %% "kamon-prometheus" % "1.0.0",
    "io.kamon" %% "kamon-zipkin" % "1.0.0"
  )
  
  lazy val splitBrainResolver = "org.guangwenz" %% "akka-down-resolver" % "1.2.4"
}
