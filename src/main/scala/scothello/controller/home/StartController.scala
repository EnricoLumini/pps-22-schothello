package scothello.controller.home

import scothello.controller.{BaseController, Controller}
import scothello.utils.Pair
import scothello.view.home.StartView
import scothello.model.game.state.ops.StartOps
import scothello.model.ModelOps.updateState

/** Controller for the home page */
trait StartController extends Controller:

  /** Starts the game */
  def startGame(usernames: Pair[String]): Unit

object StartController:

  def apply(requirements: Controller.Requirements[StartView]): StartController =
    StartControllerImpl(requirements)

private class StartControllerImpl(requirements: Controller.Requirements[StartView])
    extends BaseController(requirements)
    with StartController:

  override def startGame(usernames: Pair[String]): Unit =
    this.model.updateState(_ => StartOps.startGame(usernames))
