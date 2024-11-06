package scothello.view.game.components

import scalafx.animation.{PauseTransition, SequentialTransition, TranslateTransition}
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.HBox
import scalafx.util.Duration
import scothello.model.game.config.Player
import scothello.model.game.state.GameState
import scalafx.Includes.jfxObservableValue2sfx
import scothello.model.board.{AllowedTiles, Tile}

object NotificationsBarComponent:

  def notificationsBarComponent(notificationsBarHeight: Double)(using
      displayScene: Scene,
      reactiveGameState: ObjectProperty[GameState]
  ): HBox =
    new HBox:
      prefHeight = notificationsBarHeight
      alignment = Center

      val message: Label = new Label():
        prefHeight = notificationsBarHeight / 2

      reactiveGameState.map(_.allowedTiles).onChange { (_, _, allowedTiles) =>
        if AllowedTiles.checkIfAvailableMoves(allowedTiles) then showMessage(message)
      }

      children += message

  private def showMessage(message: Label)(using
      displayScene: Scene,
      reactiveGameState: ObjectProperty[GameState]
  ): Unit =
    message.text = s"No allowed moves, player ${reactiveGameState.value.turn.player.name} skips turn"
    message.id = "notificationLabel"

    val slideIn = new TranslateTransition(Duration(500), message):
      fromX = displayScene.width.value
      toX = 0
    val pauseM = new PauseTransition(Duration(3000))
    val slideOut = new TranslateTransition(Duration(500), message):
      fromX = 0
      toX = -displayScene.width.value

    val transition = new SequentialTransition:
      children = Seq(slideIn, pauseM, slideOut)
    transition.playFromStart()
