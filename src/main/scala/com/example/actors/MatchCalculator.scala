package com.example.actors

import akka.actor.{Actor, Props}
import com.example.messages.MatchCalculator.{GetUserRecord, ReturnUserRecord}
import com.example.utils.DTO.{Entity, QueryResult}
import com.example.utils.{DTO, Matcher}

object MatchCalculator {
  def props(companies: Seq[Entity]): Props = Props(new MatchCalculator(companies))
}

class MatchCalculator(companies: Seq[Entity]) extends Actor {

  def receive(): PartialFunction[Any, Unit] = {
    case GetUserRecord(threshold, userRecord) =>
      sender() ! ReturnUserRecord(processData(threshold, userRecord))
  }

  private def processData(threshold: Double, userRecord: Entity): QueryResult = {
    val DTO.Entity(id, name) = Matcher.matchCompanies(userRecord.name, companies, threshold)
      .map(sm => DTO.Entity(sm.id, sm.name)).getOrElse(DTO.Entity("",""))
    DTO.QueryResult(userRecord.id, userRecord.name, id, name)
  }
}
