package scothello.utils

import scala.language.implicitConversions

/** A case class representing a pair of elements.
  *
  * @param _1
  *   The first element of the pair.
  * @param _2
  *   The second element of the pair.
  * @tparam A
  *   The type of elements in the pair.
  */
case class Pair[A](_1: A, _2: A):

  /** Applies a function to both elements of the pair and returns the results as a sequence.
    *
    * @param f
    *   The function to apply to each element.
    * @tparam B
    *   The type of the elements after applying the function.
    * @return
    *   A sequence containing the results of applying the function to both elements.
    */
  def map[B](f: A => B): Seq[B] =
    Seq(f(_1), f(_2))

  /** Finds the first element in the pair that satisfies a given predicate.
    *
    * @param predicate
    *   The condition to test each element.
    * @return
    *   An `Option` containing the first matching element, or `None` if no element matches.
    */
  def find(predicate: A => Boolean): Option[A] =
    Seq(_1, _2).find(predicate)

object Pair:

  /** Implicitly converts a tuple of two elements into a Pair.
    *
    * @param tuple
    *   A tuple containing two elements.
    * @tparam A
    *   The type of the elements in the tuple.
    * @return
    *   A Pair constructed from the tuple.
    */
  implicit def tupleToPair[A](tuple: (A, A)): Pair[A] =
    Pair(tuple._1, tuple._2)

  /** Creates a Pair from a sequence of exactly two elements.
    *
    * @param seq
    *   A sequence containing exactly two elements.
    * @tparam A
    *   The type of the elements in the sequence.
    * @return
    *   A Pair constructed from the sequence.
    * @throws IllegalArgumentException
    *   if the sequence does not contain exactly two elements.
    */
  def fromSeq[A](seq: Seq[A]): Pair[A] =
    require(seq.length == 2, "Pair must have exactly two elements")
    Pair(seq.head, seq(1))

  extension [A](pair: Pair[A])
    /** Returns the element of the pair that is not equal to a given value.
      *
      * @param value
      *   The value to compare against the elements of the pair.
      * @return
      *   An `Option` containing the element of the pair that is not equal to the given value, or `None` if both
      *   elements are equal to the given value.
      */
    def oppositeOf(value: A): Option[A] =
      if value == pair._1 then Some(pair._2)
      else if value == pair._2 then Some(pair._1)
      else None
