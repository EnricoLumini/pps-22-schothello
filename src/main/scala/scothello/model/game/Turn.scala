package scothello.model.game

import scothello.model.game.config.Player

/** Represents a turn in a game.
  */
trait Turn:

  /** The turn number.
    * @return
    *   The turn number.
    */
  def number: Int

  /** The player taking the turn.
    * @return
    *   The player taking the turn.
    */
  def player: Player

object Turn:
  /** Create a new turn.
    *
    * @param number
    *   The turn number.
    * @param player
    *   The player taking the turn.
    * @return
    *   A new turn.
    */
  def apply(number: Int, player: Player): Turn = TurnImpl(number, player)

  /** Create a new turn with turn number 1.
    *
    * @param player
    *   The player taking the turn.
    * @return
    *   A new turn.
    */
  def initial(player: Player): Turn = Turn(1, player)

private final case class TurnImpl(number: Int, player: Player) extends Turn:
  require(number > 0, "Turn number must be non-negative")
