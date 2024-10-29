package scothello.model.game.state.ops

import scothello.model.components.{AssignedPawns, Scores}
import scothello.model.game.{Turn, TurnManager}
import scothello.model.game.config.{Player, PlayerColor}
import scothello.model.game.state.GameState
import scothello.utils.Pair

/** Operations on [[GameState]] related to the start of the game */
object StartOps:

  extension (state: GameState)
    def startGame(usernames: Pair[String]): Option[GameState] =
      val players: Pair[Player] = Pair(
        Player(usernames._1, PlayerColor.Black),
        Player(usernames._2, PlayerColor.White)
      )

      TurnManager(players)

      Some(
        state.copy(
          players = players,
          assignedPawns = AssignedPawns.initial(players, state.board.centralTiles),
          turn = TurnManager.initialTurn,
          playerScores = Scores.initialize(players)
        )
      )
