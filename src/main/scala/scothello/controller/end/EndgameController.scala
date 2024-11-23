package scothello.controller.end

import scothello.controller.{Controller, EmptyController}
import scothello.view.end.EndgameView

/** Controller for the endgame page
  */
trait EndgameController extends Controller

object EndgameController:
  def apply(requirements: Controller.Requirements[EndgameView]): EndgameController =
    new EmptyController(requirements) with EndgameController
