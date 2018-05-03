package com.padel.http

import akka.actor.{ Actor, ActorRef, Props }
import com.padel.couchbase.Couchbase.All
import com.padel.couchbase.{ Couchbase, Model }
import com.padel.protbuffers.padel.Player

import scala.collection.mutable.ListBuffer

class Actors {
}

object PlayerActor {
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
    case players: ListBuffer[Player] =>
      webActorRef ! players
    case player: Player => cl ! player
  }
}
