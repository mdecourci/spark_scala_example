package com.netpod.application.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import spray.json._

/**
 * Created by michaeldecourci on 23/09/15.
 */
case class Sale(productId: String, soldPrice: Double, tillId: String, shopId: String, regionId: String, timeOfSale: LocalDateTime, id: Long = 0L)

object SaleJsonProtocol extends DefaultJsonProtocol  {

  val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yy");

  implicit object saleJsonFormat extends RootJsonFormat[Sale] {
    def write(c: Sale) = JsObject(
      "id" -> JsNumber(c.id),
      "productId" -> JsString(c.productId),
      "soldPrice" -> JsNumber(c.soldPrice),
      "tillId" -> JsString(c.tillId),
      "shopId" -> JsString(c.shopId),
      "regionId" -> JsString(c.regionId),
      "timeOfSale" -> JsString(c.timeOfSale.format(formatter))
    )
    def read(value: JsValue) = {
      value.asJsObject.getFields("id", "productId", "soldPrice", "tillId", "shopId", "regionId", "timeOfSale") match {
        case Seq(JsNumber(id), JsString(productId), JsNumber(soldPrice), JsString(tillId), JsString(shopId), JsString(regionId), JsString(timeOfSale)) =>
          new Sale(productId, soldPrice.toDouble, tillId, shopId, regionId, LocalDateTime.parse(timeOfSale, formatter), id.longValue)
        case _ => throw new DeserializationException("Sale json format expected")
      }
    }
  }

}
