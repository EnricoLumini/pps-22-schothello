package scothello.model.game.state

import scothello.model.board.Board

class StateTest extends BaseStateTest:

  "A State" should "exists" in {
    val state = State(twoPlayers)
  }

  it should "have players" in {
    val state = State(twoPlayers)
    state.players should be(twoPlayers)
  }

  it should "have a board" in {
    val state = State(twoPlayers)
    state.board should be(Board())
  }
