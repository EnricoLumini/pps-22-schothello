package scothello.model

import scothello.model.game.state.GameState

/** A model is a container for the state. It provides a way to update the state.
  */
trait Model:
  def state: GameState
  def update(u: GameState => GameState): Unit

object Model:
  def apply(initialState: GameState): Model =
    new Model:
      private var _state: GameState = initialState
      override def state: GameState = _state
      override def update(u: GameState => GameState): Unit = _state = u(_state)
  trait Requirements
  trait Provider:
    def model: Model
