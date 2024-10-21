package scothello

import scalafx.scene.Scene
import scothello.controllers.home.HomeController
import scothello.views.home.HomeView
import scothello.views.{PageFactory, ScalaFXPageFactory}

given rootScene: Scene = ScothelloFXApp.rootScene

enum Pages(val pageFactory: PageFactory[?, ?]):
  case Home extends Pages(ScalaFXPageFactory(HomeView.apply, HomeController.apply))
