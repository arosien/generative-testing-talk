<div style="border-radius: 10px; background: #EEEEEE; padding: 20px; text-align: center; font-size: 1.5em">
  <big><b>Unit Tests are a Big Fail<br/>and How Generative Testing<br/>can (Help) Save You</b></big> </br>
  </br>
  Adam Rosien
</div>

---

# Unit Testing... meh #

Can _only_ show that a bug exists, not prove that bugs _don't_ exist.

---

# xUnit... meh #

* Assertions
* Before/After
* Fixtures(?)

What are you testing? _Are you sure??_

.notes: How many examples do you really test? 1? 3? FAIL: always manual, edge cases bite you in the butt.

---

# Generative Testing #

We have computers for this!

* Properties: universal quantification, i.e., invariants
* Generators: automatically generate examples to test

---
# Generative Testing: Properties #

    !scala
    val propReverseList =
      forAll { l: List[String] =>
        l.reverse.reverse == l
      }

    val propConcatString =
      forAll { (s1: String, s2: String) =>
        (s1 + s2).endsWith(s2)
      }

---

# Generative Testing: Generators #

Basic generators you get for free:

    !scala
    val tenToTwenty = Gen.choose(10,20)

    val vowel =
      Gen.oneOf("A", "E", "I", "O", "U", "Y")

    val vowel2 = Gen.frequency(
      (3, "A"),
      (4, "E"),
      (2, "I"),
      (3, "O"),
      (1, "U"),
      (1, "Y"))

---

# Generative Testing: Generators #

Abstraction via composition!

    !scala
    val myGen = for {
      n <- tenToTwenty
      m <- Gen.choose(2*n, 500)
    } yield (n,m)

---

# Generative Testing: Generators #

    !scala
    sealed abstract class Tree
    case class Node(
      left: Tree, right: Tree, v: Int) extends Tree
    case object Leaf extends Tree

    val genLeaf = value(Leaf)

    val genNode = for {
      v <- arbitrary[Int]
      left <- genTree
      right <- genTree
    } yield Node(left, right, v)

    def genTree: Gen[Tree] =
      oneOf(genLeaf, genNode)

---

# An example using ScalaCheck

    $ scala -cp scalacheck.jar
    scala> import org.scalacheck.Prop.forAll
    scala> val propConcatLists =
      forAll { (l1: List[Int], l2: List[Int]) =>
        l1.size + l2.size == (l1 ::: l2).size
      }
    scala> propConcatLists.check
    + OK, passed 100 tests.
    scala> val propSqrt =
      forAll { (n: Int) =>
        scala.Math.sqrt(n*n) == n
      }
    scala> propSqrt.check
    ! Falsified after 1 passed tests:
    > -1

---

# Demo? #

---

# Thanks!

References:

* [QuickCheck](http://en.wikipedia.org/wiki/QuickCheck), the original generative testing library
* [ScalaCheck](https://github.com/rickynils/scalacheck)
