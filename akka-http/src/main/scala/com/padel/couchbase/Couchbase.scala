package com.padel.couchbase

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import com.couchbase.client.java.query.{AsyncN1qlQueryResult, N1qlQuery}
import com.padel.couchbase.Couchbase.All
import com.padel.couchbase.Model.{Identificable, Player}
import com.sandinh.couchbase.document.JsDocument
import com.sandinh.couchbase.{CBCluster, ScalaBucket}
import com.typesafe.config.ConfigFactory
import play.api.libs.json.{JsSuccess, JsValue, Json}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

object Model {
  trait Identificable {
    def id: String
  }

  case class Player(id: String, name: String) extends Identificable
}

object JsonFormatters {
  implicit val fmtP = Json.format[Player]
  implicit val fmtW = Json.writes[Player]
  implicit val fmtR = Json.reads[Player]
}

class Couchbase(bucket : ScalaBucket)
  extends Actor {
  import JsonFormatters._


  override def receive: Receive = {
    case p: Player => {
      val toCouch = fmtW.writes(p)
      bucket.insert( JsDocument(p.id, toCouch) )
    }

    case All => {
      val snd = sender()
      val result = bucket.query(N1qlQuery.simple(" SELECT * FROM `acc`"))
        .map(
          _.rows().subscribe(res => {
            val str = res.value().get("acc")
            val parsed =
              fmtR.reads(
                Json.parse(str.toString)
              )

            parsed match {
              case JsSuccess(player, _) => snd ! player
              case other => println(s"ERROR $other")
            }
          } )
        )
    }
  }
}


object Couchbase {

  case class All()


  def newInstance(system: ActorSystem): ActorRef = {

    val config = ConfigFactory.load()
    val cluster = new CBCluster(config)
    val bucket = cluster.openBucket("acc")

    val resFut = bucket map (sc =>
      system.actorOf(Props(new Couchbase(sc)), "CouchbasePlayer")
    )

    Await.result(resFut, 10.seconds)
  }

}
