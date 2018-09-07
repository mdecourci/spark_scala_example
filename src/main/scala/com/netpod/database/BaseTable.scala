package com.netpod.database

import slick.dbio.DBIO
import slick.driver.JdbcProfile
import slick.lifted._
import slick.driver.MySQLDriver.profile.api._
import slick.jdbc.SimpleJdbcAction
import slick.lifted.TableQuery
import scala.collection.mutable.ListBuffer
import scala.compat.Platform
import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}
import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

/**
 * Created by michaeldecourci on 17/09/15.
 */
trait BaseTable[A, T <: Table[A]] {
  val db : Database
  val table : TableQuery[T]
  val random : Random = new Random();

  def create = {
    val setupAction : DBIO[Unit] = DBIO.seq(
      //table.schema.drop ,
      table.schema.create
    )
    val createFuture : Future[Unit] = db.run(setupAction)

    Await.result(createFuture, Duration.Inf)
  }

  def insert(data : Seq[A]) = {

    val setupAction : DBIO[Unit] = DBIO.seq {
      table ++= data
    }

    val populateFuture : Future[Unit] = db.run(setupAction.transactionally)

    Await.result(populateFuture, Duration.Inf)
  }

  def findAll : Seq[A] = {

    val query = this.table

    val result: Future[Seq[A]] = db.run(query.result)

    Await.result(result, Duration.Inf)

    var capture : ListBuffer[A] = ListBuffer()

    // read future
    val syncHandle = result map { r => capture = capture ++ r  }
    // wait for future to complete
    Await.result(syncHandle, Duration.Inf)

    return capture.toSeq
  }

  def delete = {
    val query = table.delete

    println("Delete rows:")

    val populateFuture : Future[Int] = db.run(query)

    Await.result(populateFuture, Duration.Inf)

  }
}
