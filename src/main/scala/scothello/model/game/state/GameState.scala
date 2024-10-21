package scothello.model.game.state

import scothello.model.board.*
import scothello.model.game.Turn
import scothello.model.game.config.Player
import scothello.model.components.Scores
import scothello.utils.Pair

import scala.annotation.tailrec

/** The state of a game of Scothello.
  * @param players
  *   The players of the game.
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
    players: Option[Pair[Player]],
    board: Board,
    turn: Turn,
    playerScores: Scores,
    isOver: Boolean,
    winner: Option[Player]
)

object GameState:

  def apply(): GameState =
    GameState(
      players = Option.empty,
      board = Board(),
      turn = Turn.empty,
      playerScores = Scores.empty,
      isOver = false,
      winner = Option.empty
    )
