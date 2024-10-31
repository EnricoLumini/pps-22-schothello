package scothello.model.board

import scothello.{BaseTest, PlayerProvider}
import scothello.model.components.AssignedPawns

class BoardTest extends BaseTest with BoardTestsRanges with PlayerProvider:

  it should "suggest the allowed positions at the start" in {
    val centralTiles: CentralTiles = Board().centralTiles
    val assignedPawns = AssignedPawns.initial(twoPlayers, centralTiles)
    val allowedTiles = AllowedTiles.calculate(twoPlayers._1, assignedPawns)

    val map = Map.from(
      Seq(
        Tile(2, 3) -> twoPlayers._1,
        Tile(3, 2) -> twoPlayers._1,
        Tile(4, 5) -> twoPlayers._1,
        Tile(5, 4) -> twoPlayers._1
      )
    )
    allowedTiles should have size 4
    allowedTiles shouldBe map
  }
