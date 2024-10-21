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
import scothello.views.{GamePage, ScalaFXPageFactory, View}
import scothello.views.home.HomeView
import scalafx.Includes.jfxScene2sfx
import scothello.model.components.Scores
import scothello.model.game.Turn

object ScothelloApp extends JFXApp3:

  override def start(): Unit =
    stage = new JFXApp3.PrimaryStage:
      title = "Scothello"
      scene = new Scene(width = 800, height = 600):
        root = new Pane()

    Platform.runLater {
      val initialState: GameState = GameState()
      val model: Model = Model(initialState)

      // When the game starts, the players are known and the state is updated.
      val players: Pair[Player] = Pair(Player("Player 1"), Player("Player 2"))

      val newState: GameState = initialState.copy(
        players = Some(players),
        turn = Turn.initial(players._1),
        playerScores = Scores.initialize(players)
      )
      model.update(_ => newState)

      given rootScene: Scene = stage.getScene

      val pageFactory = ScalaFXPageFactory(HomeView.apply, HomeController.apply)
      val homePage = GamePage(model, pageFactory)
      homePage.view.show()
    }
