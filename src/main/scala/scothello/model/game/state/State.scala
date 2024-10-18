package scothello.model.game.state

import scothello.model.board.*
import scothello.model.game.config.Player

/** Represents the state of a Scothello game.
  *
  * @param players
  *   A tuple containing exactly two players participating in the game.
  * @param board
  *   The game board.
  */
final case class State(
    players: (Player, Player),
    board: Board
)

object State:

  /** Creates a new State with the specified players.
    *
    * @param players
    *   The players in the game.
    * @return
    *   a new State with the specified players
    */
  def apply(players: (Player, Player)): State =
    State(
      players,
      Board()
    )
