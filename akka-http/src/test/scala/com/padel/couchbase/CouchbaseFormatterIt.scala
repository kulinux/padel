package com.padel.couchbase

import akka.actor.{Actor, ActorSystem, Props}
import com.padel.couchbase.CouchbaseActors._
import com.padel.couchbase.CouchbaseFormatter.{All, Insert}
import com.padel.couchbase.Model.Player
import play.api.libs.json.Json

class CouchbaseTest extends Actor with Couchbase {
  override def bucketName: String = "acc"
}

class CouchbaseFormatterIt extends Actor {

  val actorRefCouchbase = context.actorOf(Props[CouchbaseTest], "CouchbaseTest")

  val actorRef = context.actorOf(
    Props(new CouchbaseFormatter(actorRefCouchbase, Json.writes[Player], Json.reads[Player]) ),
    "CouchbaseFormatter"
  )

  actorRef ! Insert( Player("idPlayer" + new java.util.Date(), "Name Player"))
  actorRef ! All()

  override def receive: Receive = {
    case a => println(s"Response $a")
  }
}

object CouchbaseFormatterIt extends App {

  val system = ActorSystem.create("test")

  val testAc = system.actorOf(Props[CouchbaseFormatterIt], "CouchbaseFormatterIt")

}
