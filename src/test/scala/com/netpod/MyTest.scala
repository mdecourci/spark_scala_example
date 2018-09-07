package com.netpod;

import java.util.Date

import org.scalatest.{FlatSpec, Matchers}

/**
 * Created by michaeldecourci on 03/05/2017.
 */
class MyTest extends FlatSpec with Matchers {
  "a constructor val" should "define a class property" in {

        class ClassWithValParameter(val name: String)
        val aClass = new ClassWithValParameter("Gandalf")
        aClass.name should equal("Gandalf")
    }

  "an option with Some" should "have a value" in {
      val value = Some("A value")

      value should be(defined)
      value should equal(Some("A value"))
  }

  "an empty option" should "be None" in {
    val value = Option.empty[String]

    value should be(empty)
    value should equal(None)
  }

  "a None value" should "be empty" in {
    val value : Option[String] = None

    value should be(empty)
    value should equal(None)
  }

  "using getOrElse" should "provide a default value" in {
    def maybeItWillReturnSomething(flag: Boolean): Option[String] = {
      if (flag) Some("Found value") else None
    }

    val value1 = maybeItWillReturnSomething(true)
    val value2 = maybeItWillReturnSomething(false)

    value1 getOrElse "No value" should equal("Found value")
    value2 getOrElse "No value" should equal("No value")

    value1.isEmpty should be(false)
    value2.isEmpty should be(true)
  }

  "an option value" should "map its value" in {
    val number : Option[Int] = Some(3)
    val noNumber : Option[Int] = None

    val mapped1 = number map(_ * 1.5)
    val mapped2 = noNumber map(_ * 1.5)

    mapped1 should equal(Some(4.5))
    mapped2 should be(empty)
  }

  "an option value" should "fold its value" in {
    val number : Option[Int] = Some(3)
    val noNumber : Option[Int] = None

    val fold1 = number.fold(0)(_ * 3)
    val fold2 = noNumber.fold(0)(_ * 3)

    fold1 should equal(9)
    fold2 should equal(0)
  }

