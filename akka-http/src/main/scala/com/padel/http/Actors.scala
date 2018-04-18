package com.padel.http

import akka.actor.Actor
import com.padel.protbuffers.padel.Player

class Actors {
}

class GetPlayersActor extends Actor {
  override def receive: Receive = {
    case _ => sender() ! Player(id = "id_sample", name = "Name Sample")
  }
}
