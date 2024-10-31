package scothello.model.board

import scothello.model.game.config.Player
import scothello.model.components.AssignedPawns

import scala.annotation.tailrec

/** A board for Schothello.
  *
  * @param rows
  *   The number of rows in the board.
  * @param cols
  *   The number of columns in the board.
  */
final case class Board(
    override val rows: Int = 8,
    override val cols: Int = 8
) extends TiledBoard(rows, cols):

  /** The central tiles of the board. */
  val centralTiles: CentralTiles = CentralTiles(
    upperLeft = Tile(rows / 2 - 1, cols / 2 - 1),
    upperRight = Tile(rows / 2 - 1, cols / 2),
    lowerLeft = Tile(rows / 2, cols / 2 - 1),
    lowerRight = Tile(rows / 2, cols / 2)
  )

/** The central tiles of the board.
  *
  * @param upperLeft
  *   The upper left tile.
  * @param upperRight
  *   The upper right tile.
  * @param lowerLeft
  *   The lower left tile.
  * @param lowerRight
  *   The lower right tile.
  */
case class CentralTiles(upperLeft: Tile, upperRight: Tile, lowerLeft: Tile, lowerRight: Tile)

type AllowedTiles = Map[Tile, Player]

object AllowedTiles:
  def empty: AllowedTiles = Map.empty[Tile, Player]

  def calculate(player: Player, assignedPawns: AssignedPawns): AllowedTiles =
    val opponentPlayer = assignedPawns.values.find(_.player != player).map(_.player)

    assignedPawns
      .collect {
        case (tile, pawn) if pawn.player == player =>
          val adjacentOpponents = getAdjacentOpponents(tile, opponentPlayer.get, assignedPawns)

          adjacentOpponents.flatMap { opponentTile =>
            calculateNewPosition(tile, opponentTile, player, assignedPawns) match
              case Some(newTile) if !assignedPawns.contains(newTile) => Some(newTile -> player)
              case _                                                 => None
          }
      }
      .flatten
      .toMap

  private def getAdjacentOpponents(tile: Tile, opponent: Player, assignedPawns: AssignedPawns): Seq[Tile] =
    val adjacentPositions = adjacentTiles(tile)
    adjacentPositions.filter { adjTile =>
      assignedPawns.get(adjTile).exists(_.player == opponent)
    }

  @tailrec
  private def calculateNewPosition(
      playerTile: Tile,
      opponentTile: Tile,
      player: Player,
      assignedPawns: AssignedPawns
  ): Option[Tile] =
    val newRow = opponentTile.row - (playerTile.row - opponentTile.row)
    val newCol = opponentTile.col - (playerTile.col - opponentTile.col)
    val newTile = Tile(newRow, newCol)

    if newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 then
      assignedPawns.get(newTile) match
        case Some(pawn) if pawn.player != player =>
          calculateNewPosition(opponentTile, newTile, player, assignedPawns)
        case None =>
          Some(newTile)
        case _ =>
          None
    else None

  private def adjacentTiles(tile: Tile): Seq[Tile] =
    val (x, y) = (tile.row, tile.col)
    Seq(
      Tile(x - 1, y - 1),
      Tile(x, y - 1),
      Tile(x + 1, y - 1),
      Tile(x - 1, y),
      Tile(x + 1, y),
      Tile(x - 1, y + 1),
      Tile(x, y + 1),
      Tile(x + 1, y + 1)
    ).filter(t => t.row >= 0 && t.row < 8 && t.col >= 0 && t.col < 8)
