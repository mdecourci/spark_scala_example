package com.netpod.rest

import akka.actor.ActorSystem
import com.netpod.rest.domain.CustomerJsonProtocol._
import com.netpod.rest.domain.{Customer, Customers}
import com.netpod.rest.service.CustomerService
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.SparkConf
import spray.http.{MediaTypes, StatusCodes}
import spray.httpx.SprayJsonSupport
import spray.routing.SimpleRoutingApp

/**
 * Created by michaeldecourci on 27/07/15.
 */
object ReportingResource extends App with SimpleRoutingApp with SprayJsonSupport with LazyLogging {
  implicit val system = ActorSystem("my-system")

  val sparkConf = new SparkConf()
    .setAppName("Scala API demo")
    .setMaster("local[2]") // 2 cores
    .set("spark.cassandra.connection.host","127.0.0.1")

  val salesReporter = new SalesReporter();

  lazy val getHelloRoute = get {
    path("hello") {
      complete(<h1>Say hello to spray</h1>)
    }
  }

  lazy val getNumberOfSales = get {
    path("sales") {
      respondWithMediaType(MediaTypes.`application/json`)  {
        logger.debug("getNumberOfSales")
        complete   {
          Reports.toJson(salesReporter.salesCount)
        }
      }
    }
  }

    startServer(interface = "localhost", port = 9080) {
      getHelloRoute ~
        getNumberOfSales
  }

}
