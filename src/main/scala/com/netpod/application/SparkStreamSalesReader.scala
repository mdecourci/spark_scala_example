package com.netpod.application

import java.sql.Timestamp

import akka.actor.ActorSystem
import com.netpod.application.domain.{Sale, Shop, Till}
import com.typesafe.config.ConfigFactory
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.{Milliseconds, Seconds, StreamingContext}
import org.slf4j.LoggerFactory
import org.apache.spark.sql.types._
import org.apache.spark.sql.expressions.scalalang.typed.{
count => typedCount,
sum => typedSum}

case class SaleVO(saleId: Integer, productId: String, soldPrice: Double, tillId: String, shopId: String, regionId: String, timeOfSale: Timestamp)

object SparkStreamSalesReader {
  val config = ConfigFactory.load()

  implicit val system = ActorSystem(config.getString("app.name"))
  val logger = LoggerFactory.getLogger(this.getClass)

  val schema = new StructType(Array(
    new StructField("saleId", DataTypes.IntegerType, true),
    new StructField("productId", DataTypes.StringType, true),
    new StructField("soldPrice", DataTypes.DoubleType, true),
    new StructField("tillId", DataTypes.StringType, true),
    new StructField("shopId", DataTypes.StringType, true),
    new StructField("regionId", DataTypes.StringType, true),
    new StructField("timeOfSale", DataTypes.TimestampType, true)))

  def main(args: Array[String]) {

    system.logConfiguration()

    val sparkSession = SparkSession
      .builder()
      .appName("Scala Stream API demo")
      .master("local[2]")
      .config("spark.some.config.option", "some-value")
      .getOrCreate()
    import sparkSession.implicits._

   // new StreamingContext()
    println("started......")
//    val salesDS = sparkSession.read.textFile("hdfs://localhost:9000/user/hive/warehouse/SALES")
//    val salesDF = sparkSession.read.text("hdfs://localhost:9000/user/hive/warehouse/SALES").printSchema()

    println("Create DS......")

    val salesDS = sparkSession.read.option("charset", "UTF8")
        .schema(schema)
        .option("header", false)
      .option("inferSchema","true")
      .csv("hdfs://localhost:9000/user/hive/warehouse/SALES").as[SaleVO]

    println("Show DS......")
    salesDS.show()
    println("END Show DS......")

    var t0 = System.nanoTime()
    salesDS.groupByKey(d => d.productId)
      .agg(typedSum[SaleVO](_.soldPrice).name("total")).orderBy($"total".desc).show();
    val t1 = System.nanoTime();
    println("Elapsed time is " + (t1 - t0)/(1000*1000*1000) + "s" );

    //val shopsDSAsTills = shopsDS.as[Shop]

//    println("Total sales" + salesDS.count())

//    salesDS.take(3).foreach(s => println(s))
//    shopsDSAsText.map(line => {
//      val cols = line.split(",")
//      Shop(cols(0), cols(1), cols(3))
//
//    }).take(3).foreach(s => println(s))

    // val ssc = new StreamingContext(sparkConf, Milliseconds(500))

    //ssc.sparkContext.setLogLevel("WARN")


    //val sales = ssc.textFileStream("hdfs://localhost:9000/user/hive/warehouse/TILLS")
    try {

      println("*** Load file from hadoop")
      //println("*** File loaded " + sales)


      t0 = System.nanoTime()
      println("*** Perform loop")
      //sales.foreachRDD(p => {print("\n value is "); print(p)})
      //sales.map(p => {print("\n value is "); print(p)})
      println("*** Perform loop completed")
      //spark.sqlContext.s

    } catch {
      case e: RuntimeException => println("Error occured " + e.getMessage)
    } finally {
      //ssc.awaitTermination()
    }


  }

}
