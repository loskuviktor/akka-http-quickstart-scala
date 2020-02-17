package com.example.messages

import com.example.utils.DTO.QueryResult

object ActorCache {

  case class AddCachedRecord(session_id: String, records: Option[Seq[QueryResult]])
  case class GetCachedRecord(session_id: String)

  case class ReturnCachedRecord(session_id: String, records: Option[Seq[QueryResult]])
}
