package scothello.model.game

import scothello.model.game.config.Player

/** Represents a turn in a game.
  */
sealed trait Turn:

  /** The turn number.
    *
    * @return
    *   The turn number.
    */
  def number: Int

  /** The player taking the turn.
    *
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
  def apply(number: Int, player: Player): Turn = ValidTurn(number, player)

  /** Create a new turn with turn number 1.
    *
    * @param player
    *   The player taking the turn.
    * @return
    *   A new turn.
    */
  def initial(player: Player): Turn = ValidTurn(1, player)

  /** Create an empty turn.
    *
    * @return
    *   An empty turn.
    */
  def empty: Turn = EmptyTurn

  sealed trait Empty extends Turn:
    def number: Int = 0
    def player: Player = Player.empty

  final case class ValidTurn(number: Int, player: Player) extends Turn:
    require(number > 0, "Turn number must be non-negative")

  case object EmptyTurn extends Empty
