package scothello.view.utils

import scalafx.beans.property.ObjectProperty
import scalafx.beans.value.ObservableValue
import scothello.model.game.config.{Player, PlayerColor}
import scothello.model.game.state.GameState
import scalafx.beans.BeanIncludes.jfxObservableValue2sfx

object ReactiveStateUtils:

  def getPlayerColor(
      reactiveState: ObjectProperty[GameState],
      player: Player
  ): ObservableValue[PlayerColor, PlayerColor] =
    reactiveState.delegate.map { state =>
      state.players.find(_.name == player.name).map(_.color).getOrElse(PlayerColor.NoColor)
    }
