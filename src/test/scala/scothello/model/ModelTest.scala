package scothello.model

import scothello.BaseTest
import scothello.model.game.state.GameState

class ModelTest extends BaseTest:

  "Model" should "be created with a State" in {
    val state = GameState()
    val model = Model(state)
    model.state shouldBe state
  }

  it should "contains the Provider trait" in {
    val state = GameState()
    val temp: Model.Provider = new Model.Provider:
      override def model: Model = Model(state)
    temp.model.state shouldBe state
  }
