package scothello.view.game.components

import scalafx.Includes.*
import scalafx.beans.binding.Bindings
import scalafx.beans.property.ObjectProperty
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.control.Label
import scalafx.scene.layout.{HBox, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.Circle
import scothello.model.game.config.Player
import scothello.model.game.state.GameState
import scothello.view.utils.ColorMapper

object PlayerComponent:

  def playerComponent(player: Player)(using mainScene: Scene, reactiveState: ObjectProperty[GameState]): VBox =
    new VBox:
      prefHeight = mainScene.height.value / 3
      alignment = Center

      val leftPlayerHBox: HBox = new HBox:
        alignment = Center
        spacing = 20

        val player1PawnIcon: Circle = playerIcon(player)
        val player1NameLabel: Label = playerLabel(player)

        children.addAll(player1PawnIcon, player1NameLabel)

      val player1Score: Label = playerScore(reactiveState, player)

      children.addAll(leftPlayerHBox, player1Score)

  private def playerIcon(player: Player)(using reactiveState: ObjectProperty[GameState]): Circle = new Circle:
    id = "playerIcon"
    radius = 15
    fill = ColorMapper.toFxColor(player.color)
    strokeWidth = 2
    stroke = if reactiveState.value.turn.player == player then Color.Red else ColorMapper.toFxColor(player.color)
    Bindings
      .createObjectBinding(
        () => if reactiveState.value.turn.player == player then Color.Red else ColorMapper.toFxColor(player.color),
        reactiveState
      )
      .onChange((_, _, newColor) => stroke = newColor)

  private def playerLabel(player: Player): Label = new Label:
    id = "playerLabel"
    text = player.name

  private def playerScore(reactiveState: ObjectProperty[GameState], player: Player): Label = new Label:
    id = "scoreLabel"
    text <== reactiveState.map(_.playerScores.getOrElse(player, 0).toString)
