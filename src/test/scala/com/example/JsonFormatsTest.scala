package com.example

import com.example.utils.DTO.{Entity, QueryResult, SessionResult, UserData}
import org.scalatest.{Matchers, WordSpec}
import spray.json._
import DefaultJsonProtocol._
import com.example.utils.JsonFormats._

class JsonFormatsTest extends WordSpec with Matchers{

  "UserData" should {
    "convert to String" in {
      val userData = UserData(0.5D, Seq(
        Entity("4047907", "Jesus House"),
        Entity("3274858", "Alchemy Venture Partners Limited")
      ))
      userData.toJson.toString() shouldBe("{\"threshold\":0.5,\"userRecords\":[{\"id\":\"4047907\",\"name\":\"Jesus House\"},{\"id\":\"3274858\",\"name\":\"Alchemy Venture Partners Limited\"}]}")
    }

    "extract from String" in {
      val userData = "{\"threshold\":0.5,\"userRecords\":[{\"id\":\"4047907\",\"name\":\"Jesus House\"},{\"id\":\"3274858\",\"name\":\"Alchemy Venture Partners Limited\"}]}"
      val actualRes = userData.parseJson.convertTo[UserData]
      val expectedRes = UserData(0.5D, Seq(
        Entity("4047907", "Jesus House"),
        Entity("3274858", "Alchemy Venture Partners Limited")
      ))
      actualRes shouldBe(expectedRes)
    }
  }

  "SessionResult" should {
    "convert to String" in {
      val sessionResult = SessionResult("Completed", Some(Seq(
        QueryResult("4047907", "Jesus House", "4047907", "Jesus House"),
        QueryResult("3274858", "Alchemy Venture Partners Limited", "3274858", "Alchemy Venture Partners Limited")
      )))
      sessionResult.toJson.toString() shouldBe("{\"queriesResult\":[{\"id\":\"4047907\",\"matched_company\":\"Jesus House\",\"matched_company_id\":\"4047907\",\"name\":\"Jesus House\"},{\"id\":\"3274858\",\"matched_company\":\"Alchemy Venture Partners Limited\",\"matched_company_id\":\"3274858\",\"name\":\"Alchemy Venture Partners Limited\"}],\"status\":\"Completed\"}")
    }

    "extract from String" in {
      val userData = "{\"queriesResult\":[{\"id\":\"4047907\",\"matched_company\":\"Jesus House\",\"matched_company_id\":\"4047907\",\"name\":\"Jesus House\"},{\"id\":\"3274858\",\"matched_company\":\"Alchemy Venture Partners Limited\",\"matched_company_id\":\"3274858\",\"name\":\"Alchemy Venture Partners Limited\"}],\"status\":\"Completed\"}"
      val actualRes = userData.parseJson.convertTo[SessionResult]
      val expectedRes = SessionResult("Completed", Some(Seq(
        QueryResult("4047907", "Jesus House", "4047907", "Jesus House"),
        QueryResult("3274858", "Alchemy Venture Partners Limited", "3274858", "Alchemy Venture Partners Limited")
      )))
      actualRes shouldBe(expectedRes)
    }
  }
}