package scothello.model.board

import scothello.model.game.config.Player
import scothello.model.components.AssignedPawns

import scala.annotation.tailrec
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/** A board for Schothello.
  */
final case class Board() extends TiledBoard(8, 8):

  /** The central tiles of the board. */
  val centralTiles: CentralTiles = CentralTiles(
    upperLeft = Tile(rows / 2 - 1, cols / 2 - 1),
    upperRight = Tile(rows / 2 - 1, cols / 2),
    lowerLeft = Tile(rows / 2, cols / 2 - 1),
    lowerRight = Tile(rows / 2, cols / 2)
  )

  /** The total number of tiles on the board. */
  val size: Int = rows * cols

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

  /** Returns an empty AllowedTiles. */
  def empty: AllowedTiles = Map.empty[Player, Set[Tile]]

  /** Initializes the AllowedTiles for a player.
    *
    * @param player
    *   The player.
    * @param assignedPawns
    *   The assigned pawns.
    * @return
    *   The AllowedTiles.
    */
  def initial(player: Player, assignedPawns: AssignedPawns): AllowedTiles =
    this.resetMap()
    this.allowedTiles.clear()
    calculate(player, assignedPawns)

  /** Calculates the AllowedTiles for a player.
    *
    * @param player
    *   The player.
    * @param assignedPawns
    *   The assigned pawns.
    * @return
    *   The AllowedTiles.
    */
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

  /** Retrieves the adjacent tiles containing pawns belonging to the opponent of the specified player.
    *
    * @param tile
    *   the reference tile from which to find adjacent opponent pawns
    * @param player
    *   the player whose opponent's pawns are being searched
    * @param assignedPawns
    *   the current map of tiles and their assigned pawns
    * @return
    *   a sequence of adjacent tiles occupied by opponent pawns
    */
  private def getAdjacentOpponents(tile: Tile, player: Player, assignedPawns: AssignedPawns): Seq[Tile] =
    val adjacentPositions = adjacentTiles(tile)
    adjacentPositions.filter { adjTile =>
      assignedPawns.get(adjTile).exists(_.player != player)
    }

  /** Calculates the new position of a pawn.
    *
    * @param playerTile
    *   The player tile.
    * @param opponentTile
    *   The opponent tile.
    * @param player
    *   The player.
    * @param assignedPawns
    *   The assigned pawns.
    * @return
    *   The new position of the pawn.
    */
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

  /** Retrieves the adjacent tiles of a given tile.
    *
    * @param tile
    *   the reference tile from which to find adjacent tiles
    * @return
    *   a sequence of adjacent tiles
    */
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

  /** Checks if a player has no allowed moves.
    *
    * @param allowedTiles
    *   The allowed tiles.
    * @param player
    *   The player.
    * @return
    *   True if the player has no allowed moves, false otherwise.
    */
  def checkIfPlayerNoAllowedMoves(allowedTiles: AllowedTiles, player: Player): Boolean =
    allowedTiles.get(player).exists(_.isEmpty)

  /** Checks if there are no allowed moves.
    *
    * @param allowedTiles
    *   The allowed tiles.
    * @return
    *   True if there are no allowed moves, false otherwise.
    */
  def checkIfNoAllowedMoves(allowedTiles: AllowedTiles): Boolean =
    allowedTiles.forall { case (_, tiles) => tiles.isEmpty }

  /** Checks if a tile is allowed.
    *
    * @param allowedTiles
    *   The allowed tiles.
    * @param player
    *   The player.
    * @param tile
    *   The tile.
    * @return
    *   True if the tile is allowed, false otherwise.
    */
  def checkIfTileIsAllowed(allowedTiles: AllowedTiles, player: Player, tile: Tile): Boolean =
    allowedTiles.get(player).exists(_.contains(tile))

  /** Resets the flips map. */
  def resetMap(): Unit =
    flipsMap.clear()
