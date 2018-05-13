package com.padel.http

import akka.actor.{ ActorRef, ActorSystem }
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshaller
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.{ ByteString, Timeout }
import com.padel.http.PlayerActor.GetPlayerResponse
import com.padel.protbuffers.padel.Player

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

trait Routes {

  def marshallPlayer(p: Player): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/grpc+proto`, ByteString(p.toProtoString))

  def marshallListPlayer(p: ListBuffer[Player]): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/grpc+proto`, ByteString(
      "[" + p.map(_.toProtoString).mkString(",") + "]"
    ))
  /*
  def marshallPlayer(p: Player): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/json`, ByteString(JsonFormat.toJsonString(p)))

  def marshallListPlayer(p: ListBuffer[Player]): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/json`, ByteString(
      "[" + p.map(JsonFormat.toJsonString(_)).mkString(",") + "]"
    ))
    */

  implicit val marshallerPlayer = Marshaller.opaque[Player, MessageEntity](marshallPlayer)
  implicit val marshallerListPlayer = Marshaller.opaque[ListBuffer[Player], MessageEntity](marshallListPlayer)

  def actorRef: ActorRef

  implicit val timeout: Timeout = 50.seconds

  val route: Route =
    pathPrefix("player") {
      get {
        pathEndOrSingleSlash {
          val fut: Future[ListBuffer[Player]] = (actorRef ? "ALL")
            .mapTo[ListBuffer[Player]]
          complete(fut)
        } ~
          path(Remaining) { idPlayer =>
            {
              val pr = (actorRef ? PlayerActor.GetPlayer(idPlayer))
                .mapTo[GetPlayerResponse]
                .map(_.player)

              pr.onComplete(println)

              complete(pr)
            }
          }
      }
    }
}

object WebServer extends Routes {

  implicit val system = ActorSystem.create("webserver")

  implicit val materializer = ActorMaterializer()

  override val actorRef: ActorRef = system.actorOf(PlayerActor.props())

  def main(args: Array[String]): Unit = {
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

  }
}
