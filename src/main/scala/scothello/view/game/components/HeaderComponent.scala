package scothello.view.game.components

import scalafx.geometry.Pos.Center
import scalafx.scene.Scene
import scalafx.scene.layout.{HBox, VBox}
import scothello.model.game.config.Player
import scothello.model.game.state.GameState
import scothello.utils.Pair
import scothello.view.utils.ResettableObjectProperty

object HeaderComponent:

  def headerComponent(reactiveGameState: ResettableObjectProperty[GameState], headerHeight: Double)(using
      displayScene: Scene
  ): HBox =
    new HBox:
      prefHeight = headerHeight
      alignment = Center

      val players: Pair[Player] = reactiveGameState.value.players
      val playerComponentWidth: Double = displayScene.width.value / 3
      val player1Component: VBox = PlayerComponent.playerComponent(reactiveGameState, players._1)
      val clockComponent: VBox = ClockComponent.clockComponent(reactiveGameState)
      val player2Component: VBox = PlayerComponent.playerComponent(reactiveGameState, players._2)

      children.addAll(player1Component, clockComponent, player2Component)
