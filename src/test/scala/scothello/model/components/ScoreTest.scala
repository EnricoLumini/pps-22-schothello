package scothello.model.components

import scothello.{BaseTest, PlayerProvider}

class ScoreTest extends BaseTest with PlayerProvider:

  "A score" should "be initialized as empty" in {
    val emptyScore = Scores.empty
    emptyScore shouldBe Map.empty
  }

  it should "create a valid score fro players" in {
    val validScore = Scores.initialize(twoPlayers)
    validScore shouldBe Map(twoPlayers._1 -> 0, twoPlayers._2 -> 0)
  }
