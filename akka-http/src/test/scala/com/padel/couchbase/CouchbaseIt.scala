package com.padel.couchbase

import java.util.Date

import akka.actor.{ActorRef, ActorSystem}
import com.padel.couchbase.Couchbase.All
import com.padel.couchbase.Model.Player
import akka.pattern._
import akka.util.Timeout

import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object CouchbaseIt extends App {

  implicit val timeout = Timeout(10, SECONDS)

  val system = ActorSystem.create("test")

  val today = new Date().toString

  val couchbase: ActorRef = Couchbase.newInstance(system)
  val resInsert = couchbase ? Player(today, s"Value $today")

  val resWriter = couchbase ? All
  resWriter.map( f => println(s"Result $f" ) )

  Thread.sleep(100000)

}
