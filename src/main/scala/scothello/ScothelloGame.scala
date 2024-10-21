package scothello

import scothello.model.Model
import scothello.model.game.state.GameState

trait ScothelloGame:
  val model: Model

object ScothelloGame:
  def apply(): ScothelloGame =
    new ScothelloGame:
      override val model: Model = Model(GameState())
