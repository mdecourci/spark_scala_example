package com.netpod

import org.scalatest.{FlatSpec, Matchers}

class MathsSpec extends FlatSpec with Matchers {

  "the reverse function" should "return a reversed string" in {
    val myMaths : MyMath = new MyMath
    assertResult("dcba")(myMaths.reverse("abcd"))
  }

  "the generic reverse function" should " return a reversed instance for any type" in {
    val a = List(1, 2, 3,4)
    val b = List('a', 'b', 'c', 'd')

    val maths : BetterMath = new BetterMath

    assertResult(List(4, 3, 2,1)) {
      maths.reverse(a)
    }

    maths.reverse(a) should contain inOrderOnly(4, 3, 2,1)

    maths.reverse(b) should contain inOrderOnly('d', 'c', 'b', 'a')

    //   maths.fibonacci(6) equals(8)

    //    maths.fibonacci(6) should contain inOrderOnly(0, 1, 1, 2, 3, 5, 8)

    //maths.fib(3) equals(2)
    //maths.fib(6) equals(8)
  }

}
