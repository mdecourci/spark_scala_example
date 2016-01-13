package com.netpod.application.domain

import spray.json._

/**
 * Created by michaeldecourci on 24/09/15.
 */
case class Shop (id: String, location: String, regionId: String)

object ShopJsonProtocol extends DefaultJsonProtocol  {

  implicit val shopJsonFormat = jsonFormat3(Shop)

}
