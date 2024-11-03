package scothello.view.utils

import scalafx.scene.paint.Color
import scothello.model.game.config.PlayerColor

object ColorMapper:
  def toFxColor(playerColor: PlayerColor): Color = playerColor match
    case PlayerColor.Black => Color.Black
    case PlayerColor.White => Color.White
    case _                 => Color.Transparent
