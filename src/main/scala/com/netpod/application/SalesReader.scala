package com.netpod.application

import com.netpod.application.SalesWriter.db
import com.netpod.application.domain.{Sales, Tills}
import com.netpod.database.ApplicationDatabase
import com.typesafe.config.ConfigFactory
import slick.driver.MySQLDriver.profile.api._

object SalesReader {
  val db : Database = ApplicationDatabase.config(ConfigFactory.load())

  def main(args: Array[String]) {

    val sales: Sales = new Sales(db)
    val tills = new Tills(db)

//    val count = time(sales.findAll.groupBy(s => s.productId).count(p => true))

    val count = tills.findAll.groupBy(s => s.name).count(p => true)
    println("Number of records is " + count);

  }

  def time[R](block: => R): R = {
      val t0 = System.nanoTime();
      val result = block
      val t1 = System.nanoTime();
      println("Elapsed time is " + (t1 - t0)*1000 + "ms" );
      result
  }
}
