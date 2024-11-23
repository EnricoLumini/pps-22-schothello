package scothello.controller.home

import scothello.controller.{Controller, EmptyController}
import scothello.view.home.HomeView

/** Controller for the home page
  */
trait HomeController extends Controller

object HomeController:

  def apply(requirements: Controller.Requirements[HomeView]): HomeController =
    new EmptyController(requirements) with HomeController
