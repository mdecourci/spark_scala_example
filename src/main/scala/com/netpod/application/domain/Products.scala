package com.netpod.application.domain

import com.netpod.database.BaseTable
import slick.driver.JdbcDriver
import slick.driver.JdbcProfile
import slick.lifted._

import scala.collection.mutable.ListBuffer
import scala.concurrent.ExecutionContext
import scala.concurrent._
import scala.concurrent.duration._

//import slick.driver.JdbcDriver.profile.api._
import slick.driver.MySQLDriver.profile.api._
import slick.lifted.{TableQuery, Tag}
import scala.util.{Random, Failure, Success}
import ExecutionContext.Implicits.global

/**
 * Created by michaeldecourci on 23/09/15.
 */
class ProductTable(tag: Tag) extends Table[Product](tag, "PRODUCTS") {
  def skuCode = column[String]("SKU_CODE", O.PrimaryKey)

  def description = column[String]("DESCRIPTION")

  def shortName = column[String]("SHORT_NAME")

  def * = (skuCode, description, shortName) <>(Product.tupled, Product.unapply)
}

class Products(d : Database) extends BaseTable[Product, ProductTable] {
  import Products._
  override val db: Database = d
  override lazy val table: TableQuery[ProductTable] = TableQuery[ProductTable]

  def load: Seq[Product] = {
    val productItems : Seq[Product] = for (i <- 1 to total) yield Product("SKU" + i, "Product Item_" + random.nextInt(999) + i, "Item_" + random.nextInt(999) + i)
    productItems
   }

  def findCodes : Seq[String] = {
    val skuQuery = for (p <- this.table) yield p.skuCode
    val skuQueryResult = skuQuery.result

    var results : ListBuffer[String] = ListBuffer()
    val f: Future[Seq[String]] = db.run(skuQueryResult).map(r => results++=r)

    Await.result(f, Duration.Inf)
    results
  }
}

object Products {
  val total = 1000000
  def codes : Seq[String] = for (i <- 1 to total) yield "SKU" + i
}
