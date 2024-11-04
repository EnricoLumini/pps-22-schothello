package scothello.model.components

import scothello.model.board.Board
import scothello.{BaseTest, PlayerProvider}

class ScoreTest extends BaseTest with PlayerProvider:

  "A score" should "be initialized as empty" in {
    val emptyScore = Scores.empty
    emptyScore shouldBe Map.empty
  }

  it should "be 2 for each players at game start" in {
    val board = Board()
    val initialPawns = AssignedPawns.initial(twoPlayers, board.centralTiles)
    val validScore = Scores.calculateScores(initialPawns)
    validScore shouldBe Map(twoPlayers._1 -> 2, twoPlayers._2 -> 2)
  }

  it should "be correct after a few moves" in {
    val board = Board()
    val initialPawns = AssignedPawns.initial(twoPlayers, board.centralTiles)
    val newPawns = initialPawns + (board.centralTiles.upperLeft -> Pawn(twoPlayers._1))
    val score = Scores.calculateScores(newPawns)
    score shouldBe Map(twoPlayers._1 -> 3, twoPlayers._2 -> 1)
  }
