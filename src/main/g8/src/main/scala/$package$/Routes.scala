package $package$

import scala.concurrent.{Future, ExecutionContext}
import akka.actor.{ActorRef,ActorSystem}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes

object Routes {
    def rootV1()(implicit actorSystem: ActorSystem) = {
        pathSingleSlash {
          redirect("/docs/", StatusCodes.PermanentRedirect)
        } ~ path("healthcheck") {
          complete("OK")
        } ~ path("ping") {
          complete("PONG")
        } ~ pathPrefix("docs") {
          pathEndOrSingleSlash {
            redirect("/docs/index.html", StatusCodes.PermanentRedirect)
          } ~ getFromResourceDirectory("swagger")
        }
    }
}