package com.example.messages

import akka.actor.Props
import com.example.actors.ActorCache
import com.example.utils.DTO.QueryResult

object ActorCache {

  def props(event: String): Props = Props(new ActorCache(event))

  case class AddCachedRecord(session_id: String, records: Option[Seq[QueryResult]])
  case class GetCachedRecord(session_id: String)

  case class ReturnCachedRecord(session_id: String, records: Option[Seq[QueryResult]])
}
