package com.example.actors

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestActors, TestKit}
import com.example.messages.ActorCache.{AddCachedRecord, GetCachedRecord, ReturnCachedRecord}
import com.example.utils.DTO.QueryResult
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpecLike

class ActorCacheTest extends TestKit(ActorSystem("MySpec"))
  with ImplicitSender
  with AnyWordSpecLike
  with Matchers
  with BeforeAndAfterAll {

  override def afterAll: Unit = {
    TestKit.shutdownActorSystem(system)
  }

  "An ActorCache actor" must {

    "send back cached messages unchanged" in {
      val cache = system.actorOf(ActorCache.props)
      cache ! AddCachedRecord("123", Some(Seq(QueryResult("id", "name", "id2", "name2"))))
      cache ! GetCachedRecord("123")
      expectMsg(ReturnCachedRecord("123", Some(Seq(QueryResult("id", "name", "id2", "name2")))))
    }

    "send back empty messages for non-existing sessions" in {
      val cache = system.actorOf(ActorCache.props)
      cache ! AddCachedRecord("123", Some(Seq(QueryResult("id", "name", "id2", "name2"))))
      cache ! GetCachedRecord("124")
      expectMsg(ReturnCachedRecord("124", None))
    }
  }
}