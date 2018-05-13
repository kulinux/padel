import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import org.reactivecouchbase.rs.scaladsl.{ N1qlQuery, ReactiveCouchbase }
import org.reactivecouchbase.rs.scaladsl.json._
import org.reactivecouchbase.rs.scaladsl._
import play.api.libs.json._
import akka.stream.scaladsl.Sink
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory

object ReactiveCouchbaseTest extends App {

  val system = ActorSystem("ReactiveCouchbaseSystem")

  implicit val materializer = ActorMaterializer.create(system)
  implicit val ec = system.dispatcher

  val driver = ReactiveCouchbase(ConfigFactory.parseString(
    """
      |buckets {
      |  acc {
      |    name = "acc"
      |    hosts = ["127.0.0.1"]
      |     authentication = {
      |       username = "Administrator"
      |       password = "password"
      |     }
      |  }
      |}
    """.stripMargin
  ))

  val bucket = driver.bucket("acc")

  val future = for {
    _ <- bucket.insert[JsValue](
      "key1",
      Json.obj("message" -> "Hello World", "type" -> "doc")
    )
    doc <- bucket.get("key1")
    exists <- bucket.exists("key1")
    docs <- bucket.search(
      N1qlQuery("select message from ac where type = $type")
        .on(Json.obj("type" -> "doc").asQueryParams)
    )
    .asSeq
      messages <- bucket.search(
        N1qlQuery("select message from ac where type = 'doc'")
    ).asSource.map(doc => (doc \ "message").as[String].toUpperCase)
    .runWith(Sink.seq[String])
    _ <- driver.terminate()
  } yield (doc, exists, docs)

  future.map {
    case (_, _, docs) => println(s"found $docs")
  }

}