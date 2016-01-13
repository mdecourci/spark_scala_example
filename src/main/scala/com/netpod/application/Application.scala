package com.netpod.application

import java.time.LocalDateTime
import javax.sql.DataSource

import com.netpod.application.domain._
import com.typesafe.config.{Config, ConfigFactory}

import scala.compat.Platform

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

object Application {

  val conf = ConfigFactory.load()

  def main(args: Array[String]) {

    val db = database(conf)

    try {
   //   val regions : Regions = new Regions(db)
      //regions.create
   //   regions.insert(regions.load)

      val shops : Shops = new Shops(db)
      //shops.create
      shops.insert(shops.load)

  //    val tills : Tills = new Tills(db)
      //tills.create
   //   tills.insert(tills.load)

   //   val products : Products = new Products(db)
      //products.create
   //   products.insert(products.load)

      val results = shops.findAll

      Thread.currentThread().getName
      println(Thread.currentThread().getName + " :: results=" + results + ", currentTime=" + Platform.currentTime)
      for (shop <- results) println("shop=" + shop)

    } finally db.close()
  }

  def load: Seq[Review] = {
    Seq(
      Review("Godzilla", 8, LocalDateTime.now()),
      Review("Godzilla Raids Again", 6, LocalDateTime.now().plusDays(2)),
      Review("King Kong vs. Godzilla", 5, LocalDateTime.now().plusDays(5))
    )
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
