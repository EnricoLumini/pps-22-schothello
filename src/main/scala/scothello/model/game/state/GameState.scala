package scothello.model.game.state

import scothello.model.board.*
import scothello.model.game.Turn
import scothello.model.game.config.Player
import scothello.model.components.Scores
import scothello.utils.Pair

/** The state of a game of Scothello.
  *
  * @param board
  *   The board of the game.
  * @param turn
  *   The current turn.
  * @param playerScores
  *   The scores of the players.
  * @param isOver
  *   Whether the game is over.
  * @param winner
  *   The winner of the game.
  */
final case class GameState(
    board: Board,
    turn: Turn,
    playerScores: Scores,
    isOver: Boolean,
    winner: Option[Player]
)

object GameState:
  /** Create a new game state.
    *
    * @param players
    *   The players of the game.
    * @return
    *   A new game state.
    */
  def apply(players: Pair[Player]): GameState =
    GameState(
      board = Board(),
      turn = Turn.initial(players._1),
      playerScores = Scores.empty(players),
      isOver = false,
      winner = Option.empty
    )
