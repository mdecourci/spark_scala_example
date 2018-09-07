package com.netpod.application

import scala.collection.mutable._
import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory

object SparkSalesReader {

  val logger = LoggerFactory.getLogger(this.getClass)

  val spark = SparkSession
    .builder()
    .appName("SparkSalesReader")
    .master("local[2]")
    .config("spark.some.config.option", "some-value")
    .getOrCreate()

  // For implicit conversions like converting RDDs to DataFrames
  import spark.implicits._

  def main(args: Array[String]): Unit = {

    val sc = spark.newSession.sparkContext

    println("*** Load file from hadoop")
    val sales = sc.textFile("hdfs://localhost:9000/user/hive/warehouse/TILLS")
    println("*** File loaded " + sales)

    println("*** Perform map " + sales.count())
    sales.collect().map(p => {print("\n ++ value is "); print(p)})
    println("*** Perform map completed")
  }
}
