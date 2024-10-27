package scothello.model.game.config

import scothello.BaseTest

class PlayerTest extends BaseTest:

  "A Player" should "exists" in {
    val player = Player("noname")
    player should not be null
  }

  it should "have a name" in {
    val player = Player("name")
    player.name shouldBe "name"
  }

  it should "have no color if not specified" in {
    val player = Player("name")
    player.color shouldBe PlayerColor.NoColor
  }

  it should "have black color if created with it" in {
    val player = Player("name", PlayerColor.Black)
    player.color shouldBe PlayerColor.Black
  }

  it should "have white color if created with it" in {
    val player = Player("name", PlayerColor.White)
    player.color shouldBe PlayerColor.White
  }

  it should "be created empty" in {
    val player = Player.empty
    player.name shouldBe empty
    player.color shouldBe PlayerColor.NoColor
  }
