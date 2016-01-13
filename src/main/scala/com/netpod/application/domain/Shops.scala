package com.netpod.application.domain

import com.netpod.database.BaseTable
import slick.driver.JdbcDriver
import slick.driver.JdbcProfile
import slick.lifted._

import scala.collection.mutable.ListBuffer
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._
import ExecutionContext.Implicits.global

//import slick.driver.JdbcDriver.profile.api._
import slick.driver.MySQLDriver.profile.api._
import slick.lifted.{TableQuery, Tag}

/**
 * Created by michaeldecourci on 24/09/15.
 */

class ShopTable(tag: Tag) extends Table[Shop](tag, "SHOPS") {
  def id = column[String]("ID", O.PrimaryKey)

  def location = column[String]("LOCATION")

  def regionId = column[String]("REGION_ID")

  def * = (id, location, regionId) <>(Shop.tupled, Shop.unapply)
}

class Shops(d : Database) extends BaseTable[Shop, ShopTable] {
  import Shops._
  override val db: Database = d
  override lazy val table: TableQuery[ShopTable] = TableQuery[ShopTable]

  def findIds : Seq[String] = {
    val shopIds = for (r <- this.table) yield r.id
    val shopIdsQueryResult = shopIds.distinct.result

    var results : ListBuffer[String] = ListBuffer()
    val f: Future[Seq[String]] = db.run(shopIdsQueryResult).map(results++=_)

    Await.result(f, Duration.Inf)
    results.reverse.slice(1,9).reverse
  }

  def load: Seq[Shop] = {
    val surreyShops : Seq[Shop] = for (i <- 1 to totalPerRegion) yield Shop("ShopA_" + i, "Location_" + i, "Surrey")
    val sussexShops : Seq[Shop] = for (i <- 1 to totalPerRegion) yield Shop("SussexShopA_" + i, "SussexLocation_" + i, "Sussex")
    val hantsShops : Seq[Shop] = for (i <- 1 to totalPerRegion) yield Shop("HamshireShopA_" + i, "HamshireLocation_" + i, "Hamshire")

    surreyShops++(sussexShops++hantsShops)
  }
}
object Shops {
  val totalPerRegion = 100
}

