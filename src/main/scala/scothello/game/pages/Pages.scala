package scothello.game.pages

import scalafx.scene.Scene
import scothello.controller.home.HomeController
import scothello.game.manager.ScothelloFXApp
import scothello.view.home.HomeView

given rootScene: Scene = ScothelloFXApp.rootScene

enum Pages(val pageFactory: PageFactory[?, ?]):
  case Home extends Pages(ScalaFXPageFactory(HomeView.apply, HomeController.apply))
