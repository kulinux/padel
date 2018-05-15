package com.padel.http

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.padel.couchbase.CouchbaseFormatter.All
import com.padel.couchbase.{Couchbase, CouchbaseActors, Model}
import com.padel.http.PlayerActor.{GetPlayer, GetPlayerResponse}
import com.padel.protbuffers.padel.Player

import scala.collection.mutable.ListBuffer

class Actors {
}

object PlayerActor {
  case class GetPlayer(id: String)
  case class GetPlayerResponse(player: Player)

  case class InsertPlayer(player: Player)

  def props() = Props[PlayerActor]
}

class PlayerActor extends Actor with CouchbaseActors {

  import CouchbaseActors._

  var webActorRef: ActorRef = null

  override def receive: Receive = {
    case "ALL" => {
      webActorRef = sender()
      cbPlayer ! All()
    }
    case PlayerActor.GetPlayer(id) => {
      webActorRef = sender()
      cbPlayer ! GetPlayer(id)
    }

    case GetPlayerResponse(player) =>
      webActorRef ! PlayerActor.GetPlayerResponse(Player(id = player.id, name = player.name))

    case player: Player => cbPlayer ! player

    case players: ListBuffer[Model.Player] =>
      webActorRef ! players.map(pl => Player(pl.id))

  }
}
