package com.netpod.application.domain

import spray.json._

/**
 * Created by michaeldecourci on 23/09/15.
 */
case class Product(skuCode: String, description: String, shortName: String)

object ProductJsonProtocol extends DefaultJsonProtocol  {

  implicit val productJsonFormat = jsonFormat3(Product)

}
