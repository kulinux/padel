package com.padel.couchbase

import akka.actor.{Actor, ActorRef}
import akka.stream.ActorMaterializer
import com.padel.couchbase.CouchbaseActors._
import com.typesafe.config.ConfigFactory
import org.reactivecouchbase.rs.scaladsl.json._
import org.reactivecouchbase.rs.scaladsl.{N1qlQuery, ReactiveCouchbase}
import play.api.libs.json.{JsValue, Json}


trait CouchbaseActors extends Actor {
  val cbPlayer: ActorRef = ???
}

object CouchbaseActors {


  case class AllJson()
  case class InsertJson(id: String, js: JsValue)
  case class GetJson(id: String)
  case class GetResponseJson(js: Seq[JsValue])
  case class RemoveJson(id: String)
  case class AckJson()

}

trait Couchbase
    extends Actor {

  def bucketName: String


  val system = context.system

  implicit val materializer = ActorMaterializer.create(system)
  implicit val ec = system.dispatcher

  val driver = ReactiveCouchbase(ConfigFactory.load())

  val bucket = driver.bucket(bucketName)

  override def receive: Receive = {
    case InsertJson(id, js) => {
      val snd = sender()
      bucket.insert[JsValue](
        id,
        js
      ).map( _ => snd ! AckJson() )
    }
    case GetJson(id) => {
      val snd = sender()
      for( docs <- bucket.search(
        N1qlQuery( "select * from " + bucketName + " where id = $id")
          .on(Json.obj("id" -> id).asQueryParams)
      ).asSeq ) {
        docs.map( _ \\ bucketName )
            .map( snd ! GetResponseJson(_) )
      }
    }
    case AllJson() => {
      val snd = sender()
      for( docs <- bucket.search(
        N1qlQuery( "select * from " + bucketName )
      ).asSeq ) {
        snd ! GetResponseJson( docs.flatMap( _ \\ bucketName ) )
      }
    }
    case RemoveJson(id) => {
      val snd = sender()
     bucket.remove(id)
      .map( _ => snd ! AckJson() )
    }

  }



}

private object Couchbase {

}
