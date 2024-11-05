package scothello.view.game

import scalafx.scene.{Parent, Scene}
import scalafx.scene.layout.{HBox, VBox}
import scothello.view.{BaseScalaFXView, View}
import scothello.controller.game.GameController
import scothello.view.game.components.*

trait GameView extends View

object GameView:
  def apply(scene: Scene, requirements: View.Requirements[GameController]): GameView =
    new BaseScalaFXGameView(scene, requirements)

private class BaseScalaFXGameView(mainScene: Scene, requirements: View.Requirements[GameController])
    extends BaseScalaFXView(mainScene, requirements)
    with GameView:

  given clickHandler: GameViewClickHandler = GameViewClickHandler(controller)

  override def parent: Parent = new VBox:
    stylesheets = List(getClass.getResource("/styles/gamepage.css").toExternalForm)

    val header: HBox = HeaderComponent.headerComponent(using mainScene, reactiveState)
    val notificationsBar: HBox = NotificationsBarComponent.notificationsBarComponent(using mainScene, reactiveState)
    val board: HBox = BoardComponent.boardComponent(using mainScene, header, reactiveState, clickHandler, controller)

    children.addAll(header, notificationsBar, board)
