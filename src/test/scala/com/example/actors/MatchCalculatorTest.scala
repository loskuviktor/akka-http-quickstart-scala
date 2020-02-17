package com.example.actors

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.example.messages.MatchCalculator.{GetUserRecord, ReturnUserRecord}
import com.example.utils.{DTO, Util}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class MatchCalculatorTest extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "An ActorCache actor" must {

    "find a matched record if such exists" in {
      val cache = system.actorOf(MatchCalculator.props(Util.readResourceFile("companies.csv")))
      cache ! GetUserRecord(1D, DTO.Entity("4047907","Jesus House"))
      expectMsg(ReturnUserRecord(DTO.QueryResult("4047907","Jesus House", "4047907", "Jesus House")))
    }

    "fill with empty fields if it doesn't exist" in {
      val cache = system.actorOf(MatchCalculator.props(Util.readResourceFile("companies.csv")))
      cache ! GetUserRecord(0.5D, DTO.Entity("4047907","Mouse House"))
      expectMsg(ReturnUserRecord(DTO.QueryResult("4047907","Mouse House", "", "")))
    }
  }
}