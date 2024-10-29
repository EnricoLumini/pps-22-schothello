package scothello.model.components

import scothello.BaseTest
import scothello.PlayerProvider
import scothello.model.board.{Board, CentralTiles, Tile}
import scothello.model.game.config.Player

class PawnTest extends BaseTest with PlayerProvider:

  "A pawn" should "have a player" in {
    val pawn = Pawn(onePlayer)
    pawn.player shouldBe onePlayer
  }

class AssignedPawnsTest extends BaseTest with PlayerProvider:

  "Assigned pawns" should "initialized as empty" in {
    val assignedPawns = AssignedPawns.empty
    assignedPawns shouldBe Map.empty
  }

  it should "be initialized with four pawns" in {
    val centralTiles: CentralTiles = Board().centralTiles
    val assignedPawns = AssignedPawns.initial(twoPlayers, centralTiles)
    assignedPawns should have size 4
    assignedPawns(centralTiles.upperLeft).player shouldBe twoPlayers._2
    assignedPawns(centralTiles.lowerRight).player shouldBe twoPlayers._2
    assignedPawns(centralTiles.upperRight).player shouldBe twoPlayers._1
    assignedPawns(centralTiles.lowerLeft).player shouldBe twoPlayers._1
  }
