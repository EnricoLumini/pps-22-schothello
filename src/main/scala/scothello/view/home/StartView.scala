package scothello.view.home

import scalafx.geometry.Pos.Center
import scalafx.scene.{Node, Parent, Scene}
import scalafx.scene.control.{Button, Label, TextField}
import scalafx.scene.layout.VBox
import scothello.controller.home.StartController
import scothello.game.pages.Pages
import scothello.utils.Pair
import scothello.view.{BaseScalaFXView, View}
import scalafx.Includes.jfxNode2sfx

trait StartView extends View

object StartView:
  def apply(scene: Scene, requirements: View.Requirements[StartController]): StartView =
    BaseScalaFXStartView(scene, requirements)

/** Represents the home view.
  * @param mainScene
  *   The parent pane.
  * @param requirements
  *   The requirements of the view.
  */
private class BaseScalaFXStartView(mainScene: Scene, requirements: View.Requirements[StartController])
    extends BaseScalaFXView(mainScene, requirements)
    with StartView:

  override def parent: Parent = new VBox:
    spacing = 30
    alignment = Center
    stylesheets = List(getClass.getResource("/styles/startpage.css").toExternalForm)

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
        removeById("error-message")
        val player1Name = player1Field.text.value.trim
        val player2Name = player2Field.text.value.trim

        var validInput = true
        if player1Name.isEmpty then
          markFieldInvalid(player1Field)
          validInput = false

        if player2Name.isEmpty then
          markFieldInvalid(player2Field)
          validInput = false

        if player1Name == player2Name && (player1Name.nonEmpty && player2Name.nonEmpty) then
          markFieldInvalid(player1Field)
          markFieldInvalid(player2Field)
          validInput = false

          val errorMessage = new Label("Names must be different"):
            id = "error-message"
          errorMessage.setStyle(
            "-fx-text-fill: red;" +
              "-fx-font-size: 15"
          )
          children += errorMessage

        if validInput then
          controller.startGame((player1Name, player2Name))
          navigateToGamePage()

    children += button

    private def resetFieldStyle(field: TextField): Unit =
      field.setStyle("")

    private def markFieldInvalid(field: TextField): Unit =
      field.setStyle("-fx-border-color: red;")

    private def addTextFieldListener(field: TextField): Unit =
      field.text.onChange { (_, _, newValue) =>
        if newValue.trim.nonEmpty then resetFieldStyle(field)
      }

    addTextFieldListener(player1Field)
    addTextFieldListener(player2Field)

    private def removeById(nodeId: String): Unit =
      children.find(_.id.value == nodeId).foreach(children -= _)

  private def navigateToGamePage(): Unit =
    this.navigateTo(Pages.Game)
