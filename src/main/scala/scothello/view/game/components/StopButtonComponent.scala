package scothello.view.game.components

import scalafx.geometry.Insets
import scalafx.geometry.Pos.BottomRight
import scalafx.scene.Scene
import scalafx.scene.control.Button
import scalafx.scene.layout.HBox
import scothello.controller.game.GameController

object StopButtonComponent:

  private val buttonRadius: Int = 20
  private val paddingSize: Int = 10

  def stopButtonComponent(using
      displayScene: Scene,
      gameController: GameController
  ): HBox =
    new HBox:
      alignment = BottomRight
      padding = Insets(0, paddingSize, paddingSize, 0)
      val stopButton: Button = new Button:
        style = s"""
          -fx-background-image: url('/imgs/stop-button.png');
          -fx-background-radius: ${buttonRadius}px;
          -fx-background-size: contain;
          -fx-background-color: transparent;
          -fx-border-color: transparent;
          -fx-pref-width: ${buttonRadius * 2}px;
          -fx-pref-height: ${buttonRadius * 2}px;
          -fx-cursor: hand;
        """
        prefHeight = buttonRadius * 2
        prefWidth = buttonRadius * 2

        onAction = _ => println("Stop button clicked")

      children.add(stopButton)
