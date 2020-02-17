package com.example.actors

import akka.actor.Actor
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

//class ProcessRecordBot (event: String) extends Actor {
//  def apply(userData: UserData, companies: Seq[Entity]): Behavior[ProcessRecord.ProcessStatus] = {
//    bot(userData, companies)
//  }
//
//  private def bot(userData: UserData, companies: Seq[Entity]): Behavior[ProcessRecord.ProcessStatus] =
//    Behaviors.receive { (context, message) =>
//      context.log.info("{} records are left to process.", userData.userRecords.size)
//      if (userData.userRecords.isEmpty) {
//        Behaviors.stopped
//      } else {
//        val userRecord = userData.userRecords.head
//        val bestMatch = Matcher.matchCompanies(userRecord.name, companies, userData.threshold)
//          .map(sm => DTO.Entity(sm.id, sm.name)).getOrElse(DTO.Entity("",""))
//        val queryResult = DTO.QueryResult(userRecord.id, userRecord.name, bestMatch.id, bestMatch.name)
//        val queriesResult: Option[Seq[DTO.QueryResult]] = if (message.sessionRes.queriesResult.isDefined)
//          message.sessionRes.queriesResult.map(_ :+ queryResult)
//        else
//          Some(Seq(queryResult))
//        val sessionRes = ProcessRecord.SessionResult(s"${userData.userRecords.size - 1} records are left to process", queriesResult)
//        message.sessionRes ! ProcessRecord.ProcessStatus(sessionRes, context.self)
////        message.from ! Greeter.Greet(message.whom, context.self)
//        bot(DTO.UserData(userData.threshold, userData.userRecords.tail), companies)
//      }
//    }
//}
