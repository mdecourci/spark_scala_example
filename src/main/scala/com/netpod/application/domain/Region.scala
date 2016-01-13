package com.netpod.application.domain

import spray.json._

/**
 * Created by michaeldecourci on 24/09/15.
 */
case class Region(name: String, id: Long = 0L)

object RegionJsonProtocol extends DefaultJsonProtocol  {

  implicit val regionJsonFormat = jsonFormat2(Region)

}