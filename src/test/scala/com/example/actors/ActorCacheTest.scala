package com.example.actors

import org.scalatest.{Matchers, WordSpec}

class ActorCacheTest extends WordSpec with Matchers {

  //todo: add actor test as dependency (akka test possible)
//
//  "A Greeter" must {
//    //#test
//    "reply to greeted" in {
//      val replyProbe = createTestProbe[Greeted]()
//      val underTest = spawn(Greeter())
//      underTest ! Greet("Santa", replyProbe.ref)
//      replyProbe.expectMessage(Greeted("Santa", underTest.ref))
//    }
//    //#test
//  }
}
