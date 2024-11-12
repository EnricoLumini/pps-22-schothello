package scothello.model.board

import scothello.{BaseTest, PlayerProvider}
import scothello.model.components.{AssignedPawns, Pawn}
import scothello.model.game.state.GameState

import scala.collection.mutable.ArrayBuffer

class BoardTest extends BaseTest with BoardTestsRanges with PlayerProvider:

  val centralTiles: CentralTiles = Board().centralTiles
  var assignedPawns: AssignedPawns = AssignedPawns.initial(twoPlayers, centralTiles)
  var allowedTiles: AllowedTiles = AllowedTiles.calculate(twoPlayers._1, assignedPawns)
  val state: GameState = GameState()

  it should "suggest the allowed positions at the start" in {
    val testMap = Map(
      twoPlayers._1 -> Set(
        Tile(2, 3),
        Tile(3, 2),
        Tile(4, 5),
        Tile(5, 4)
      )
    )
    allowedTiles shouldBe testMap
  }

  it should "flip the correct pawn after the first move (black in (2,3))" in {
    assignedPawns = assignedPawns + (Tile(2, 3) -> Pawn(twoPlayers._1))
    val array = AllowedTiles.flipsMap.getOrElse(Tile(2, 3), ArrayBuffer.empty[Tile])

    assignedPawns = assignedPawns.collect {
      case (t, pawn) if array.contains(t) =>
        t -> Pawn(twoPlayers._1)
      case (t, pawn) =>
        t -> pawn
    }

    val testMap = Map(
      Tile(2, 3) -> Pawn(twoPlayers._1),
      Tile(3, 3) -> Pawn(twoPlayers._1),
      Tile(4, 3) -> Pawn(twoPlayers._1),
      Tile(3, 4) -> Pawn(twoPlayers._1),
      Tile(4, 4) -> Pawn(twoPlayers._2)
    )

    assignedPawns shouldBe testMap
  }

  it should "suggest the allowed positions after the first move (black in (2,3))" in {
    allowedTiles = AllowedTiles.calculate(twoPlayers._2, assignedPawns)

    val testMap = Map(
      twoPlayers._1 -> Set(
        Tile(2, 3),
        Tile(3, 2),
        Tile(4, 5),
        Tile(5, 4)
      ),
      twoPlayers._2 -> Set(
        Tile(2, 2),
        Tile(2, 4),
        Tile(4, 2)
      )
    )

    allowedTiles shouldBe testMap
  }

  it should "flip the correct pawn after the move (white in (4,2))" in {
    assignedPawns = assignedPawns + (Tile(4, 2) -> Pawn(twoPlayers._2))
    val array = AllowedTiles.flipsMap.getOrElse(Tile(4, 2), ArrayBuffer.empty[Tile])

    assignedPawns = assignedPawns.collect {
      case (t, pawn) if array.contains(t) =>
        t -> Pawn(twoPlayers._2)
      case (t, pawn) =>
        t -> pawn
    }

    val testMap = Map(
      Tile(2, 3) -> Pawn(twoPlayers._1),
      Tile(3, 3) -> Pawn(twoPlayers._1),
      Tile(4, 3) -> Pawn(twoPlayers._2),
      Tile(3, 4) -> Pawn(twoPlayers._1),
      Tile(4, 4) -> Pawn(twoPlayers._2),
      Tile(4, 2) -> Pawn(twoPlayers._2)
    )

    assignedPawns shouldBe testMap
  }

  it should "suggest the allowed positions after the move (white in (4,2))" in {
    allowedTiles = AllowedTiles.calculate(twoPlayers._1, assignedPawns)

    val testMap = Map(
      twoPlayers._2 -> Set(
        Tile(2, 2),
        Tile(2, 4),
        Tile(4, 2)
      ),
      twoPlayers._1 -> Set(
        Tile(5, 1),
        Tile(5, 2),
        Tile(5, 3),
        Tile(5, 4),
        Tile(5, 5)
      )
    )

    allowedTiles shouldBe testMap
  }

  it should "suggest no positions when there are no more allowed ones" in {
    val assignedPawns = Map(
      Tile(3, 6) -> Pawn(twoPlayers._1),
      Tile(3, 7) -> Pawn(twoPlayers._1),
      Tile(4, 6) -> Pawn(twoPlayers._1),
      Tile(5, 6) -> Pawn(twoPlayers._1),
      Tile(5, 7) -> Pawn(twoPlayers._1),
      Tile(4, 7) -> Pawn(twoPlayers._2)
    )

    allowedTiles = AllowedTiles.calculate(twoPlayers._1, assignedPawns)

    AllowedTiles.checkIfPlayerNoAllowedMoves(allowedTiles, twoPlayers._1) shouldBe true
  }
