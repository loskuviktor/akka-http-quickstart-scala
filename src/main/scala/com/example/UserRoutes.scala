package com.example

import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route

import scala.concurrent.Future
import com.example.UserRegistry._
import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.AskPattern._
import akka.util.Timeout
import com.example.utils.DTO.{SessionResult, UserData}

class UserRoutes(userRegistry: ActorRef[UserRegistry.Command])(implicit val system: ActorSystem[_]) {

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import com.example.utils.JsonFormats._

  // If ask takes more time than this to complete the request is failed
  private implicit val timeout = Timeout.create(system.settings.config.getDuration("my-app.routes.ask-timeout"))

  def createSession(userData: UserData): Future[SessionResult] =
    userRegistry.ask(CreateSession(userData, _))

  val userRoutes: Route =
    pathPrefix("companies") {
      concat(
        pathEnd {
          concat(
            post {
              entity(as[UserData]) {userData =>
                onSuccess(createSession(userData)) {requestResult =>
                  complete((StatusCodes.Created, requestResult))
                }
              }
            })
        })
    }
}
