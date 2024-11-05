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
import scalafx.geometry.Insets
import scothello.model.board.{AllowedTiles, Tile}

object NotificationsBarComponent:

  def notificationsBarComponent(using mainScene: Scene, reactiveState: ObjectProperty[GameState]): HBox =
    new HBox:
      alignment = Center
      margin = Insets(10, 0, 10, 0)

      val message: Label = new Label()

      children.addAll(message)

      reactiveState.map(_.allowedTiles).onChange { (_, _, allowedTiles) =>
        if AllowedTiles.checkIfAvailableMoves(allowedTiles) then showMessage(message)
      }

      def showMessage(message: Label): Unit =
        message.text = s"No allowed moves, player ${reactiveState.value.turn.player.name} skips turn"
        message.id = "notificationLabel"

        val slideIn = new TranslateTransition(Duration(500), message):
          fromX = mainScene.width.value
          toX = 0
        val pauseM = new PauseTransition(Duration(4000))
        val slideOut = new TranslateTransition(Duration(500), message):
          fromX = 0
          toX = -mainScene.width.value

        new SequentialTransition:
          children = Seq(slideIn, pauseM, slideOut)
        .playFromStart()
