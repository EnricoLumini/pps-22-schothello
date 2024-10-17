package scothello.model.game.config

import scothello.BaseTest

class PlayerTest extends BaseTest:

  "A Player" should "exists" in {
    val player = Player("noname")
    player should not be null
  }

  it should "have a name" in {
    val player = Player("name")
    player.name should be("name")
  }
