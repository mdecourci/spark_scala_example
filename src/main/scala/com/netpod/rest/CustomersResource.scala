package com.netpod.rest

import akka.actor.ActorSystem
import akka.event.Logging
import com.netpod.rest.domain.{Customer, Customers}
import com.netpod.rest.domain.CustomerJsonProtocol._
import com.netpod.rest.service.CustomerService
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.SparkConf
import spray.http.{MediaTypes, StatusCode, StatusCodes}
import spray.httpx.SprayJsonSupport
import spray.routing.directives.DebuggingDirectives
import spray.routing.{Route, SimpleRoutingApp}

/**
 * Created by michaeldecourci on 27/07/15.
 */
object CustomersResource extends App with SimpleRoutingApp with SprayJsonSupport with LazyLogging {
  implicit val system = ActorSystem("my-system")

  val sparkConf = new SparkConf()
    .setAppName("Scala API demo")
    .setMaster("local[2]") // 2 cores
    .set("spark.cassandra.connection.host","127.0.0.1")

  val customerService = new CustomerService(sparkConf);

  lazy val getHelloRoute = get {
    path("hello") {
      complete(<h1>Say hello to spray</h1>)
    }
  }

  lazy val getCustomerRoute = get {
    path("customers" / IntNumber) { index =>
      respondWithMediaType(MediaTypes.`application/json`)  {
        logger.debug("getCustomerRoute - index={}", Int.box(index))
        complete   {
          Customers.toJson(customerService.find(index))
        }
      }
    }
  }

  lazy val getCustomersRoute = get {
    path("customers") {
      parameter("lastName"?) {
        (name) =>
          logger.debug("getCustomersRoute - lastName={}", name)

          respondWithMediaType(MediaTypes.`application/json`) {

            complete {

              if(name.isEmpty)
              { Customers.toJson(customerService.findAll) }
              else
              { Customers.toJson(customerService.find(c => c.lastName.equals(name.get))) }

            }
          }
      }
    }
  }

  lazy val postCustomerRoute = post {
    path("customers") {
      entity(as[Customer]) { customer =>
        respondWithMediaType(MediaTypes.`application/json`) {

          logger.debug("postCustomerRoute - customer={}", customer)

          customerService.save(customer)

          respondWithStatus(StatusCodes.Created) {
            complete(customer)
          }
        }
      }
    }
  }

    startServer(interface = "localhost", port = 9080) {
      getHelloRoute ~
      getCustomersRoute ~
      getCustomerRoute ~
      postCustomerRoute
  }

}
