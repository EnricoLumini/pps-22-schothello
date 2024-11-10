package scothello.utils

import scala.language.implicitConversions

case class Pair[A](_1: A, _2: A):

  def map[B](f: A => B): Seq[B] =
    Seq(f(_1), f(_2))

  def find(predicate: A => Boolean): Option[A] =
    Seq(_1, _2).find(predicate)

object Pair:

  implicit def tupleToPair[A](tuple: (A, A)): Pair[A] =
    Pair(tuple._1, tuple._2)

  /** Create a pair from a sequence of two elements. */
  def fromSeq[A](seq: Seq[A]): Pair[A] =
    require(seq.length == 2, "Pair must have exactly two elements")
    Pair(seq.head, seq(1))

  extension [A](pair: Pair[A])
    def oppositeOf(value: A): Option[A] =
      if value == pair._1 then Some(pair._2)
      else if value == pair._2 then Some(pair._1)
      else None
