package com.padel.couchbase

import akka.actor.{Actor, ActorSystem, Props}
import com.padel.couchbase.CouchbaseActors.{GetJson, InsertJson}
import play.api.libs.json.Json

class CouchbaseTest extends Actor with Couchbase {
  override def bucketName: String = "acc"
}

object CouchbaseIt extends App {

  val system = ActorSystem.create("test")

  val actorRef = system.actorOf(Props[CouchbaseTest])

  actorRef ! InsertJson("test", Json.obj("id" -> "test", "dos" -> "dos"))

  actorRef ! GetJson("test")

}
