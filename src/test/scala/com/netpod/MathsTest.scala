package com.netpod

import org.scalatest.{FlatSpec, Matchers}

/**
  * Created by michaeldecourci on 29/01/2016.
  */
class MathsTest extends FlatSpec with Matchers {

  "the reverse function" should "return a reversed string" in {
    val myMaths : MyMath = new MyMath
    assertResult("dcba")(myMaths.reverse("abcd"))
  }

}
