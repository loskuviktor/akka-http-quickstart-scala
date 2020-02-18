package com.example.actors

import java.util.concurrent.TimeUnit

import akka.actor.{Actor, ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout
import com.example.messages
import com.example.messages.MatchCalculator.ReturnUserRecord
import com.example.messages.ProcessRecord.{AddUserDataToProcess, GetStatusOrResult, ReturnStatusOrResult}
import com.example.utils.DTO.QueryResult

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.Success

object ProcessRecord {
  def props(sessionId: String, matchCalculator: ActorRef, actorCache: ActorRef): Props =
    Props(new ProcessRecord(sessionId, matchCalculator, actorCache))
}

class ProcessRecord (sessionId: String, matchCalculator: ActorRef, actorCache: ActorRef) extends Actor {

   var requestsFutures: Seq[Future[Any]] = Seq()

   implicit val timeout: Timeout = Timeout(10, TimeUnit.SECONDS)

   def receive: PartialFunction[Any, Unit] = {

    case GetStatusOrResult(session_id) =>
      val readyRequests = this.requestsFutures.map(_.isCompleted).count(_ == true)
      val commonCount = this.requestsFutures.size
      val percentage: Double = if (readyRequests == commonCount) 100D else 100D / commonCount * readyRequests

      if (percentage != 100D) sender() ! ReturnStatusOrResult(s"$percentage% is processed!", None)
      else {
        sender() ! ReturnStatusOrResult("100% is processed!", Some(completeFutures(this.requestsFutures)))
        actorCache ! messages.ActorCache.AddCachedRecord(session_id, Some(completeFutures(this.requestsFutures)))
        context.stop(self)
      }

    case AddUserDataToProcess(user_data) =>
      this.requestsFutures = user_data.userRecords
        .map(entity => matchCalculator ? messages.MatchCalculator.GetUserRecord(user_data.threshold, entity))
   }

   protected def completeFutures(futures: Seq[Future[Any]]): Seq[QueryResult] = {
     val seq = Future.sequence(futures.map(_.transform(Success(_))))
     val successes = seq.map(_.collect{case Success(x)=>x})
     Await.result(successes, 1 second).map(_.asInstanceOf[ReturnUserRecord]).map(_.queryResult)
   }
}