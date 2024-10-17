package scothello.model

import scothello.model.game.state.State

/** A model is a container for the state. It provides a way to update the state.
  */
trait Model:
  def state: State
  def update(u: State => State): Unit

object Model:
  ???
