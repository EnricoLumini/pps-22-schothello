package scothello.model.game.state.ops

import scothello.model.board.AllowedTiles
import scothello.model.components.{AssignedPawns, Scores}
import scothello.model.game.{Turn, TurnManager}
import scothello.model.game.config.{Player, PlayerColor}
import scothello.model.game.state.GameState
import scothello.utils.Pair

/** Operations on [[GameState]] related to the start of the game */
object StartOps:

  /** Start a new game with the given usernames.
    *
    * @param usernames
    *   the usernames of the players
    * @return
    *   the initial state of the game
    */
  def startGame(usernames: Pair[String]): GameState =
    val state = GameState()
    val players: Pair[Player] = Pair(
      Player(usernames._1, PlayerColor.Black),
      Player(usernames._2, PlayerColor.White)
    )
    val initialAssignedPawns: AssignedPawns =
      AssignedPawns.initial(players, state.board.centralTiles)

    TurnManager(players)

    state.copy(
      players = players,
      assignedPawns = initialAssignedPawns,
      allowedTiles = AllowedTiles.initial(players._1, initialAssignedPawns),
      turn = TurnManager.initialTurn,
      playerScores = Scores.calculateScores(initialAssignedPawns)
    )
