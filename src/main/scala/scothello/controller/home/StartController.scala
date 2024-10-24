package scothello.controller.home

import scothello.controller.{Controller, EmptyController}
import scothello.model.components.Scores
import scothello.model.game.Turn
import scothello.model.game.config.Player
import scothello.utils.Pair
import scothello.view.home.StartView
import scothello.model.game.state.ops.StartOps.startGame
import scothello.model.ModelOps.{updateState, onError}

/** Controller for the home page */
trait StartController extends Controller:

  /** Starts the game */
  def startGame(players: Pair[Player]): Unit

object StartController:

  def apply(requirements: Controller.Requirements[StartView]): StartController =
    new EmptyController(requirements) with StartController:
      override def startGame(players: Pair[Player]): Unit =
        // TODO: Show popup on view if error
        this.model
          .updateState(_.startGame(players))
          .onError(println(s"Error starting game"))
