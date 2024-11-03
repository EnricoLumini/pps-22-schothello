package scothello.view.game.components

import javafx.application.Platform
import scalafx.animation.PauseTransition
import scalafx.beans.property
import scalafx.beans.property.StringProperty
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.VBox
import scalafx.util.Duration

object ClockComponent:

  def clockComponent(using mainScene: Scene): VBox =
    new VBox:
      prefWidth = mainScene.width.value / 3
      alignment = Center

      val clockLabel: Label = new Label:
        id = "clockLabel"
        text = "Time:"
      val timeLabel: Label = new Label:
        id = "timeLabel"
        text <== Clock.curTime

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

  private def formatTime(seconds: Int): String =
    f"${seconds / 60}%02d:${seconds % 60}%02d"
