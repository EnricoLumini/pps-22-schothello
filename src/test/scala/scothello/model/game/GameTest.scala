package scothello.model.game

import scothello.{BaseTest, PlayerProvider}
import scothello.model.game.state.GameState

class GameTest extends BaseTest with PlayerProvider:

  val initialState: GameState = GameState(twoPlayers)

  "A Game" should "exist given the players and initial state" in {
    val game = Game(twoPlayers, initialState)
  }

  it should "exist given the players" in {
    val game = Game(twoPlayers)
  }

  it should "have a state" in {
    val game = Game(twoPlayers)
    game.state shouldBe initialState
  }
