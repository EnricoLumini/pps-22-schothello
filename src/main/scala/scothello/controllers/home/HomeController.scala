package scothello.controllers.home

import scothello.controllers.{Controller, EmptyController}
import scothello.views.home.HomeView

/** Controller for the home page */
trait HomeController extends Controller

object HomeController:
  def apply(requirements: Controller.Requirements[HomeView]): HomeController =
    new EmptyController(requirements) with HomeController
