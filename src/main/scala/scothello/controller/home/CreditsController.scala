package scothello.controller.home

import scothello.controller.{Controller, EmptyController}
import scothello.view.home.CreditsView

/** Controller for the credits page.
  */
trait CreditsController extends Controller

object CreditsController:
  def apply(requirements: Controller.Requirements[CreditsView]): CreditsController =
    new EmptyController(requirements) with CreditsController
