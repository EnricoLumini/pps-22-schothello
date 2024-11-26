package scothello.model.game.state.ops

import scothello.BaseTest
import scothello.PlayerProvider
import scothello.model.components.Scores
import scothello.model.game.Turn
import scothello.model.game.config.{Player, PlayerColor}
import scothello.model.game.state.ops.StartOps.startGame
import scothello.utils.Pair

class StartOpsTest extends BaseTest with PlayerProvider:

  val playerNames: Pair[String] = Pair.fromSeq(twoPlayers.map(_.name))

  "A state with Start Ops" should "start the game" in {
    val newState = startGame(playerNames)
    newState should not be None
    newState.players shouldBe twoPlayers
    newState.turn.player shouldBe twoPlayers._1
    newState.playerScores shouldBe Scores.calculateScores(newState.assignedPawns)
  }

  it should "assign black color to first player and white to second" in {
    val newState = startGame(playerNames)
    newState should not be None
    newState.players shouldBe twoPlayers
    newState.players._1.color shouldBe PlayerColor.Black
    newState.players._2.color shouldBe PlayerColor.White
  }
