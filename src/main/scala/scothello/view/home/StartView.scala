package scothello.view.home

import scalafx.geometry.Pos.{Center, CenterRight, TopCenter}
import scalafx.scene.{Node, Parent, Scene}
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{Background, VBox}
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
    spacing = 30
    alignment = Center
    stylesheets = List("file:style/startpage.css")

    val player1Label = new Label("Player 1:")
    val player1Field: TextField = new TextField:
      promptText = "Enter Player 1 name"
    children += player1Label += player1Field

    val player2Label = new Label("Player 2:")
    val player2Field: TextField = new TextField:
      promptText = "Enter Player 2 name"
    children += player2Label += player2Field

    val button: Button = new Button("Start game"):
      id = "startButton"
      text = "Start"
      alignment = Center
      onAction = _ =>
        // TODO: Check if empty
        val players: Pair[Player] = (
          Player(player1Field.text.value.trim),
          Player(player2Field.text.value.trim)
        )

        controller.startGame(players)

    children += button
