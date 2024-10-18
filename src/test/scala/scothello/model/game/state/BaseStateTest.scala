package scothello.model.game.state

import scothello.BaseTest
import scothello.model.game.config.Player

class BaseStateTest extends BaseTest:

  protected def players(): (Player, Player) =
    (Player("Player 1"), Player("Player 2"))

  val twoPlayers: (Player, Player) = players()
