package com.netpod

/**
  * Created by michaeldecourci on 04/01/2016.
  */
object MyApp extends App {

  def fabbanici(n : Int) : Int = {
    if (n == 0 || n == 1) 0 else if (n == 2) 1 else fabbanici(n - 1) + fabbanici(n - 2)
  }

  def factorial(n : Int) : Int = {
     if (n == 0) 1 else n*factorial(n-1)
  }

  println(factorial(3))

  println(fabbanici(6))
  println(fabbanici(7))
  val series : Stream[Int] = Stream.range(1, 9)
}

/**
  * Series;
  * i=      1 2 3 4 5 6 7 8   9
  * Series= 0,1,1,2,3,5,8,13, 21
  *
  * f(n) = f(n-1) + f(n-2)
  * f(n-1) = f(n-2) + f(n-3)
  * f(2) = 1
  * f(1) = 0
  */
