package scothello.model.game.state

import scothello.model.board.*
import scothello.model.game.config.Player

/** Represents the state of a Scothello game.
  *
  * @param players
  *   The players in the game.
  * @param board
  *   The game board.
  */
final case class State(
    players: Seq[Player],
    board: Board
)
