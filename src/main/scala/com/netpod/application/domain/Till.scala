package com.netpod.application.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import spray.json._

/**
 * Created by michaeldecourci on 23/09/15.
 */
case class Till(name: String, attendant: String, loginTime: LocalDateTime, logOutTime: LocalDateTime, shopId: String, id: Long = 0L)

object TillJsonProtocol extends DefaultJsonProtocol  {

  val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yy")

  implicit object tillJsonFormat extends RootJsonFormat[Till] {
    def write(t: Till) = JsObject(
      "id" -> JsNumber(t.id),
      "name" -> JsString(t.name),
      "attendant" -> JsString(t.attendant),
      "loginTime" -> JsString(t.loginTime.format(formatter)),
      "logOutTime" -> JsString(t.logOutTime.format(formatter)),
      "shopId" -> JsString(t.shopId)
    )
    def read(value: JsValue) = {
      value.asJsObject.getFields("name", "attendant", "loginTime", "logOutTime", "shopId", "id") match {
        case Seq(JsString(name), JsString(attendant), JsString(loginTime), JsString(logOutTime), JsString(shopId), JsNumber(id)) =>
          new Till(name, attendant, LocalDateTime.parse(loginTime, formatter), LocalDateTime.parse(logOutTime, formatter), shopId, id.longValue)
        case _ => throw new DeserializationException("Till json format expected")
      }
    }
  }

}