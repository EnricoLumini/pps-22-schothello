package scothello

import scothello.model.game.config.Player
import scothello.utils.Pair

trait PlayerProvider:

  protected def players(): Pair[Player] =
    (Player("Player 1"), Player("Player 2"))

  val twoPlayers: Pair[Player] = players()
