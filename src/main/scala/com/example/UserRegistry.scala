package com.example

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.example.utils.DTO.{Entity, SessionResult, UserData}
import com.example.utils.{DTO, Matcher}

// rename to Router
object UserRegistry {
  sealed trait Command
  final case class CreateSession(userData: UserData, replyTo: ActorRef[SessionResult]) extends Command

  var companies: Seq[DTO.Entity] = Seq()

  def apply(companiesSeq: Seq[DTO.Entity]): Behavior[Command] = {
    this.companies = companiesSeq
    registry()
  }

  private def registry(): Behavior[Command] =
    Behaviors.receiveMessage {
      case CreateSession(userData, replyTo) =>
        replyTo ! processData(userData, this.companies)
        Behaviors.same
    }

  // process user data in a single thread. to show a progress bar 1 actor cal per user record is required
  private def processData(userData: UserData, companies: Seq[Entity]): SessionResult =
      if (userData.userRecords.isEmpty) {
        SessionResult("Completed", None)
      } else {
        val queriesResult = userData.userRecords.map(entity => {
          val bestMatch = Matcher.matchCompanies(entity.name, companies, userData.threshold)
            .map(sm => DTO.Entity(sm.id, sm.name)).getOrElse(DTO.Entity("",""))
          DTO.QueryResult(entity.id, entity.name, bestMatch.id, bestMatch.name)
        })

        SessionResult("Completed", Some(queriesResult))
      }
}
