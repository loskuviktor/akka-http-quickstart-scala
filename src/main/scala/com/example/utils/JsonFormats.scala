package com.example.utils

import com.example.utils.DTO.{Entity, QueryResult, SessionResult, UserData}
import spray.json.DefaultJsonProtocol

object JsonFormats  {
  // import the default encoders for primitive types (Int, String, Lists etc)
  import DefaultJsonProtocol._

  implicit val EntityFormat = jsonFormat2(Entity)
  implicit val QueryResultForamt = jsonFormat4(QueryResult)

  implicit val userDataFormat = jsonFormat2(UserData)
  implicit val sessionResultFormat = jsonFormat2(SessionResult)

}
