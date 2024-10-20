package scothello.utils

import scala.language.implicitConversions

case class Pair[A](_1: A, _2: A):
  def map[B](f: A => B): Seq[B] =
    Seq(f(_1), f(_2))

object Pair:
  implicit def tupleToPair[A](tuple: (A, A)): Pair[A] =
    Pair(tuple._1, tuple._2)
