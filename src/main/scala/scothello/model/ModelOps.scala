package scothello.model

import scothello.model.game.state.GameState

object ModelOps:

  extension (model: Model)
    /** Updates the state of the model */
    def updateState(update: GameState => Option[GameState]): Option[GameState] =
      val state = model.state
      update(state).map { nextState =>
        model.update(_ => nextState)
        nextState
      }

  extension (state: Option[GameState])
    /** Calls the callback if the state is None */
    def onError(callback: => Unit): Unit = state match
      case None    => callback
      case Some(_) => ()
