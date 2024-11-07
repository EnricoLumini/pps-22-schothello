package scothello.view.game.components

import javafx.application.Platform
import scalafx.animation.PauseTransition
import scalafx.beans.property
import scalafx.beans.property.{ObjectProperty, StringProperty}
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scalafx.util.Duration
import scothello.model.game.state.GameState
import scalafx.Includes.jfxObservableValue2sfx

object ClockComponent:

  def clockComponent(using displayScene: Scene, reactiveGameState: ObjectProperty[GameState]): VBox =
    new VBox:
      prefWidth = displayScene.width.value / 3
      alignment = Center

      val clockLabel: Label = new Label:
        id = "clockLabel"
        text = "Time:"
      val timeLabel: Label = new Label:
        id = "timeLabel"
        text <== Clock.curTime

      reactiveGameState.map(_.isPaused).onChange { (_, _, isPaused) =>
        if isPaused then Clock.pause()
        else Clock.resume()
      }

      children.addAll(clockLabel, timeLabel)

object Clock:

  assert(Platform.isFxApplicationThread)

  private var secondsElapsed: Int = 0
  val curTime: StringProperty = new StringProperty("00:00")

  private val timer = new PauseTransition(Duration(1000))
  timer.onFinished = _ =>
    secondsElapsed += 1
    curTime.value = formatTime(secondsElapsed)
    timer.playFromStart()

  timer.play()

  def pause(): Unit =
    timer.stop()

  def resume(): Unit =
    timer.play()

  private def formatTime(seconds: Int): String =
    f"${seconds / 60}%02d:${seconds % 60}%02d"
