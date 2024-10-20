package scothello

import scalafx.application.{JFXApp3, Platform}
import scalafx.scene.Scene
import scalafx.scene.layout.{Pane, VBox}
import scothello.controllers.Controller
import scothello.controllers.home.HomeController
import scothello.model.Model
import scothello.model.game.config.Player
import scothello.model.game.state.GameState
import scothello.utils.Pair
import scothello.views.{ApplicationPage, ScalaFXPageFactory, View}
import scothello.views.home.HomeView
import scalafx.Includes.jfxScene2sfx

object ScothelloApp extends JFXApp3:

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Scothello"
      scene = new Scene:
        root = new Pane()

    Platform.runLater {
      val players: Pair[Player] = Pair(Player("Player 1"), Player("Player 2"))
      val initialState: GameState = GameState(players)
      val model: Model = Model(initialState)

      given rootScene: Scene = stage.getScene

      val pageFactory = ScalaFXPageFactory(HomeView.apply, HomeController.apply)
      val homePage = ApplicationPage(model, pageFactory)
      homePage.view.show()
    }
