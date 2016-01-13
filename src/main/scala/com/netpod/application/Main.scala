package com.netpod.application

import java.time.LocalDateTime
import javax.sql.DataSource

import com.netpod.application.domain.{Review, Reviews}

import scala.compat.Platform

//import slick.driver.H2Driver
//import slick.jdbc.JdbcBackend.Database
//import slick.lifted._
//import slick.lifted._
//import slick.driver.H2Driver.profile.api._

//import scala.slick.driver.H2Driver.simple._
//import slick.driver.H2Driver.profile.api._
import slick.driver.MySQLDriver.profile.api._

import org.apache.commons.dbcp.BasicDataSource

/**
 * Created by michaeldecourci on 21/08/15.
 */
object Main {

  def main(args: Array[String]) {
  //  val db = Database.forURL("jdbc:h2:mem:test1;DB_CLOSE_DELAY=-1", driver="org.h2.Driver")

    val dataSource: DataSource = {
      val ds = new BasicDataSource
      ds.setDriverClassName("com.mysql.jdbc.Driver")
//      ds.setDriverClassName("oracle.jdbc.driver.OracleDriver")
      ds.setUsername("dev")
      ds.setPassword("dev")
      ds.setMaxActive(20);
      ds.setMaxIdle(10);
      ds.setInitialSize(10);
      ds.setValidationQuery("SELECT 1 FROM DUAL")
      ds.setUrl("jdbc:mysql://localhost/dev")
      ds
    }

    // test the data source validity
    dataSource.getConnection().close()

    // get the Slick database that uses the pooled connection
    val db = Database.forDataSource(dataSource)

    val testData = Seq(
      Review("Godzilla", 8, LocalDateTime.now()),
      Review("Godzilla Raids Again", 6, LocalDateTime.now().plusDays(2)),
      Review("King Kong vs. Godzilla", 5, LocalDateTime.now().plusDays(5))
    )

    try {
      var r : Reviews = new Reviews(db)

      r.create

      r.insert(testData)
      val results = r.findAll

      Thread.currentThread().getName
      println(Thread.currentThread().getName + " :: results=" + results + ", currentTime=" + Platform.currentTime)
      for (review <- results) println("review=" + review)

    } finally db.close()
  }
}
