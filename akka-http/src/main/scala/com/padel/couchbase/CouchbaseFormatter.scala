package com.padel.couchbase

import akka.actor.{Actor, ActorRef}
import com.padel.couchbase.CouchbaseActors.InsertJson
import com.padel.couchbase.CouchbaseFormatter.Insert
import com.padel.couchbase.Model.Identificable
import play.api.libs.json.Writes

class CouchbaseFormatter[T <: Identificable] (cb: ActorRef, writer: Writes[T]) extends Actor {
  override def receive: Receive = {
    case Insert(obj: T) => cb.tell(InsertJson(obj.id, writer.writes(obj)), sender())
  }
}


object CouchbaseFormatter {

  case class All()
  case class Insert[T <: Identificable](player: T)
  case class Get(id: String)
  case class GetResponse[T <: Identificable](player: T)
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



