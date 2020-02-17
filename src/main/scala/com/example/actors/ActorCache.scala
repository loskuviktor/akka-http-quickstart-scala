package com.example.actors

import akka.actor.{Actor, PoisonPill, Props}
import com.example.messages.ActorCache.{AddCachedRecord, GetCachedRecord, ReturnCachedRecord}
import com.example.utils.DTO.QueryResult

object ActorCache {
  def props(event: String): Props = Props(new ActorCache(event))
}

class ActorCache (event: String) extends Actor {

  import context._

  // session_id + processed_records
//  var cachedRecords = Map.empty[String, Option[Seq[QueryResult]]]

  //todo: check if 1 receive method is possible
  def receive(): PartialFunction[Any, Unit] = {
    receiveMap(Map.empty)
  }

  def receiveMap(cachedRecords: Map[String, Option[Seq[QueryResult]]]): PartialFunction[Any, Unit] = {

    case AddCachedRecord(sessionId, records) =>
      become(receiveMap(cachedRecords + (sessionId -> records)))

    case GetCachedRecord(sessionId) =>
      sender() ! ReturnCachedRecord(sessionId, cachedRecords.getOrElse(sessionId, None))
  }
}