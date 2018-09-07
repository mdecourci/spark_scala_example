package com.netpod


/**
  * Created by michaeldecourci on 26/04/2017.
  * f(1) => List(0)
  * f(2) => List(0,1) =
  * f(3) => List(0,1,1)
  * f(4) => List(0,1,1,2) => f(2) + f(1) => List(0,1,1) + List(0,1)
  */
class BetterMath {

  def fibonacci(n: Int): List[Int] = {
    var s: List[Int] = Nil

    def addme(elem : List[Int]): Int = {
      val last = elem.length - 1;
      val previous = last - 1;
      elem(last) + elem(previous)
    }

    def f(k: Int): List[Int] = k match {
      case 1 => s.::(0):::f(2)
      case 2 => s.::(1):::f(3)
      //case _ => s.::(addme(s)):::f(_)
    }
    f(1)
  }


  def fibSeries(n: Int): List[Int] = {
    def fib(k: Int): Int = {
      if (k == 1 || k == 2) return 1
      fib(k - 1) + fib(k - 2)
    }
    //var x : ListBuffer[Int]
Nil

  }


  def reverse[T](list: List[T]): List[T] = list match {
    case h::tail => reverse(tail) ::: List(h)
    case Nil => Nil
  }

}
