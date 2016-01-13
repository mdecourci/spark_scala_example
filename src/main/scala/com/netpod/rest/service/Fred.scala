package com.netpod.rest.service

/**
 * Created by michaeldecourci on 17/10/15.
 */

import akka.actor.ActorSystem
import akka.event.{LoggingAdapter, Logging}
import com.netpod.rest.CustomersResource._
import com.netpod.rest.domain.{Customers, Customer}
import com.netpod.rest.domain.CustomerJsonProtocol._
import com.netpod.rest.service.CustomerService
import com.typesafe.config.Config
import com.typesafe.scalalogging.slf4j.LazyLogging
import org.apache.spark.SparkConf
import spray.http.{StatusCodes, StatusCode, MediaTypes}
import spray.httpx.SprayJsonSupport
import spray.routing.directives.DebuggingDirectives
import spray.routing.{Route, SimpleRoutingApp}


trait Service extends App with SimpleRoutingApp with SprayJsonSupport with LazyLogging {
  implicit val system: ActorSystem
  //implicit def executor: ExecutionContextExecutor

  def config: Config

  def persistToDatabase(data: String) = {
  }

  lazy val putCustomerRoute = put {
    path("persist") {
      entity(as[String]) { payload =>
        respondWithMediaType(MediaTypes.`application/json`) {
          logger.debug("putCustomerRoute - payload={}", payload)

          persistToDatabase(payload)

           respondWithStatus(StatusCodes.Created) {
             complete("{}")
           }

        }
      }
    }
  }

  startServer(interface = "localhost", port = 9080) {
      putCustomerRoute
  }

}
