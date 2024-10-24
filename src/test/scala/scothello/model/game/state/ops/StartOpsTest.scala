package scothello.model.game.state.ops

import scothello.BaseTest
import scothello.PlayerProvider
import scothello.model.components.Scores
import scothello.model.game.Turn
import scothello.model.game.state.GameState
import scothello.model.game.state.ops.StartOps.startGame

class StartOpsTest extends BaseTest with PlayerProvider:

  "A state with Start Ops" should "start the game" in {
    val state = GameState()
    val newState = state.startGame(twoPlayers)
    newState should not be None
    newState match
      case Some(s) =>
        s.players should be(Some(twoPlayers))
        s.turn shouldBe Turn.initial(twoPlayers._1)
        s.playerScores shouldBe Scores.initialize(twoPlayers)
      case None => fail("Expected a new state")
  }
