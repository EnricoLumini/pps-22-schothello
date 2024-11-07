package scothello.controller.end

import scothello.controller.{BaseController, Controller}
import scothello.view.end.EndgameView

trait EndgameController extends Controller

object EndgameController:
  def apply(requirements: Controller.Requirements[EndgameView]): EndgameController =
    new EndgameControllerImpl(requirements)

private class EndgameControllerImpl(requirements: Controller.Requirements[EndgameView])
    extends BaseController(requirements)
    with EndgameController
