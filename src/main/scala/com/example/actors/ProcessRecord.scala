package com.example.actors

import java.util.UUID.randomUUID

import akka.actor.{Actor, Props}
import com.example.messages.ProcessRecord.{AddUserDataToProcess, GetStatusOrResult, ReturnSession, ReturnStatusOrResult}
import com.example.utils.DTO.{Entity, QueryResult, UserData}
import com.example.utils.{DTO, Matcher}

object ProcessRecord {
  def props(event: String): Props = Props(new ProcessRecord(event))
}

class ProcessRecord (event: String) extends Actor {

  var isCompleted: Boolean = false
  var queriesResult: Option[Seq[QueryResult]] = None
  var session_id: String = ""

  private def processData(userData: UserData, companies: Seq[Entity]): Option[Seq[QueryResult]] =
    if (userData.userRecords.isEmpty) {
      None
    } else {
      val queriesResult = userData.userRecords.map(entity => {
        val DTO.Entity(id, name) = Matcher.matchCompanies(entity.name, companies, userData.threshold)
          .map(sm => DTO.Entity(sm.id, sm.name)).getOrElse(DTO.Entity("",""))
        DTO.QueryResult(entity.id, entity.name, id, name)
      })

      Some(queriesResult)
    }

  def receive: PartialFunction[Any, Unit] = {

    case GetStatusOrResult(session_id) =>
      if (!isCompleted) sender() ! ReturnStatusOrResult(s"${queriesResult.map(_.size).getOrElse(0)} records are processed!", None)
      else sender() ! ReturnStatusOrResult("All records are processed!", queriesResult)

    case AddUserDataToProcess(user_data, companies) => {
      this.session_id = randomUUID.toString
      this.queriesResult = processData(user_data, companies)
      sender() ! ReturnSession(this.session_id)
    }
  }
}