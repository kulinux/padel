package com.padel.http

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.padel.couchbase.{Couchbase, CouchbaseFormatter, Model}
import com.padel.couchbase.CouchbaseFormatter._
import play.api.libs.json.{Json, Reads, Writes}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._


trait Routes {

  /*
  def marshallPlayer(p: Player): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/grpc+proto`, ByteString(p.toProtoString))

  def marshallListPlayer(p: ListBuffer[Player]): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/grpc+proto`, ByteString(
      "[" + p.map(_.toProtoString).mkString(",") + "]"
    ))
  def marshallPlayer(p: Player): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/json`, ByteString(JsonFormat.toJsonString(p)))

  def marshallListPlayer(p: ListBuffer[Player]): MessageEntity =
    HttpEntity.Strict(ContentTypes.`application/json`, ByteString(
      "[" + p.map(JsonFormat.toJsonString(_)).mkString(",") + "]"
    ))

  implicit val marshallerPlayer = Marshaller.opaque[Player, MessageEntity](marshallPlayer)
  implicit val marshallerListPlayer = Marshaller.opaque[ListBuffer[Player], MessageEntity](marshallListPlayer)
    */


  implicit val timeout: Timeout = 300.seconds

  def entityRoute[T <% {def id: String}](url: String,
                     writer: Writes[T],
                     reader: Reads[T],
                     actorRef: ActorRef
                    ): Route = {
    pathPrefix(url) {
      get {
        pathEndOrSingleSlash {
          val fut: Future[GetResponse[T]] = (actorRef ? All())
            .mapTo[GetResponse[T]]

          val futStr = fut.map(_.player.map(writer.writes(_) ) )
            .map("[" + _.mkString(",") + "]")

          complete(futStr)
        } ~
        path(Remaining) { id => {
            val pr = (actorRef ? Get(id))
              .mapTo[GetResponse[T]]
              .map(rsp => rsp.player)
              .map(rsp => writer.writes(rsp(0)))
              .map(_.toString())

            complete(pr)
          }
        }
      } ~
      post {
          pathEndOrSingleSlash { entity(as[String]) { json => {
            val js = Json.parse(json)
            val fut : Future[Ack] = (actorRef ? Insert(reader.reads(js).get))
              .mapTo[Ack]
            complete(json)
          } } }
      } ~
      delete {
        path(Remaining) { id => {
          val fut : Future[Ack] = (actorRef ? Remove(id))
            .mapTo[Ack]
          complete("")
        } }
      } ~
      put {
        pathEndOrSingleSlash { entity(as[String]) { json => {
          val js = Json.parse(json)
          val fut : Future[Ack] = (actorRef ? Update(reader.reads(js).get))
            .mapTo[Ack]
          complete(json)
        } } }
      }
    }
  }
}


class CouchbaseImpl(bn: String) extends Actor with Couchbase {
  override def bucketName: String = bn
}

object WebServer extends Routes {

  implicit val system = ActorSystem.create("webserver")

  val actorRefCouchbasePlayer = system.actorOf(Props(new CouchbaseImpl("pl")), "CouchbasePlayer")
  val actorRefCouchbaseTeam = system.actorOf(Props(new CouchbaseImpl("tm")), "CouchbaseTeam")

  val playerActor = system.actorOf(
    Props(new CouchbaseFormatter(actorRefCouchbasePlayer, Json.writes[Model.Player], Json.reads[Model.Player]) ),
        "CouchbaseFormatterPlayer")

  val teamActor = system.actorOf(
    Props(new CouchbaseFormatter(actorRefCouchbaseTeam, Json.writes[Model.Player], Json.reads[Model.Player]) ),
    "CouchbaseFormatterTeam")


  val route: Route =
    entityRoute("player", Json.writes[Model.Player], Json.reads[Model.Player], playerActor) ~
      entityRoute("team", Json.writes[Model.Team], Json.reads[Model.Team], teamActor)


  implicit val materializer = ActorMaterializer()


  def main(args: Array[String]): Unit = {
    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)
  }
}
