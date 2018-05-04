package com.padel.http

import akka.actor.{ Actor, ActorRef, Props }
import com.padel.couchbase.Couchbase.All
import com.padel.couchbase.{ Couchbase, Model }
import com.padel.http.PlayerActor.{ GetPlayer, GetPlayerResponse }
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

class PlayerActor extends Actor {

  val cl = Couchbase.newInstance(context.system)

  var webActorRef: ActorRef = null

  override def receive: Receive = {
    case "ALL" => {
      webActorRef = sender()
      cl ! All
    }
    case PlayerActor.GetPlayer(id) => cl ! Couchbase.GetPlayer(id)

    case Couchbase.GetPlayerResponse(player) =>
      webActorRef ! PlayerActor.GetPlayerResponse(Player(id = player.id, name = player.name))

    case player: Player => cl ! player

    case players: ListBuffer[Model.Player] =>
      webActorRef ! players.map(pl => Player(pl.id))

  }
}
