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
    def nextTurn(): Option[GameState] =
      Some(
        state.copy(
          turn = TurnManager.nextTurn
        )
      )

    def placePawn(tile: Tile): Option[GameState] =
      val assignedPawns = state.assignedPawns + (tile -> Pawn(state.turn.player))
      Some(
        state.copy(
          assignedPawns = assignedPawns
        )
      )

    def calculateAllowedPos(): Option[GameState] =
      Some(
        state.copy(
          allowedTiles = AllowedTiles.calculate(state.turn.player, state.assignedPawns)
        )
      )

    def flipPawns(tile: Tile): Option[GameState] =
      val array = AllowedTiles.flipsMap.getOrElse(tile, ArrayBuffer.empty[Tile])

      val newAssignedPawns: AssignedPawns = state.assignedPawns.collect {
        case (t, pawn) if array.contains(t) =>
          t -> Pawn(state.turn.player)
        case (t, pawn) =>
          t -> pawn
      }

      AllowedTiles.resetMap()
      Some(
        state.copy(
          assignedPawns = newAssignedPawns,
          playerScores = Scores.calculateScores(newAssignedPawns)
        )
      )

    def pauseGame(): Option[GameState] =
      Some(
        state.copy(
          isPaused = true
        )
      )

    def resumeGame(): Option[GameState] =
      Some(
        state.copy(
          isPaused = false
        )
      )

    // TODO: Find if necessary
    def stopGame(): Option[GameState] =
      Some(
        state.copy(
          isOver = false
        )
      )

    def endGame(): Option[GameState] =
      val winner = determineWinner(state)
      Some(
        state.copy(
          isOver = true,
          winner = winner
        )
      )

  private def determineWinner(state: GameState): Option[Player] =
    val pawnCounts: Map[Player, Int] = state.assignedPawns.pawnCounts

    val (leadingPlayer, maxCount) = pawnCounts.maxBy(_._2)

    if pawnCounts.values.count(_ == maxCount) > 1 then None
    else Some(leadingPlayer)
