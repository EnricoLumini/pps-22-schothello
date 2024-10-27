package scothello

import scothello.model.game.config.{Player, PlayerColor}
import scothello.utils.Pair

trait PlayerProvider:

  protected def player(): Player =
    Player("Player", PlayerColor.Black)
  protected def players(): Pair[Player] =
    (Player("Player 1", PlayerColor.Black), Player("Player 2", PlayerColor.White))

  val onePlayer: Player = player()
  val twoPlayers: Pair[Player] = players()
