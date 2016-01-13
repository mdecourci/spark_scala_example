package com.netpod.application.domain

import java.time.{LocalDate, LocalDateTime}

import com.netpod.database.BaseTable
import com.netpod.retail.domain.SlickExtension._
import slick.driver.JdbcDriver
import slick.driver.JdbcProfile
import slick.lifted._

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent._
import scala.concurrent.{Await, Future}

import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

//import slick.driver.JdbcDriver.profile.api._
import slick.driver.MySQLDriver.profile.api._
import slick.lifted.{TableQuery, Tag}

/**
 * Created by michaeldecourci on 23/09/15.
 */
class TillsTable(tag: Tag) extends Table[Till](tag, "TILLS") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc, O.SqlType("BIGINT"))
  def name = column[String]("NAME")
  def attendant = column[String]("ATTENDANT")
  def loginTime = column[LocalDateTime]("LOGIN_TIME")
  def logOutTime = column[LocalDateTime]("LOGOUT_TIME")
  def shopId = column[String]("SHOP_ID")

  def * = (name, attendant, loginTime, logOutTime, shopId, id) <> (Till.tupled , Till.unapply)
}

class Tills(d : Database) extends BaseTable[Till, TillsTable] {
  import Tills._

  override val db: Database = d
  override lazy val table: TableQuery[TillsTable] = TableQuery[TillsTable]

  def load: Seq[Till] = {
    val today =  LocalDate.now
    val todayShopTills = allocateTills(today, "Location_") ++ allocateTills(today, "SussexLocation_") ++ allocateTills(today, "HamshireLocation_")
    val yesterdayShopTills = allocateTills(today.minusDays(1), "Location_") ++ allocateTills(today.minusDays(1), "SussexLocation_") ++ allocateTills(today.minusDays(1), "HamshireLocation_")
    val dayBeforeYesterdayShopTills = allocateTills(today.minusDays(2), "Location_") ++ allocateTills(today.minusDays(2), "SussexLocation_") ++ allocateTills(today.minusDays(2), "HamshireLocation_")

    todayShopTills ++ yesterdayShopTills ++ dayBeforeYesterdayShopTills
  }

  private def allocateTills(dayInShop : LocalDate, shopLocationPrefix : String): ListBuffer[Till] = {
    var tills : ListBuffer[Till] = ListBuffer()
    val openingDayTime: LocalDateTime = openingTime(dayInShop)
    for (s <- 1 to Shops.totalPerRegion) {
      for (hours <- 1 to 8) {
        for (t <- 1 to numberOfTills) {
          val attendantId = if (hours % 2 == 0) 8 + t else t
          tills = tills += Till("Till_" + t, "A_" + attendantId, openingDayTime.plusHours(hours - 1), openingDayTime.plusHours(hours), shopLocationPrefix + s)
        }
      }
    }
    tills
  }

  def findCodes : Seq[String] = {
    val tillNames = for (t <- this.table) yield t.name
    val tillNamesQueryResult = tillNames.distinct.result

    var results : ListBuffer[String] = ListBuffer()
    val f: Future[Seq[String]] = db.run(tillNamesQueryResult).map(results++=_)

    Await.result(f, Duration.Inf)
    results
  }
}

object Tills {
  val numberOfTills = 8

  def codes : Seq[String] = for (t <- 1 to numberOfTills) yield "Till_" + t

  def openingTime(dayInShop: LocalDate): LocalDateTime = {
    LocalDateTime.of(dayInShop.getYear, dayInShop.getMonthValue, dayInShop.getDayOfMonth, 9, 0, 0)
  }
}