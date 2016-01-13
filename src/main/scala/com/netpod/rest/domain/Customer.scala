package com.netpod.rest.domain

import org.json4s.ShortTypeHints
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization._
import spray.httpx.unmarshalling.FromRequestUnmarshaller
import spray.json.DefaultJsonProtocol

/**
 * Created by michaeldecourci on 27/07/15.
 */
case class Customer(firstName : String, lastName : String)

object CustomerJsonProtocol extends DefaultJsonProtocol{
  implicit val customerFormat = jsonFormat2(Customer)
}
object Customers {
  val customers = List[Customer] (
    new Customer("John", "Smith"),
    new Customer("Annie", "Hall"),
    new Customer("Jenny", "Smith"),
    new Customer("James", "Bond")
  )

  private implicit val formats = Serialization.formats(ShortTypeHints(List()))
  def toJson(customers : List[Customer]) : String = writePretty(customers)
  def toJson(customer : Customer) : String = writePretty(customer)

}
