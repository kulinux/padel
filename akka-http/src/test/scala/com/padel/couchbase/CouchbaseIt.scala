package com.padel.couchbase

import akka.actor.{Actor, ActorSystem, Props}
import com.padel.couchbase.CouchbaseActors._
import play.api.libs.json.Json

class CouchbaseTest extends Actor with Couchbase {
  override def bucketName: String = "acc"
}

class CouchbaseIt extends Actor {
  val actorRef = context.actorOf(Props[CouchbaseTest])

  actorRef ! InsertJson("test2", Json.obj("id" -> "test2", "dos" -> "dos2"))
  actorRef ! GetJson("test", self)

  actorRef ! AllJson(self)
  actorRef ! RemoveJson("test2")

  override def receive: Receive = {
    case a => println(s"Response $a")
  }
}

object CouchbaseIt extends App {

  val system = ActorSystem.create("test")

  val testAc = system.actorOf(Props[CouchbaseIt])

}
