package scothello.view.game

import scalafx.beans.property.ObjectProperty
import scalafx.scene.control.Button
import scalafx.scene.{Parent, Scene}
import scalafx.scene.layout.{HBox, VBox}
import scothello.view.{BaseScalaFXView, View}
import scothello.controller.game.GameController
import scothello.model.game.state.GameState
import scothello.view.game.components.*

trait GameView extends View

object GameView:
  def apply(scene: Scene, requirements: View.Requirements[GameController]): GameView =
    new BaseScalaFXGameView(scene, requirements)

private class BaseScalaFXGameView(mainScene: Scene, requirements: View.Requirements[GameController])
    extends BaseScalaFXView(mainScene, requirements)
    with GameView:

  override def parent: Parent = new VBox:
    stylesheets = List(getClass.getResource("/styles/gamepage.css").toExternalForm)

    given displayScene: Scene = mainScene
    given reactiveGameState: ObjectProperty[GameState] = reactiveState
    given clickHandler: GameViewClickHandler = GameViewClickHandler(controller)
    given gameController: GameController = requirements.controller

    val headerHeight: Double = mainScene.height.value / 8
    val notificationsBarHeight: Double = mainScene.height.value / 24

    val header: HBox = HeaderComponent.headerComponent(headerHeight)
    val notificationsBar: HBox = NotificationsBarComponent.notificationsBarComponent(notificationsBarHeight)
    val board: HBox = BoardComponent.boardComponent(headerHeight, notificationsBarHeight)
    val stopButton: HBox = StopButtonComponent.stopButtonComponent

    children.addAll(header, notificationsBar, board, stopButton)
