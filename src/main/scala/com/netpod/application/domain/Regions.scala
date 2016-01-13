package com.netpod.application.domain

import java.time.LocalDateTime

import com.netpod.application.domain.Region
import com.netpod.database.BaseTable
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
class RegionTable(tag: Tag) extends Table[Region](tag, "REGIONS") {

  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc, O.SqlType("BIGINT"))

  def name = column[String]("NAME", O.PrimaryKey)

  def * = (name, id) <>(Region.tupled, Region.unapply)
}

class Regions(d : Database) extends BaseTable[Region, RegionTable] {
  override val db: Database = d
  override lazy val table: TableQuery[RegionTable] = TableQuery[RegionTable]

  def findCodes : Seq[String] = {
    return List("Hamshire", "Surrey").toSeq
    val regionNames = for (r <- this.table) yield r.name
    val regionNamesQueryResult = regionNames.distinct.result

    var results : ListBuffer[String] = ListBuffer()
    val f: Future[Seq[String]] = db.run(regionNamesQueryResult).map(results++=_)

    Await.result(f, Duration.Inf)
    results
  }

  def load: Seq[Region] = {
    Seq(
      Region("Surrey"),
      Region("Sussex"),
      Region("Hamshire")
    )
  }
}

