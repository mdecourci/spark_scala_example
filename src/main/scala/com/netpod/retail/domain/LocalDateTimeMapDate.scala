package com.netpod.retail.domain

import java.sql.{Timestamp, Date}
import java.time.{LocalDate, ZoneOffset, LocalDateTime}
import slick.driver.H2Driver.api._
//import slick.driver.MySQLDriver.api._
/**
 * Created by michaeldecourci on 14/08/15.
 */

//class LocalDateTimeMapDate(localDateTime: LocalDateTime) extends MappedTo[Date] {
//  override def value: Timestamp = new Timestamp(localDateTime.getNano*1000)
//}

object SlickExtension {

  /**
   * map between java.time.LocalDateTime and sql.Timestamp
   */
  implicit val JavaLocalDateTimeMapper = MappedColumnType.base[LocalDateTime, Timestamp](
    d => Timestamp.from(d.toInstant(ZoneOffset.ofHours(0))),
    d => d.toLocalDateTime
  )

  /**
   * map between java.time.LocalDate and sql.Date
   */
  implicit val JavaLocalDateMapper = MappedColumnType.base[LocalDate, Date](
    d => Date.valueOf(d),
    d => d.toLocalDate
  )
}