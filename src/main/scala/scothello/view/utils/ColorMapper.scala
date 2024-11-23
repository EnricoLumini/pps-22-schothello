package scothello.view.utils

import scalafx.scene.paint.Color
import scothello.model.game.config.PlayerColor

/** Maps PlayerColor to JavaFX Color
  */
object ColorMapper:

  /** Maps PlayerColor to JavaFX Color
    *
    * @param playerColor
    *   PlayerColor to be mapped
    * @return
    *   JavaFX Color
    */
  def toFxColor(playerColor: PlayerColor): Color = playerColor match
    case PlayerColor.Black => Color.Black
    case PlayerColor.White => Color.White
    case _                 => Color.Transparent
