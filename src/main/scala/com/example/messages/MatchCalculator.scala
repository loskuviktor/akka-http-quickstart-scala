package com.example.messages

import com.example.utils.DTO.{Entity, QueryResult}

object MatchCalculator {

  case class GetUserRecord(threshold: Double, userRecord: Entity)

  case class ReturnUserRecord(queryResult: QueryResult)
}
