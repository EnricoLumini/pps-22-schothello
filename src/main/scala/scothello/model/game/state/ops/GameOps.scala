package scothello.model.game.state.ops

import scothello.model.board.{AllowedTiles, Tile}
import scothello.model.components.{AssignedPawns, Pawn, Scores}
import scothello.model.components.AssignedPawns.pawnCounts
import scothello.model.game.config.Player
import scothello.model.game.{Turn, TurnManager}
import scothello.model.game.state.GameState

import scala.collection.mutable.ArrayBuffer

/** Operations on [[GameState]] related to the execution of the game */
object GameOps:

  extension (state: GameState)
    /** Advance the game to the next turn
      *
      * @return
      *   the updated game state
      */
    def nextTurn(): GameState =
      state.copy(
        turn = TurnManager.nextTurn
      )

    /** Place a pawn on the board
      *
      * @param tile
      *   the tile to place the pawn on
      * @return
      *   the updated game state
      */
    def placePawn(tile: Tile): GameState =
      val assignedPawns = state.assignedPawns + (tile -> Pawn(state.turn.player))
      state.copy(
        assignedPawns = assignedPawns
      )

    /** Calculate the allowed positions for the current player
      *
      * @return
      *   the updated game state
      */
    def calculateAllowedPos(): GameState =
      state.copy(
        allowedTiles = AllowedTiles.calculate(state.turn.player, state.assignedPawns)
      )

    /** Flip the pawns on the board
      *
      * @param tile
      *   the tile to flip the pawns from
      * @return
      *   the updated game state
      */
    def flipPawns(tile: Tile): GameState =
      val array = AllowedTiles.flipsMap.getOrElse(tile, ArrayBuffer.empty[Tile])

      val newAssignedPawns: AssignedPawns = state.assignedPawns.collect {
        case (t, pawn) if array.contains(t) =>
          t -> Pawn(state.turn.player)
        case (t, pawn) =>
          t -> pawn
      }

      AllowedTiles.resetMap()
      state.copy(
        assignedPawns = newAssignedPawns,
        playerScores = Scores.calculateScores(newAssignedPawns)
      )

    /** Pause the game
      *
      * @return
      *   the updated game state
      */
    def pauseGame(): GameState =
      state.copy(
        isPaused = true
      )

    /** Resume the game
      *
      * @return
      *   the updated game state
      */
    def resumeGame(): GameState =
      state.copy(
        isPaused = false
      )

    /** End the game
      *
      * @return
      *   the updated game state
      */
    def endGame(): GameState =
      val winner = determineWinner(state)
      state.copy(
        isOver = true,
        winner = winner
      )

  /** Determine the winner of the game
    *
    * @param state
    *   the game state
    * @return
    *   the winner of the game
    */
  private def determineWinner(state: GameState): Option[Player] =
    val pawnCounts: Map[Player, Int] = state.assignedPawns.pawnCounts

    val (leadingPlayer, maxCount) = pawnCounts.maxBy(_._2)

    if pawnCounts.values.count(_ == maxCount) > 1 then None
    else Some(leadingPlayer)
