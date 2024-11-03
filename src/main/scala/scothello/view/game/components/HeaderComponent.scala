package scothello.view.game.components

import scalafx.beans.property.ObjectProperty
import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}
import scothello.model.game.config.Player
import scothello.model.game.state.GameState
import scothello.utils.Pair

object HeaderComponent:

  def headerComponent(using mainScene: Scene, reactiveState: ObjectProperty[GameState]): HBox =
    new HBox:
      prefHeight = mainScene.height.value / 8
      alignment = Center

      val players: Pair[Player] = reactiveState.value.players
      val player1Component: VBox = PlayerComponent.playerComponent(players._1)
      val clockComponent: VBox = ClockComponent.clockComponent
      val player2Component: VBox = PlayerComponent.playerComponent(players._2)

      children.addAll(player1Component, clockComponent, player2Component)
