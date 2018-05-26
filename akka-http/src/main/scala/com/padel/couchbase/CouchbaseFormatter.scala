package com.padel.couchbase

import akka.actor.{Actor, ActorRef}
import com.padel.couchbase.CouchbaseActors._
import com.padel.couchbase.CouchbaseFormatter._
import com.padel.couchbase.Model.Identificable
import play.api.libs.json.{Reads, Writes}

class CouchbaseFormatter[T <: Identificable]
(
  cb: ActorRef,
  writer: Writes[T],
  reader: Reads[T] ) extends Actor {
  override def receive: Receive = {
    case Insert(obj: T) => {
      cb ! InsertJson(obj.id, writer.writes(obj))
    }
    case Update(obj: T) => {
      cb ! UpdateJson(obj.id, writer.writes(obj))
    }
    case Get(id) => {
      cb ! GetJson(id, sender())
    }
    case GetResponseJson(jss, answerTo) => {
      val res: Seq[T] = jss.map( reader.reads(_) ).filter( _.isSuccess ).map( _.get )

      answerTo ! GetResponse( res )
    }
    case All() => {
      cb ! AllJson(sender())
    }
    case Remove(id) => {
      cb ! RemoveJson(id)
    }
    case a => println(s"CouchbaseFormatter $a")
  }
}


object CouchbaseFormatter {

  case class All()
  case class Insert[T <% {def id: String}](player: T)
  case class Update[T <% {def id: String}](player: T)
  case class Remove(id: String)
  case class Ack()
  case class Get(id: String)
  case class GetResponse[T <% {def id: String}](player: Seq[T])
}

object Model {
  trait Identificable {
    def id: String
  }

  case class Player
  ( id: String,
    name: String
  ) extends Identificable

  case class Team
  (
    id: String,
    player1: String,
    player2: String
  )

  case class Match
  (
    id: String,
    team: Team
  )

  case class Round
  (
    id: String,
    matches: Match
  ) extends Identificable

  case class Tournament
  (
    id: String,
    name: String,
    players: Seq[Player],
    rounds: Round
  ) extends Identificable
}



