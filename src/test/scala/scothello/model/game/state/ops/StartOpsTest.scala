package scothello.model.game.state.ops

import scothello.BaseTest
import scothello.PlayerProvider
import scothello.model.components.Scores
import scothello.model.game.Turn
import scothello.model.game.config.{Player, PlayerColor}
import scothello.model.game.state.GameState
import scothello.model.game.state.ops.StartOps.startGame
import scothello.utils.Pair

class StartOpsTest extends BaseTest with PlayerProvider:

  val playerNames: Pair[String] = Pair.fromSeq(twoPlayers.map(_.name))

  "A state with Start Ops" should "start the game" in {
    val state = GameState()
    val newState = state.startGame(playerNames)
    newState should not be None
    newState match
      case Some(s) =>
        s.players shouldBe twoPlayers
        s.turn shouldBe Turn.initial(twoPlayers._1)
        s.playerScores shouldBe Scores.calculateScores(s.assignedPawns)
      case None => fail("Expected a new state")
  }

  it should "assign black color to first player and white to second" in {
    val state = GameState()
    val newState = state.startGame(playerNames)
    newState should not be None
    newState match
      case Some(s) =>
        s.players shouldBe twoPlayers
        s.players._1.color shouldBe PlayerColor.Black
        s.players._2.color shouldBe PlayerColor.White
      case None => fail("Expected a new state")
  }
