package scothello.model.components

import scothello.{BaseTest, PlayerProvider}

class ScoreTest extends BaseTest with PlayerProvider:

  "A Score" should "be initialized as empty" in {
    val scores = Scores.empty(twoPlayers)
    scores shouldBe Map(twoPlayers._1 -> 0, twoPlayers._2 -> 0)
  }
