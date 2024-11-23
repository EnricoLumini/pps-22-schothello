package scothello.model.game.state.ops

import scothello.model.board.Tile
import scothello.model.components.Scores
import scothello.model.game.Turn
import scothello.model.game.state.GameState
import scothello.model.game.state.ops.StartOps.startGame
import scothello.utils.Pair
import scothello.{BaseTest, PlayerProvider}

class GameOpsTest extends BaseTest with PlayerProvider:

  val playerNames: Pair[String] = Pair.fromSeq(twoPlayers.map(_.name))
  val newState: GameState = startGame(playerNames)

  it should "initialize a new state with the initial position" in {
    newState should not be None
    val testMap = Map(
      twoPlayers._1 -> Set(
        Tile(2, 3),
        Tile(3, 2),
        Tile(4, 5),
        Tile(5, 4)
      )
    )

    newState.players shouldBe twoPlayers
    newState.turn shouldBe Turn.initial(twoPlayers._1)
    newState.playerScores shouldBe Scores.calculateScores(newState.assignedPawns)
    newState.allowedTiles shouldBe testMap
  }
