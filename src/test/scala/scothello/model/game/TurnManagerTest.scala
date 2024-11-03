package scothello.model.game

import scothello.{BaseTest, PlayerProvider}

class TurnManagerTest extends BaseTest with PlayerProvider:

  val maxTurnNumberToTest: Int = 100

  "TurnManager" should "initialize the turn manager" in {
    TurnManager(twoPlayers)
    val initialTurn = TurnManager.initialTurn
    initialTurn.number shouldBe 1
    initialTurn.player shouldBe twoPlayers._1
  }

  def advanceToTurn(turnNumber: Int): Unit =
    TurnManager(twoPlayers)
    while TurnManager.nextTurn.number < turnNumber do {}

  def validateTurnTransition(startTurn: Int): Unit =
    TurnManager(twoPlayers)
    advanceToTurn(startTurn)
    val currTurn = TurnManager.nextTurn
    val nextTurn = TurnManager.nextTurn
    nextTurn.number shouldBe currTurn.number + 1
    nextTurn.player should not be currTurn.player

  it should "return correct next turn" in {
    validateTurnTransition(startTurn = 1)
  }

  it should "return correct turn after multiple turns" in {
    validateTurnTransition(startTurn = maxTurnNumberToTest)
  }

  it should "return always the same initial turn" in {
    TurnManager(twoPlayers)
    advanceToTurn(maxTurnNumberToTest)
    val initialTurn = TurnManager.initialTurn
    initialTurn.number shouldBe 1
    initialTurn.player shouldBe twoPlayers._1
  }
