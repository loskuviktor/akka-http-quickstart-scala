package com.example.utils

object DTO {

  /**
   * Schema of user records and companies
   */
  final case class Entity (id: String, name: String)

  /**
   * Schema of cached data
   */
  final case class QueryResult(
                              id: String,
                              name: String,
                              matched_company_id: String,
                              matched_company: String
                              )

  /**
   * Data sent by users
   */
  final case class UserData(threshold: Double, userRecords: Seq[Entity])
  final case class SessionId(session: String)

  /**
   * Data returned to users
   */
  final case class SessionResult(status: String, queriesResult: Option[Seq[QueryResult]])
}
