package scothello.model.board

import scothello.model.game.config.Player
import scothello.model.components.AssignedPawns

import java.security.PrivateKey
import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

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

type AllowedTiles = Map[Player, Set[Tile]]

object AllowedTiles:
  val flipsMap = mutable.Map.empty[Tile, ArrayBuffer[Tile]]
  private val flips: ArrayBuffer[Tile] = ArrayBuffer.empty
  private val allowedTiles = mutable.Map.empty[Player, Set[Tile]]

  def empty: AllowedTiles = Map.empty[Player, Set[Tile]]

  def initial(player: Player, assignedPawns: AssignedPawns): AllowedTiles =
    calculate(player, assignedPawns)

  def calculate(player: Player, assignedPawns: AssignedPawns): AllowedTiles =
    allowedTiles.update(player, calculatePlayerAllowedTiles(player, assignedPawns).getOrElse(player, Set()))
    val allowed: AllowedTiles = allowedTiles.toMap
    allowed

  private def calculatePlayerAllowedTiles(player: Player, assignedPawns: AssignedPawns): AllowedTiles =
    assignedPawns
      .collect {
        case (tile, pawn) if pawn.player == player =>
          val adjacentOpponents = getAdjacentOpponents(tile, player, assignedPawns)
          adjacentOpponents.flatMap { opponentTile =>
            calculateNewPosition(tile, opponentTile, player, assignedPawns) match
              case Some(newTile) =>
                flips.clear()
                Some(player -> newTile)
              case _ =>
                flips.clear()
                None
          }
      }
      .flatten
      .groupBy(_._1)
      .map { case (player, tiles) =>
        player -> tiles.map(_._2).toSet
      }

  private def getAdjacentOpponents(tile: Tile, player: Player, assignedPawns: AssignedPawns): Seq[Tile] =
    val adjacentPositions = adjacentTiles(tile)
    adjacentPositions.filter { adjTile =>
      assignedPawns.get(adjTile).exists(_.player != player)
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

    if newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 then
      val newTile = Tile(newRow, newCol)
      assignedPawns.get(newTile) match
        case Some(pawn) if pawn.player != player =>
          flips += opponentTile
          calculateNewPosition(opponentTile, newTile, player, assignedPawns)
        case None =>
          flips += opponentTile
          flipsMap.get(newTile) match
            case Some(existingFlips) =>
              existingFlips ++= flips
            case None =>
              flipsMap += (newTile -> flips.clone())

          Some(newTile)
        case _ =>
          None
    else None

  private def adjacentTiles(tile: Tile): Seq[Tile] =
    val (x, y) = (tile.row, tile.col)
    Seq(
      (x - 1, y - 1),
      (x, y - 1),
      (x + 1, y - 1),
      (x - 1, y),
      (x + 1, y),
      (x - 1, y + 1),
      (x, y + 1),
      (x + 1, y + 1)
    ).collect {
      case (row, col) if row >= 0 && row < 8 && col >= 0 && col < 8 => Tile(row, col)
    }

  def checkIfPlayerNoAllowedMoves(allowedTiles: AllowedTiles, player: Player): Boolean =
    allowedTiles.get(player).exists(_.isEmpty)

  def checkIfNoAllowedMoves(allowedTiles: AllowedTiles): Boolean =
    allowedTiles.forall { case (_, tiles) => tiles.isEmpty }

  def checkIfTileIsAllowed(allowedTiles: AllowedTiles, player: Player, tile: Tile): Boolean =
    allowedTiles.get(player).exists(_.contains(tile))

  def resetMap(): Unit =
    flipsMap.clear()
