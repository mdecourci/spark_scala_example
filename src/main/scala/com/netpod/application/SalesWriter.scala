package com.netpod.application

import java.time.{LocalDate, LocalDateTime}
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

import com.netpod.application.domain._
import com.netpod.database.ApplicationDatabase
import com.typesafe.config.{Config, ConfigFactory}
import slick.dbio.FutureAction

import scala.collection.mutable.ListBuffer
import scala.compat.Platform
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{Failure, Random, Success}
import ExecutionContext.Implicits.global

//import slick.driver.H2Driver
//import slick.jdbc.JdbcBackend.Database
//import slick.driver.JdbcDriver.profile.api._
import slick.driver.MySQLDriver.profile.api._
//import slick.lifted._
//import slick.lifted._
//import slick.driver.H2Driver.profile.api._

//import scala.slick.driver.H2Driver.simple._
//import slick.driver.H2Driver.profile.api._

import org.apache.commons.dbcp.BasicDataSource

object SalesWriter {

  val random = Random
  val maxSalesRate: Int = 100
  val secondsPerHour: Int = 60*60

  val interval = 60
  val closingTime: Int = 17
  val openingTime: Int = 9
  val maxIntervals = (closingTime-openingTime)*secondsPerHour/interval
  val minPrice = 10.00d
  val startDate: LocalDateTime = Tills.openingTime(LocalDate.now)

  val db : Database = ApplicationDatabase.config(ConfigFactory.load())

  def main(args: Array[String]) {

    implicit val ec = ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())

    try {

      val products: Products = new Products(db)
      val productCodes: Seq[String] = products.findCodes
      //      val productCodes : Seq[String] = Products.codes
      val maxProducts = productCodes.seq.length

      val regions: Regions = new Regions(db)
      val regionIds: Seq[String] = regions.findCodes

      val shops: Shops = new Shops(db)
      val shopIds: Seq[String] = shops.findIds

      val tills: Tills = new Tills(db)
      val tillIds: Seq[String] = tills.findCodes
      //      val tillIds : Seq[String] = Tills.codes

      val t1 = Duration(Platform.currentTime, MILLISECONDS)
      val count : AtomicInteger = new AtomicInteger
      val salesCount : AtomicInteger = new AtomicInteger

      val  allFutures = for (regionId <- regionIds;
                             shopId <- shopIds;
                             tillId <- tillIds) yield {
        Future {
          println("Future = regionId=" + regionId + ",shopId=" + shopId  + ",tillId=" + tillId + ", thread=" + Thread.currentThread().getName)

          val sales: Sales = new Sales(db)

          for (intervalCount <- 1 to maxIntervals) {
 //           Thread.sleep(10l + random.nextInt(100))
            val salesThisInterval: ListBuffer[Sale] = ListBuffer[Sale]()
            for (saleCount <- 1 to maxSalesRate) {
              val dataTime = startDate.plusSeconds((intervalCount - 1) * interval).plusNanos(saleCount * 1000l)
              salesThisInterval += Sale(productCodes(random.nextInt(maxProducts-1)), minPrice*random.nextInt(100), tillId, shopId, regionId, dataTime)
              salesCount.incrementAndGet()
            }
            sales.insert(salesThisInterval.toSeq)
          }
          count.incrementAndGet()
          println("Total sales = " + salesCount.intValue())
          "Id:" + "regionId=" + regionId + ",shopId=" + shopId  + ",tillId=" + tillId + ": " + Thread.currentThread().getName
        }
      }

      var results : ListBuffer[String] = new ListBuffer()
      val f = Future.sequence(allFutures.toList).map{ r => results = results ++ r  }

      Await.result(f, Duration.Inf)
      results map {s => println("Thread completed=" + s)}

      val tdiff = Duration(Platform.currentTime, MILLISECONDS) - t1

      println("Processing time(secs) = " + tdiff.toSeconds + ", total tills count=" + count.get() + ", total sales count="+ salesCount.get())

    } catch {
      case t: Throwable => t.printStackTrace
    } finally {
      db.close()
    }
  }
}
