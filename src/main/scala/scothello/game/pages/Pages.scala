package scothello.game.pages

import scalafx.scene.Scene
import scothello.controller.end.EndgameController
import scothello.controller.game.GameController
import scothello.controller.home.{CreditsController, HomeController, StartController}
import scothello.game.manager.ScothelloFXApp
import scothello.view.end.EndgameView
import scothello.view.game.GameView
import scothello.view.home.{HomeView, StartView, CreditsView}

given rootScene: Scene = ScothelloFXApp.rootScene

/** Enum for the different pages of the game
  */
enum Pages(val pageFactory: PageFactory[?, ?]):
  case Home extends Pages(ScalaFXPageFactory(HomeView.apply, HomeController.apply))
  case Start extends Pages(ScalaFXPageFactory(StartView.apply, StartController.apply))
  case Game extends Pages(ScalaFXPageFactory(GameView.apply, GameController.apply))
  case End extends Pages(ScalaFXPageFactory(EndgameView.apply, EndgameController.apply))
  case Credits extends Pages(ScalaFXPageFactory(CreditsView.apply, CreditsController.apply))
