package scothello.view.home

import scalafx.geometry.Pos.{Center, TopCenter}
import scalafx.scene.{Node, Parent, Scene}
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.VBox
import scothello.controller.home.{HomeController, StartController}
import scothello.game.pages.Pages
import scothello.model.game.config.Player
import scothello.utils.Pair
import scothello.view.{BaseScalaFXView, View}

trait StartView extends View

object StartView:
  def apply(scene: Scene, requirements: View.Requirements[StartController]): StartView =
    BaseScalaFXStartView(scene, requirements)

/** Represents the home view.
  * @param scene
  *   The parent pane.
  * @param requirements
  *   The requirements of the view.
  */
private class BaseScalaFXStartView(scene: Scene, requirements: View.Requirements[StartController])
    extends BaseScalaFXView(scene, requirements)
    with StartView:

  override def parent: Parent = new VBox:
    spacing = 170

    val textFields: List[TextField] = List("Player 1", "Player 2").map { player =>
      new TextField:
        promptText = s"$player name: "
        alignment = Center
    }

    val vbox: VBox = new VBox:
      alignment = Center
      children ++= textFields.map(_.delegate)

    val button: Button = new Button("Start game"):
      id = "startButton"
      text = "Start"
      alignment = Center
      onAction = _ =>
        // TODO: Check if empty
        val players: Pair[Player] = textFields.map(tf => Player(tf.text.value)) match
          case List(p1, p2) => Pair(p1, p2)
        controller.startGame(players)

    vbox.children += button
    children += vbox
