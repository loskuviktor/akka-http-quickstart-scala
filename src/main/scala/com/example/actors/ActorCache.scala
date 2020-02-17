package com.example.actors

import akka.actor.{Actor, PoisonPill, Props}
import com.example.messages.ActorCache.{AddCachedRecord, GetCachedRecord, ReturnCachedRecord}
import com.example.utils.DTO.QueryResult

object ActorCache {
  def props(event: String): Props = Props(new ActorCache(event))
}

class ActorCache (event: String) extends Actor {

  // session_id + processed_records
  var cachedRecords = Map.empty[String, Option[Seq[QueryResult]]]

  def receive: PartialFunction[Any, Unit] = {

    case AddCachedRecord(sessionId, records) => cachedRecords = cachedRecords + (sessionId -> records)

    case GetCachedRecord(sessionId) =>
      sender() ! ReturnCachedRecord(sessionId, cachedRecords.getOrElse(sessionId, None))
  }
}