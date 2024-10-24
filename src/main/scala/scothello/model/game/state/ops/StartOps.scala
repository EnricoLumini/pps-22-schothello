package scothello.model.game.state.ops

import scothello.model.components.Scores
import scothello.model.game.Turn
import scothello.model.game.config.Player
import scothello.model.game.state.GameState
import scothello.utils.Pair

/** Operations on [[GameState]] related to the start of the game */
object StartOps:

  extension (state: GameState)
    def startGame(players: Pair[Player]): Option[GameState] =
      print("Game started with players: " + players)
      Some(
        state.copy(
          players = Some(players),
          turn = Turn.initial(players._1),
          playerScores = Scores.initialize(players)
        )
      )
