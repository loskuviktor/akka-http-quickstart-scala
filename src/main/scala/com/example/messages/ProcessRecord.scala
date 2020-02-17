package com.example.messages

import com.example.utils.DTO
import com.example.utils.DTO.QueryResult

object ProcessRecord {

  case class GetStatusOrResult(session_id: String)
  case class AddUserDataToProcess(user_data: DTO.UserData, companies: Seq[DTO.Entity])

  case class ReturnStatusOrResult(status: String, query_result: Option[Seq[QueryResult]])
  case class ReturnSession(session_id: String)
}
