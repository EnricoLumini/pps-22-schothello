package scothello.controller.game

import scothello.controller.{BaseController, Controller, EmptyController}
import scothello.view.game.GameView
import scothello.model.ModelOps.updateState
import scothello.model.game.state.ops.GameOps.nextTurn

/** Controller for the game page */
trait GameController extends Controller:

  /** Increases the turn */
  def nextTurn(): Unit

object GameController:

  def apply(requirements: Controller.Requirements[GameView]): GameController =
    new GameControllerImpl(requirements)

private class GameControllerImpl(requirements: Controller.Requirements[GameView])
    extends BaseController(requirements)
    with GameController:

  override def nextTurn(): Unit =
    this.model.updateState(_.nextTurn())
