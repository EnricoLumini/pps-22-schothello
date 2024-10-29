package scothello.model.game.state.ops

import scothello.model.game.{Turn, TurnManager}
import scothello.model.game.state.GameState

/** Operations on [[GameState]] related to the execution of the game */
object GameOps:

  extension (state: GameState)
    def nextTurn(): Option[GameState] =
      Some(
        state.copy(
          turn = TurnManager.nextTurn
        )
      )
