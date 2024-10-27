package scothello.controller.game

import scothello.controller.{Controller, EmptyController}
import scothello.view.game.GameView

trait GameController extends Controller

object GameController:

  def apply(requirements: Controller.Requirements[GameView]): GameController =
    new EmptyController(requirements) with GameController
