package com.padel.http

import akka.actor.Actor
import com.padel.couchbase.{Couchbase, Model}
import com.padel.protbuffers.padel.Player

class Actors {
}

class PlayerActor extends Actor {

  //val cl = Couchbase()
  override def receive: Receive = {
    case _ => sender() ! Player(id = "id_sample", name = "Name Sample")
  }
}
