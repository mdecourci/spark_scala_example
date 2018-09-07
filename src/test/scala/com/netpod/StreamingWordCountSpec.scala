package com.netpod

import org.scalatest._
import java.nio.file.Files

import com.typesafe.scalalogging.LazyLogging

import scala.collection._
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming._
import org.scalatest.concurrent.Eventually

class StreamingWordCountSpec extends FlatSpec with Matchers with BeforeAndAfter with GivenWhenThen with Eventually with LazyLogging {

  private val master = "local[2]"
  private val appName = "test-spark-streaming"
  private val batchDuration = Seconds(1)
  private val checkpointDir = Files.createTempDirectory(appName).toString
  private val windowDuration = Seconds(4)
  private val slideDuration = Seconds(2)

  // Name of the framework for Spark context
  def framework: String = this.getClass.getSimpleName
  // Whether to use manual clock or not
  def useManualClock: Boolean = true
  
  private var sc: SparkContext = _
  private var ssc: StreamingContext = _

  before {
    val conf = new SparkConf()
      .setMaster(master)
      .setAppName(appName)

    beforeFunction(conf)
    
    ssc = new StreamingContext(conf, batchDuration)
    ssc.checkpoint(checkpointDir)

    sc = ssc.sparkContext
  }

  "Sample set" should "be counted" in {
    Given("streaming context is initialized")
    val lines = mutable.Queue[RDD[String]]()

    var results = mutable.ListBuffer.empty[Array[WordCount]]

    WordCount.count(ssc,
      ssc.queueStream(lines),
      windowDuration,
      slideDuration) { (wordsCount: RDD[WordCount], time: Time) =>
      results += wordsCount.collect()
    }

    ssc.start()

    When("first set of words queued")
    lines += sc.makeRDD(Seq("a", "b"))

    Then("words counted after first slide")
    advanceClock(slideDuration)
    eventually {
      results.last should equal(Array(
        WordCount("a", 1),
        WordCount("b", 1)))
    }
// stops

    When("second set of words queued")
    lines += sc.makeRDD(Seq("b", "c"))

    Then("words counted after second slide")
    advanceClock(slideDuration)
    eventually {
      results.last should equal(Array(
        WordCount("a", 1),
        WordCount("b", 2),
        WordCount("c", 1)))
    }

    When("nothing more queued")

    Then("word counted after third slide")
    advanceClock(slideDuration)
    eventually {
      results.last should equal(Array(
        WordCount("a", 0),
        WordCount("b", 1),
        WordCount("c", 1)))
    }

    When("nothing more queued")

    Then("word counted after fourth slide")
    advanceClock(slideDuration)
    eventually {
      results.last should equal(Array(
        WordCount("a", 0),
        WordCount("b", 0),
        WordCount("c", 0)))
    }
  }

  after {
    logger.debug("*** Ended...")
    if (ssc != null) {
      ssc.stop()
    }
    afterFunction()
  }

  // Default before function for any streaming test suite. Override this
  // if you want to add your stuff to "before" (i.e., don't call before { } )
  def beforeFunction(conf : SparkConf) {
    if (useManualClock) {
      logger.info("Using manual clock")
      conf.set("spark.streaming.clock", "org.apache.spark.util.ManualClock")
    } else {
      logger.info("Using real clock")
      conf.set("spark.streaming.clock", "org.apache.spark.util.SystemClock")
    }
  }

  // Default after function for any streaming test suite. Override this
  // if you want to add your stuff to "after" (i.e., don't call after { } )
  def afterFunction() {
    System.clearProperty("spark.streaming.clock")
  }

  def advanceClock(timeToAdd: Duration): Unit = {
    ClockWrapper.advance(ssc, timeToAdd)
  }

  def advanceClockOneBatch(): Unit = {
    advanceClock(Duration(batchDuration.milliseconds))
  }

}
