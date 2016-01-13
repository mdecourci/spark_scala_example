package com.netpod.application

import java.time.{LocalDate, LocalDateTime}
import java.util.concurrent.{Executors, ExecutorService, ThreadPoolExecutor}
import java.util.concurrent.atomic.AtomicInteger

import com.netpod.application.domain._
import com.typesafe.config.{Config, ConfigFactory}

import scala.collection.mutable.ListBuffer
import scala.compat.Platform
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._
import scala.concurrent.duration._
import scala.util.Random

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

object FutureWriter {

  val random = Random
  val interval = 60
  val maxIntervals = (17-9)*60*60/interval
  val minPrice = 10.00d
  val openingTime: LocalDateTime = Tills.openingTime(LocalDate.now)

  val conf = ConfigFactory.load()


  def main(args: Array[String]) {

    implicit val ec = ExecutionContext.fromExecutorService(Executors.newCachedThreadPool())
    val db = database(conf)

    try {
      val products : Products = new Products(db)
//      val productCodes : Seq[String] = products.findCodes
      val productCodes : Seq[String] = Products.codes
      val maxProducts = productCodes.seq.length

      val tills : Tills = new Tills(db)
//      val tillIds : Seq[String] = tills.findCodes
      val tillIds : Seq[String] = Tills.codes
      val shopIds : Seq[String] = List("shop1", "shop2", "shop3")
      val regionIds : Seq[String] = List("region1", "region2", "region3")

      val t1 = Duration(Platform.currentTime, MILLISECONDS)

      val count : AtomicInteger = new AtomicInteger

      val  allFutures = for (regionId <- regionIds;
                              shopId <- shopIds;
                              tillId <- tillIds) yield {
        Future {
          println("Future = regionId=" + regionId + ",shopId=" + shopId  + ",tillId=" + tillId + ", thread=" + Thread.currentThread().getName)
          for (intervalCount <- 1 to maxIntervals) {
            Thread.sleep(10l + random.nextInt(10))
          }
          count.incrementAndGet()
          "Id:" + "regionId=" + regionId + ",shopId=" + shopId  + ",tillId=" + tillId + ": " + Thread.currentThread().getName
        }
      }


      var results : ListBuffer[String] = new ListBuffer()
      val f = Future.sequence(allFutures.toList).map{ r => results = results ++ r  }

      Await.result(f, Duration.Inf)
      results map {s => println("Thread completed=" + s)}
      val tdiff = Duration(Platform.currentTime, MILLISECONDS) - t1
      println("Processing time(secs) = " + tdiff.toSeconds + ", expected count=" + regionIds.size*shopIds.size*tillIds.size + ", actual count="+ count.get())

      //      val sales : Sales = new Sales(db)
//      //sales.create
//      sales.insert(sales.load)
//
//      val shops : Shops = new Shops(db)
//      //shops.create
//      shops.insert(shops.load)
//
//      val tills : Tills = new Tills(db)
//      //tills.create
//      tills.insert(tills.load)
//
//      val products : Products = new Products(db)
//      //products.create
//      products.insert(products.load)
//
//      val results = shops.findAll
//
//      Thread.currentThread().getName
//      println(Thread.currentThread().getName + " :: results=" + results + ", currentTime=" + Platform.currentTime)
//      for (shop <- results) println("shop=" + shop)

    } catch {
      case t: Throwable => t.printStackTrace
    } finally {
      db.close()
    }
  }

  def database(configuration : Config): Database = {
    //    val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")

    val ds = new BasicDataSource
    ds.setDriverClassName(configuration.getString("database.driverClassName"))
    ds.setUsername(configuration.getString("database.username"))
    ds.setPassword(configuration.getString("database.password"))
    ds.setMaxActive(configuration.getInt("database.maxActive"));
    ds.setMaxIdle(configuration.getInt("database.maxIdle"));
    ds.setInitialSize(configuration.getInt("database.initialSize"));
    ds.setValidationQuery(configuration.getString("database.validationQuery"))
    ds.setUrl(configuration.getString("database.jdbcUrl"))
    ds

    // test the data source validity
    ds.getConnection().close()

    // get the Slick database that uses the pooled connection
    Database.forDataSource(ds)

  }
}
