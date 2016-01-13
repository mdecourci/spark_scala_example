package com.netpod.rest

import com.netpod.rest.domain.{Customer, Customers}
import org.scalatest.{Matchers, FlatSpec}
import spray.http.{MediaTypes, HttpEntity, HttpHeaders}
import spray.httpx.SprayJsonSupport
import spray.routing.{HttpService, Directives}
import spray.testkit.ScalatestRouteTest
import com.netpod.rest.domain.CustomerJsonProtocol._
import spray.http.StatusCodes.OK
import spray.http.StatusCodes.Created

/**
 * Created by michaeldecourci on 27/07/15.
 */
class CustomersResourceTest extends FlatSpec with Matchers with ScalatestRouteTest with Directives with SprayJsonSupport {
  def actorRefFactory = system // connect the DSL to the test ActorSystem
//  val testHost = new HttpHeaders.Host("localhost", 9080)
//  implicit val  defaulthost = new DefaultHostInfo(testHost, false)

  "the get customers service" should "return customers" in {
    Get("/customers") ~> CustomersResource.getCustomersRoute ~> check {
      status should equal(OK)
      var actualCustomers = responseAs[List[Customer]]
      actualCustomers(1) should equal(Customers.customers(1))
    }
  }

  "the get customer service" should "return a customer" in {
    Get("/customers/1") ~> CustomersResource.getCustomerRoute ~> check {
      status should equal(OK)
      responseAs[Customer] should equal(Customers.customers(1))
    }
  }

  "the post customer service" should "create a customer" in {
    Post("/customers", HttpEntity(MediaTypes.`application/json`, """{ "firstName": "Fiona", "lastName" : "Jones" }""" )) ~> CustomersResource.postCustomerRoute ~> check {
      status should equal(Created)
      responseAs[Customer].firstName should equal("Fiona")
      responseAs[Customer].lastName should equal("Jones")
    }
  }
}
