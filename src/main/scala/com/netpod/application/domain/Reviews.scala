package com.netpod.application.domain

import com.netpod.retail.domain.SlickExtension
import slick.driver.JdbcProfile
import slick.lifted._

import java.time.LocalDateTime

import com.netpod.database.BaseTable
import slick.driver.JdbcDriver
import slick.driver.MySQLDriver.profile.api._
import com.netpod.retail.domain.SlickExtension._
//import slick.jdbc.JdbcBackend._
import slick.lifted.{TableQuery, Tag}

/**
 * Created by michaeldecourci on 26/08/15.
 */

class ReviewTable(tag: Tag) extends Table[Review](tag, "REVIEW") {
  def id = column[Long]("ID", O.PrimaryKey, O.AutoInc, O.SqlType("BIGINT"))

  def title = column[String]("TITLE")

  def rating = column[Int]("RATING")

  def created = column[LocalDateTime]("CREATED")(SlickExtension.JavaLocalDateTimeMapper)

  def * = (title, rating, created, id) <>(Review.tupled, Review.unapply)
}

class Reviews(d : Database) extends BaseTable[Review, ReviewTable] {
  override val db: Database = d
  override lazy val table: TableQuery[ReviewTable] = TableQuery[ReviewTable]

  def load: Seq[Review] = {
    Seq(
      Review("Godzilla", 8, LocalDateTime.now()),
      Review("Godzilla Raids Again", 6, LocalDateTime.now().plusDays(2)),
      Review("King Kong vs. Godzilla", 5, LocalDateTime.now().plusDays(5))
    )
  }
}

//case class Review(title: String, rating: Int, id: Long = 0L)
//
//class ReviewTable(tag: Tag) extends Table[Review](tag, "REVIEW") {
//  def id = column[Long]("id", O.PrimaryKey, O.SqlType("NUMBER"))
//
//  def title = column[String]("title")
//
//  def rating = column[Int]("rating")
//
//  def * = (title, rating, id) <>(Review.tupled, Review.unapply)
//}
//
//
//object Reviews {
//  val reviews = TableQuery[ReviewTable]
//}
