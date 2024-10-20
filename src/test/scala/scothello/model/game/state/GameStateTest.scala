package scothello.model.game.state

import scothello.{BaseTest, PlayerProvider}
import scothello.model.board.Board

class GameStateTest extends BaseTest with PlayerProvider:

  "A State" should "exists" in {
    val gameState = GameState(twoPlayers)
  }

  it should "have a board" in {
    val gameState = GameState(twoPlayers)
    gameState.board should be(Board())
  }

  it should "have turn one on creation" in {
    val gameState = GameState(twoPlayers)
    gameState.turn.number shouldBe 1
  }

  it should "have player one as the first player on creation" in {
    val gameState = GameState(twoPlayers)
    gameState.turn.player shouldBe twoPlayers._1
  }

  it should "have 0 score for both players on creation" in {
    val gameState = GameState(twoPlayers)
    gameState.playerScores shouldBe Map(twoPlayers._1 -> 0, twoPlayers._2 -> 0)
  }

  it should "not be over on creation" in {
    val gameState = GameState(twoPlayers)
    gameState.isOver shouldBe false
  }

  it should "not have a winner on creation" in {
    val gameState = GameState(twoPlayers)
    gameState.winner shouldBe None
  }
