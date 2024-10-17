package scothello.model.game.config

/** A player in a game of Scothello.
  */
trait Player:
  def name: String

object Player:
  def apply(name: String): Player = PlayerImpl(name)

private final case class PlayerImpl(name: String) extends Player
