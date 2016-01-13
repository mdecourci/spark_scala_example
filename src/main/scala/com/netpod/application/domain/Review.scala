package com.netpod.application.domain

import java.time.LocalDateTime

/**
 * Created by michaeldecourci on 23/09/15.
 */
case class Review(title: String, rating: Int, created: LocalDateTime, id: Long = 0L)   {
  override def toString: String = {
    "Review(title=" + title + ",rating=" + rating + ",id=" + id + ")"
  }
}

