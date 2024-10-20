package scothello.model

import scothello.{BaseTest, PlayerProvider}
import scothello.model.game.state.GameState

class ModelTest extends BaseTest with PlayerProvider:

  "Model" should "be created with a State" in {
    val state = GameState(twoPlayers)
    val model = Model(state)
    model.state shouldBe state
  }

  it should "contains the Provider trait" in {
    val state = GameState(twoPlayers)
    val temp: Model.Provider = new Model.Provider:
      override def model: Model = Model(state)
    temp.model.state shouldBe state
  }
