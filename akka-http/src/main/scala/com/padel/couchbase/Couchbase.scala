package com.padel.couchbase

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.Sink
import org.reactivecouchbase.rs.scaladsl.json._
import com.padel.couchbase.CouchbaseActors.{AllJson, GetJson, GetResponseJson, InsertJson}
import com.padel.couchbase.Model.{Identificable, Player}
import com.typesafe.config.ConfigFactory
import org.reactivecouchbase.rs.scaladsl.{N1qlQuery, ReactiveCouchbase}
import play.api.libs.json.{JsObject, JsValue, Json}

object Model {
  trait Identificable {
    def id: String
  }

  case class Player(
    id: String,
    name: String
  ) extends Identificable

  case class Team(
    id: String,
    player1: String,
    player2: String
  )

  case class Match(
    id: String,
    team: Team
  )

  case class Round(
    id: String,
    matches: Match
  ) extends Identificable

  case class Tournament(
    id: String,
    name: String,
    players: Seq[Player],
    rounds: Round
  ) extends Identificable
}

trait CouchbaseActors extends Actor {
  val cbPlayer: ActorRef = ???




}

object CouchbaseActors {
  case class All()
  case class Insert[T <: Identificable](player: T)
  case class Get(id: String)
  case class GetResponse[T <: Identificable](player: T)

  case class AllJson()
  case class InsertJson(id: String, js: JsValue)
  case class GetJson(id: String)
  case class GetResponseJson(js: Seq[JsValue])

}

trait Couchbase
    extends Actor {

  def bucketName: String


  val system = context.system

  implicit val materializer = ActorMaterializer.create(system)
  implicit val ec = system.dispatcher

  val driver = ReactiveCouchbase(ConfigFactory.load())

  val bucket = driver.bucket(bucketName)

  override def receive: Receive = {
    case InsertJson(id, js) => {
      bucket.insert[JsValue](
        id,
        js
      )
    }
    case GetJson(id) => {
      for( docs <- bucket.search(
        N1qlQuery( "select * from " + bucketName + " where id = $id")
          .on(Json.obj("id" -> id).asQueryParams)
      ).asSeq ) {
        docs.map( _ \\ bucketName )
            .map( sender() ! GetResponseJson(_) )
      }
    }
    case AllJson() => {
      for( docs <- bucket.search(
        N1qlQuery( "select * from " + bucketName )
      ).asSeq ) {
        docs.map( _ \\ bucketName )
          .map( sender() ! GetResponseJson(_) )
      }

    }

  }



}

private object Couchbase {

}
