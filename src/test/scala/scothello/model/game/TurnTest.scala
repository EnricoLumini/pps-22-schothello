package scothello.model.game

import scothello.BaseTest
import scothello.model.game.Turn.EmptyTurn
import scothello.model.game.config.Player

class TurnTest extends BaseTest:

  val player: Player = Player("name")

  "A turn" should "have a number" in {
    val turn = Turn(1, player)
    turn.number shouldBe 1
  }

  it should "have a player" in {
    val turn = Turn(1, player)
    turn.player shouldBe player
  }

  it should "not allow to have a number less that 1" in {
    an[IllegalArgumentException] should be thrownBy Turn(0, player)
  }

  it should "be possible to be initialized" in {
    val turn = Turn.initial(player)
    turn.number shouldBe 1
    turn.player shouldBe player
  }

  it should "represent an empty turn" in {
    val emptyTurn = Turn.empty
    emptyTurn shouldBe a[EmptyTurn.type]
    emptyTurn.number shouldBe 0
    emptyTurn.player shouldBe Player.empty
  }