  "fold" should "remap a list" in {
    class Foo(val name: String, val age: Int, val sex: Symbol)

    object Foo {
      def apply(name: String, age: Int, sex: Symbol) = new Foo(name, age, sex)
    }

    val fooList = Foo("Hugh Jass", 25, 'male) ::
      Foo("Biggus Dickus", 43, 'male) ::
      Foo("Incontinentia Buttocks", 37, 'female) ::
      Nil

    val stringList = fooList.foldLeft(List[String]()) { (z, f) =>
      val title = f.sex match {
        case 'male => "Mr."
        case 'female => "Ms."
      }
      z :+ s"$title ${f.name}, ${f.age}"
    }

    stringList(0) should equal("Mr. Hugh Jass, 25")
    stringList(2) should equal("Ms. Incontinentia Buttocks, 37")
  }

  "object refs" should "have same reference" in {

    object Greeting {
      def english = "Hi"

      def espanol = "Hola"
    }

    val x = Greeting
    val y = x

    x eq y should be(true)

    val z = Greeting

    x eq z should be(true)
  }

  "An object with a class of the same name" should "be a companion object" in  {
    class Movie(val name: String, val year: Short)

    object Movie {
      def academyAwardBestMoviesForYear(x: Short) = {
        //This is a match statement, more powerful than a Java switch statement!
        x match {
          case 1930 ⇒ Some(new Movie("All Quiet On the Western Front", 1930))
          case 1931 ⇒ Some(new Movie("Cimarron", 1931))
          case 1932 ⇒ Some(new Movie("Grand Hotel", 1932))
          case _ ⇒ None
        }
      }
    }

    Movie.academyAwardBestMoviesForYear(1932).get.name should be("Grand Hotel")
  }

  "A companion object" should "access private members" in {
    class Person(val name: String, private val superheroName : String) //The superhero name is private!

    object Person {
      def showMeInnerSecret(x : Person) = x.superheroName
    }

    val clark = new Person("Clark Kent", "Superman")
    val peter = new Person("Peter Parker", "Spiderman")

    Person.showMeInnerSecret(clark) should equal("Superman")
    Person.showMeInnerSecret(peter) should equal("Spiderman")
  }

  "A tuple" should "contain many item types" in {

    val tuple = ("apple", "dog")
    val tuple5 = ("a", 1, 2.2, new Date(), "five")

    val fruit = tuple._1
    val animal = tuple._2

    fruit should equal("apple")
    animal should equal("dog")

    tuple5._2 should equal(1)
    tuple5._5 should equal("five")
  }

  "A tuple" should "be assignable with multiple variables" in {
    val student = ("Sean Peters", 35, 3.5)
    val (name, age, gpa) = student

    name should equal ("Sean Peters")
    age should equal (35)
    gpa should equal (3.5)
  }

  "The swap function" should "swap the elements" in  {
    val student = ("Sean Peters", 35)
    val swappedStudent = student.swap

    swappedStudent._1 should equal(student._2)
    swappedStudent._2 should equal(student._1)
  }

  "a closure" should "maintain the reference of all variables outside of function scope" in {

    val incrementer = 1

    def closure = { x: Int => x + incrementer}

    val result1 = closure(10)
    result1 should equal (11)

    val result2 = closure(10)
    result2 should equal (11)

  }

  "a closure in a function" should "maintain the reference of all variables outside of function scope" in {

      def summation(x: Int, y : Int=> Int) = y(x)

      var incrementer = 3
      def closure = { x: Int => x + incrementer}

      var result = summation(10, closure)
      result should equal (13)

      incrementer = 4

      var result1 = summation(10, closure)
      result1 should equal (14)
  }

  "function" should "return another function" in {
     def addWithoutSyntaxSugar(x:Int) : Function1[Int, Int] = {
        new Function[Int, Int] {
          override def apply(y: Int): Int = x + y
        }
     }

    addWithoutSyntaxSugar(1).isInstanceOf[Function1[Int, Int]] should equal (true)

    addWithoutSyntaxSugar(2)(3) should equal (5)

    def add5 = addWithoutSyntaxSugar(5)

    add5(2) should equal(7)
  }

  it should "be the same with an anonymous function" in {
     def addWithSyntaxSugar(x:Int) = (y: Int) => x + y

    addWithSyntaxSugar(1).isInstanceOf[Function1[Int, Int]] should equal (true)

    addWithSyntaxSugar(2)(3) should equal (5)
    def add5 = addWithSyntaxSugar(5)

    add5(2) should equal(7)
  }

  "a map method" should "operate on each element of a list" in {
       def makeUpper(xs : List[String]) = xs.map {_.toUpperCase}

       def makeWhatEverYouLike[T](xs : List[String], sideEffect: String => T) = xs map sideEffect

       makeUpper(List("abc", "xyz", "123")) should contain only("ABC", "XYZ", "123")

       makeWhatEverYouLike(List("ABC", "XYZ", "123"), { x ⇒
          x.toLowerCase
        }) should contain only("abc", "xyz", "123")

      //using it inline
      List("Scala", "Erlang", "Clojure") map {
        _.length
      } should contain only(5, 6, 7)
  }

  "a list" should "obey identiy and eq" in {
      val a = List(1, 2, 3)
      val b = List(1, 2, 3)

      (a eq b) should be(false)
  }

  "a list" should "obey identiy and ==" in {
    val a = List(1, 2, 3)
    val b = List(1, 2, 3)

    (a == b) should be(true)
  }

  "Nil lists" should "identical even" in {
    val a : List[String] = Nil
    val b : List[Int] = Nil

    (a == Nil) should be(true)
    (a equals Nil) should be(true)
    (a eq Nil) should be(true)

    (b == Nil) should be(true)
    (b equals Nil) should be(true)
    (b eq Nil) should be(true)

    (a == b) should be(true)
    (a equals b) should be(true)
    (a eq b) should be(true)
  }

  "lists" should "be easily created" in {
    val a = List(1, 2, 3)

    a should equal(List(1 ,2, 3))
  }

  "lists" should "be accessed by position" in {
     val a = List(1, 3, 5, 7, 9)

     a(0) should equal(1)
     a(2) should equal(5)
     a(4) should equal(9)

    intercept[IndexOutOfBoundsException] {
      println(a(5))
    }
  }

  "lists" should "be immutable" in {
    val a = List(1, 3, 5, 7, 9)
    val b = a.filterNot(c => c ==5)

    a should equal(List(1, 3, 5, 7, 9))
    b should equal(List(1, 3, 7, 9))
  }

  "maps" should "be created easily" in {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")

    myMap should have size 4
  }

  "maps" should "not contain duplicates" in {
      val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa", "MI" -> "Michigan")

      myMap should have size 4
  }

  "maps" should "be added easily" in {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa")

    val aNewMap = myMap + ("NY" -> "New York")

    aNewMap should have size 5

    aNewMap should contain ("OH" -> "Ohio")
    aNewMap contains("NY") should equal(true)
  }

  "map insertion with duplicate key" should "update previous entry with subsequent value" in {
    val myMap = Map("MI" -> "Michigan", "OH" -> "Ohio", "WI" -> "Wisconsin", "IA" -> "Iowa", "MI" -> "Michigeen")

    myMap should have size 4

    myMap should contain ("MI" -> "Michigeen")
    myMap should not contain ("MI" -> "Michigan")

  }

  "map keys" should "be able to have mixed types" in {
    val myMap = Map("MI" -> "Michigan", 1000 -> "population")

    myMap("MI") should be("Michigan")
    myMap(1000) should be("population")
  }
}
