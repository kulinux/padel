package com.padel.http

import akka.actor.{ActorRef, Props}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import com.padel.http.WebServer.system
import org.scalatest.{Matchers, WordSpec}

class WebServerSpec extends WordSpec with Matchers with ScalatestRouteTest {

  val route = new Routes() {

    override def actorRef: ActorRef =
      system.actorOf(Props[GetPlayersActor])
  }

  "Service" should {
    "Return list player" in {
      Get("/players") ~> route.route ~> check {
        status.intValue() shouldBe 200
      }
    }

  }

}
