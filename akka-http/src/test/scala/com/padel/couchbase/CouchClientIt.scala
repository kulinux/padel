package com.padel.couchbase

import com.couchbase.client.java.document.JsonStringDocument
import com.sandinh.couchbase.CBCluster
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.{Failure, Success}

/**
  * https://github.com/ohze/couchbase-scala
  */
object CouchClientIt {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.load()
    val cluster = new CBCluster(config)
    val accBucket = cluster.openBucket("acc")


    val res = accBucket.map(
      _.insert(
          JsonStringDocument.create("test3", "{\"test\": \"mono\"}")
      )
    )

    res.onComplete {
      case Success(x) => {
        // the code won't come here
        println(s"\nresult = $x")
      }
      case Failure(e) => {
        // the code comes here because of the intentional exception
        System.err.println("Failure happened!")
        // just a short message; i don't care about the full exception
        System.err.println(e.getMessage)
      }
      case a => println(a)
    }

    Await.result(res, 5.seconds)

  }
}
class CouchClient {

}
