package scothello.model.game.config

/** A player in a game of Scothello.
  */
trait Player:
  def name: String
  def color: PlayerColor

object Player:
  def apply(name: String, color: PlayerColor): Player = PlayerImpl(name, color)
  def apply(name: String): Player = Player(name, PlayerColor.NoColor)
  val empty: Player = Player("")

private final case class PlayerImpl(name: String, color: PlayerColor) extends Player
