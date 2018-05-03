package com.padel.http

import akka.actor.{ ActorRef, ActorSystem, Props }
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.marshalling.{ Marshaller, ToResponseMarshallable, ToResponseMarshaller }
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import com.padel.protbuffers.padel.Player
import akka.pattern.ask
import akka.util.{ ByteString, Timeout }
import com.padel.couchbase.Model

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

trait Routes {

  def marshallPlayer(p: Player): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/grpc+proto`, ByteString(p.toProtoString))

  def marshallListPlayer(p: ListBuffer[Player]): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/grpc+proto`, ByteString(
      "[" + p.map(_.toProtoString).mkString(",") + "]"
    ))

  implicit val marshallerPlayer = Marshaller.opaque[Player, MessageEntity](marshallPlayer)
  implicit val marshallerListPlayer = Marshaller.opaque[ListBuffer[Player], MessageEntity](marshallListPlayer)

  def actorRef: ActorRef

  val route: Route =
    path("players") {
      get {
        implicit val timeout: Timeout = 50.seconds
        val fut: Future[ListBuffer[Model.Player]] = (actorRef ? "ALL")
          .mapTo[ListBuffer[Model.Player]]
        val futWeb = fut.map(_.map(pl => Player(pl.id)))
        complete(futWeb)
      }
    }

}

object WebServer extends Routes {

  implicit val system = ActorSystem.create("webserver")

  implicit val materializer = ActorMaterializer()

  override def actorRef: ActorRef = system.actorOf(PlayerActor.props())

  def main(args: Array[String]): Unit = {
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    /*
    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
      */
  }
}
