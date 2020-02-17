//package com.example
//
//import com.example.utils.DTO
//import spray.json.{DefaultJsonProtocol, JsArray, JsNumber, JsObject, JsString, JsValue, JsonFormat, _}
//
//object CustomJsonProtocol extends CustomJsonProtocol
//
//trait CustomJsonProtocol extends DefaultJsonProtocol{
//
////  implicit object EntityJsonFormatter extends JsonFormat[DTO.Entity] {
////    def write(c: DTO.Entity) = JsObject(
////      "id" -> JsString(c.id),
////      "name" -> JsString(c.name))
////    def read(value: JsValue) = {
////      value.asJsObject.getFields("id", "name") match {
////        case Seq(JsString(id), JsString(name)) =>
////          DTO.Entity(id, name)
////        case _ => throw DeserializationException("Entity expected")
////      }
////    }
////  }
//
////  implicit object QueryResultJsonFormatter extends JsonFormat[DTO.QueryResult] {
////    def write(c: DTO.QueryResult) = JsObject(
////      "id" -> JsString(c.id),
////      "name" -> JsString(c.name),
////      "matched_company_id" -> JsString(c.matched_company_id),
////      "matched_company" -> JsString(c.matched_company))
////    def read(value: JsValue) = {
////      value.asJsObject.getFields("id", "name", "matched_company_id", "matched_company") match {
////        case Seq(JsString(id), JsString(name), JsString(matched_company_id), JsString(matched_company)) =>
////          DTO.QueryResult(id, name, matched_company_id, matched_company)
////        case _ => throw DeserializationException("QueryResult expected")
////      }
////    }
////  }
//
////  implicit object UserDataJsonFormatter extends JsonFormat[DTO.UserData] {
////    def write(c: DTO.UserData) = JsObject(
////      "threshold" -> JsNumber(c.threshold),
////      "userRecords" -> JsArray(c.userRecords.map(_.toJson).toVector))
////    def read(value: JsValue) = {
////      value.asJsObject.getFields("threshold", "userRecords") match {
////        case Seq(JsNumber(threshold), JsArray(userRecords)) =>
////          DTO.UserData(threshold.toDouble, userRecords.map(_.convertTo[DTO.Entity]))
////        case _ => throw DeserializationException("UserData expected")
////      }
////    }
////  }
//
////  implicit object SessionResultJsonFormatter extends JsonFormat[DTO.SessionResult] {
////    def write(c: DTO.SessionResult) = JsObject(
////      "status" -> JsString(c.status),
////      "queriesResult" -> JsArray(c.queriesResult.getOrElse(Seq()).map(_.toJson).toVector))
////    def read(value: JsValue) = {
////      value.asJsObject.getFields("status", "queriesResult") match {
////        case Seq(JsString(status), JsArray(queriesResult)) =>
////          DTO.SessionResult(status, Some(queriesResult.map(_.convertTo[DTO.QueryResult])))
////        case _ => throw DeserializationException("SessionResult expected")
////      }
////    }
////  }
//
//}
