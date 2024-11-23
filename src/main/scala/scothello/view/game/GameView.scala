package scothello.view.game

import scalafx.scene.{Parent, Scene}
import scalafx.scene.layout.{HBox, VBox}
import scothello.view.{BaseScalaFXView, View}
import scothello.controller.game.GameController

import scothello.view.game.components.*

/** The view of the game page */
trait GameView extends View

object GameView:
  def apply(scene: Scene, requirements: View.Requirements[GameController]): GameView =
    new BaseScalaFXGameView(scene, requirements)

private class BaseScalaFXGameView(mainScene: Scene, requirements: View.Requirements[GameController])
    extends BaseScalaFXView(mainScene, requirements)
    with GameView:

  given gameView: BaseScalaFXView[GameController] = this

  override def parent: Parent = new VBox:
    stylesheets = List(getClass.getResource("/styles/gamepage.css").toExternalForm)

    given displayScene: Scene = mainScene
    given clickHandler: GameViewClickHandler = GameViewClickHandler(controller)
    given gameController: GameController = requirements.controller

    val headerHeight: Double = mainScene.height.value / 8
    val notificationsBarHeight: Double = mainScene.height.value / 24

    val header: HBox =
      HeaderComponent.headerComponent(reactiveState, headerHeight)
    val notificationsBar: HBox =
      NotificationsBarComponent.notificationsBarComponent(reactiveState, notificationsBarHeight)
    val board: HBox =
      BoardComponent.boardComponent(reactiveState, headerHeight, notificationsBarHeight)
    val stopButton: HBox =
      StopButtonComponent.stopButtonComponent(reactiveState)

    children.addAll(header, notificationsBar, board, stopButton)
