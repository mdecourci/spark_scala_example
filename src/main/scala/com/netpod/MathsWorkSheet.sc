def abs(x: Double) = if (x < 0) -x else x

def iterate(guess: Double, x: Double): Double =
  if (isGoodEnough(guess, x)) guess
  else iterate(improve(guess, x), x)

def isGoodEnough(guess: Double, x: Double): Boolean =
  abs(guess * guess - x) < 0.001

def improve(guess: Double, x: Double): Double =
  (guess + x/guess)/2

def sqrt(x: Double) = iterate(1, x)

sqrt(2)