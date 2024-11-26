package scothello.model.board

import scothello.{BaseTest, PlayerProvider}
import scothello.model.components.{AssignedPawns, Pawn}

import scala.collection.mutable.ArrayBuffer

class BoardTest extends BaseTest with BoardTestsRanges with PlayerProvider:

  def createInitialAssignedPawns(): AssignedPawns =
    val centralTiles = Board().centralTiles
    AssignedPawns.initial(twoPlayers, centralTiles)

  "AllowedTiles" should "initialize correctly for a player at the start of the game" in {
    val assignedPawns = createInitialAssignedPawns()
    val allowedTiles = AllowedTiles.initial(twoPlayers._1, assignedPawns)
    val expectedTiles = Set(
      Tile(2, 3),
      Tile(3, 2),
      Tile(4, 5),
      Tile(5, 4)
    )

    allowedTiles(twoPlayers._1) shouldBe expectedTiles
  }

  it should "calculate allowed tiles after a move is made" in {
    val assignedPawns = createInitialAssignedPawns() + (Tile(2, 3) -> Pawn(twoPlayers._1))
    val allowedTiles = AllowedTiles.calculate(twoPlayers._2, assignedPawns)
    val expectedTiles = Set(
      Tile(1, 3),
      Tile(2, 4),
      Tile(3, 5),
      Tile(4, 2),
      Tile(5, 3)
    )

    allowedTiles(twoPlayers._2) shouldBe expectedTiles
  }

  it should "correctly identify when a player has no allowed moves" in {
    val assignedPawns = Map(
      Tile(3, 6) -> Pawn(twoPlayers._1),
      Tile(3, 7) -> Pawn(twoPlayers._1),
      Tile(4, 6) -> Pawn(twoPlayers._1),
      Tile(5, 6) -> Pawn(twoPlayers._1),
      Tile(5, 7) -> Pawn(twoPlayers._1),
      Tile(4, 7) -> Pawn(twoPlayers._2)
    )

    val allowedTiles = AllowedTiles.calculate(twoPlayers._1, assignedPawns)
    AllowedTiles.checkIfPlayerNoAllowedMoves(allowedTiles, twoPlayers._1) shouldBe true
  }

  it should "reset the flips map correctly" in {
    AllowedTiles.flipsMap += (Tile(2, 3) -> ArrayBuffer(Tile(3, 3)))
    AllowedTiles.resetMap()
    AllowedTiles.flipsMap shouldBe empty
  }

  it should "correctly identify if a tile is allowed for a player" in {
    val assignedPawns = createInitialAssignedPawns()
    val allowedTiles = AllowedTiles.initial(twoPlayers._1, assignedPawns)
    AllowedTiles.checkIfTileIsAllowed(allowedTiles, twoPlayers._1, Tile(2, 3)) shouldBe true
    AllowedTiles.checkIfTileIsAllowed(allowedTiles, twoPlayers._1, Tile(0, 0)) shouldBe false
  }

  it should "return an empty map when no allowed tiles exist" in {
    val allowedTiles = AllowedTiles.empty
    allowedTiles shouldBe empty
  }
