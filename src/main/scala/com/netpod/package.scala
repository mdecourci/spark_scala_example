package com

import org.slf4j.LoggerFactory

package netpod {

  object Utils {
    val logger = LoggerFactory.getLogger(Utils.getClass)

    def time[R](msg: String, block: => R): R = {
      val t0 = System.nanoTime()
      val result = block
      val t1 = System.nanoTime()
      logger.info(msg + ", took {} millis", (t1 - t0) * 1000)
      result
    }

  }

}
