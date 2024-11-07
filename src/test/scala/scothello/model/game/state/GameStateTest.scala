package scothello.model.game.state

import scothello.BaseTest
import scothello.model.board.Board
import scothello.model.game.Turn.EmptyTurn

class GameStateTest extends BaseTest:

  "A State" should "exists" in {
    val gameState = GameState()
  }

  it should "have a board" in {
    val gameState = GameState()
    gameState.board should be(Board())
  }

  it should "have no pawn assigned on creation" in {
    val gameState = GameState()
    gameState.assignedPawns shouldBe Map.empty
  }

  it should "have an empty turn on creation" in {
    val gameState = GameState()
    gameState.turn shouldBe EmptyTurn
  }

  it should "have empty scores on creation" in {
    val gameState = GameState()
    gameState.playerScores shouldBe Map.empty
  }

  it should "not be paused on creation" in {
    val gameState = GameState()
    gameState.isPaused shouldBe false
  }

  it should "not be over on creation" in {
    val gameState = GameState()
    gameState.isOver shouldBe false
  }

  it should "not have a winner on creation" in {
    val gameState = GameState()
    gameState.winner shouldBe None
  }
