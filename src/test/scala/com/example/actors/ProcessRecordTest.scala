package com.example.actors

import java.util.UUID.randomUUID

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import com.example.messages.ProcessRecord.{AddUserDataToProcess, GetStatusOrResult, ReturnStatusOrResult}
import com.example.utils.DTO.Entity
import com.example.utils.{DTO, Util}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ProcessRecordTest extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "An ProcessRecord actor" must {

    val sessionId = randomUUID.toString

    "find a matched record if such exists and return to a user" in {
      val matchCalculator = system.actorOf(MatchCalculator.props(Util.readResourceFile("companies.csv")))
      val cache = system.actorOf(ActorCache.props("cache"))

      val processRecord = system.actorOf(ProcessRecord.props(sessionId, matchCalculator, cache))
      processRecord ! AddUserDataToProcess(DTO.UserData(0.9D, Seq(Entity("4047907","Jesus House"))))
      processRecord ! GetStatusOrResult(sessionId)
      expectMsg(ReturnStatusOrResult("0.0% is processed!", None))
      Thread.sleep(1000)
      processRecord ! GetStatusOrResult(sessionId)
      expectMsg(ReturnStatusOrResult("100% is processed!", Some(Seq(DTO.QueryResult("4047907","Jesus House","4047907","Jesus House")))))
    }
  }
}