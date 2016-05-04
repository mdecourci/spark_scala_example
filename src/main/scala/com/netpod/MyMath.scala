package com.netpod

/**
  * Created by michaeldecourci on 29/01/2016.
  */
class MyMath {
  def reverse(str: String) : String = {
    // swap(tail, n, n-1) - abcd=>abdc
    // swap(tail-1, n-1, n-2)
    val chars : Array[Char] = str.toCharArray
    var temp : Char = Character2char(' ')

    def swap(start: Int, end : Int) : Array[Char] = {
      if (start >= end) chars
      else {
        temp = chars(start)
        chars(start) = chars(end)
        chars(end) = temp
        swap(start + 1, end - 1)
      }
    }

    swap(0 , str.length - 1).mkString
  }
}
