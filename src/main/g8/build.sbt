import com.typesafe.sbt.packager.docker._
import Dependencies._

enablePlugins(JavaAppPackaging,JavaAgent)

fork := true
javaAgents += "org.aspectj" % "aspectjweaver" % "1.8.13"
javaOptions in Universal += "-Dorg.aspectj.tracing.factory=default" 

javaOptions := Seq(
  "-DactorSystemName=$actorSystemName$",
  "-Dakka.cluster.seed-nodes.0=akka.tcp://$actorSystemName$@localhost:2551",
  "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=9797"
)

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "$organization$",
      scalaVersion := "$scala_version$"
    )),
    name := "$name$",
    libraryDependencies ++= kamon,
    libraryDependencies ++= akka,
    libraryDependencies ++= akkaHttp,
    libraryDependencies ++= akkaMgr,
    libraryDependencies ++= slick,
    libraryDependencies ++= persistence,
    libraryDependencies += logback,
    libraryDependencies += joda,
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.trueaccord.scalapb" %% "scalapb-runtime" % com.trueaccord.scalapb.compiler.Version.scalapbVersion % "protobuf",
    dockerCommands += Cmd("USER", "root")
  )

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)