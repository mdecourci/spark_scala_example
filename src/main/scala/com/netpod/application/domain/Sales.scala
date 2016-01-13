package com.netpod.application.domain

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import com.netpod.database.BaseTable
import slick.driver.JdbcDriver
import slick.driver.JdbcProfile
import slick.lifted._
//import slick.driver.JdbcDriver.profile.api._
import slick.driver.MySQLDriver.profile.api._
import slick.lifted.{TableQuery, Tag}
import spray.json._
import com.netpod.retail.domain.SlickExtension._

/**
 * Created by michaeldecourci on 18/09/15.
 */
class SalesTable(tag: Tag) extends Table[Sale](tag, "SALES") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc, O.SqlType("BIGINT"))
  def productId = column[String]("PRODUCT_SOLD_ID")
  def soldPrice = column[Double]("SOLD_PRICE")
  def tillId = column[String]("TILL_ID")
  def shopId = column[String]("SHOP_ID")
  def regionId = column[String]("REGION_ID")
  def timeOfSale = column[LocalDateTime]("TIME_OF_SALE")

  def * = (productId,  soldPrice, tillId, shopId, regionId, timeOfSale, id) <> (Sale.tupled , Sale.unapply)
}

class Sales(d : Database) extends BaseTable[Sale, SalesTable] {
  override val db: Database = d
  override lazy val table: TableQuery[SalesTable] = TableQuery[SalesTable]
}
