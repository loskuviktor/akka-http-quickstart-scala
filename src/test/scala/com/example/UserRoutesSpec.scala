package com.example

import akka.actor.testkit.typed.scaladsl.ActorTestKit
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.{Matchers, WordSpec}
import akka.actor.typed.scaladsl.adapter._
import com.example.utils.DTO.{Entity, UserData}
import com.example.utils.Util

class UserRoutesSpec extends WordSpec with Matchers with ScalaFutures with ScalatestRouteTest {

  lazy val testKit = ActorTestKit()
  implicit def typedSystem = testKit.system
  override def createActorSystem(): akka.actor.ActorSystem =
    testKit.system.toClassic

  val userRegistry = testKit.spawn(UserRegistry(Util.readResourceFile("companies.csv")))
  lazy val routes = new UserRoutes(userRegistry).userRoutes

  import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
  import com.example.utils.JsonFormats._

  "UserRoutes" should {
    //#testing-post
    "be able to create a session (POST /companies)" in {
      val userData = UserData(0.5D, Seq(
        Entity("4047907", "Jesus House"),
        Entity("3274858", "Alchemy Venture Partners Limited")
      ))
      val userDataEntity = Marshal(userData).to[MessageEntity].futureValue // futureValue is from ScalaFutures

      // using the RequestBuilding DSL:
      val request = Post("/companies").withEntity(userDataEntity)

      request ~> routes ~> check {
        status should ===(StatusCodes.Created)

        contentType should ===(ContentTypes.`application/json`)

        entityAs[String] should ===("""{"queriesResult":[{"id":"4047907","matched_company":"Jesus House","matched_company_id":"4047907","name":"Jesus House"},{"id":"3274858","matched_company":"Alchemy Venture Partners Limited","matched_company_id":"3274858","name":"Alchemy Venture Partners Limited"}],"status":"Completed"}""")
      }
    }
  }

}
