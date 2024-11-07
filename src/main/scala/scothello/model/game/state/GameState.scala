package scothello.model.game.state

import scothello.model.board.*
import scothello.model.game.Turn
import scothello.model.game.config.Player
import scothello.model.components.{AssignedPawns, Scores}
import scothello.utils.Pair

/** The state of a game of Scothello.
  * @param players
  *   The players of the game.
  * @param board
  *   The board of the game.
  * @param assignedPawns
  *   The pawns assigned to the tiles.
  * @param turn
  *   The current turn.
  * @param playerScores
  *   The scores of the players.
  * @param isOver
  *   Whether the game is over.
  * @param isPaused
  *   Whether the game is paused.
  * @param winner
  *   The winner of the game.
  */
final case class GameState(
    players: Pair[Player],
    board: Board,
    assignedPawns: AssignedPawns,
    allowedTiles: AllowedTiles,
    turn: Turn,
    playerScores: Scores,
    isOver: Boolean,
    isPaused: Boolean,
    winner: Option[Player]
)

object GameState:

  def apply(): GameState =
    GameState(
      players = (Player.empty, Player.empty),
      board = Board(),
      assignedPawns = AssignedPawns.empty,
      allowedTiles = AllowedTiles.empty,
      turn = Turn.empty,
      playerScores = Scores.empty,
      isOver = false,
      isPaused = false,
      winner = Option.empty
    )
