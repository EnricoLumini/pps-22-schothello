package scothello.model.components

import scothello.model.board.{CentralTiles, Tile}
import scothello.model.game.config.Player
import scothello.utils.Pair

/** A pawn in a game of Scothello.
  */
trait Pawn:

  /** The player that owns the pawn.
    */
  def player: Player

object Pawn:
  def apply(player: Player): Pawn = PawnImpl(player)
  private case class PawnImpl(player: Player) extends Pawn

/** A map of assigned pawns.
  */
type AssignedPawns = Map[Tile, Pawn]

object AssignedPawns:
  def empty: AssignedPawns = Map.empty[Tile, Pawn]

  /** Initializes the assigned pawns.
    *
    * @param players
    *   The players.
    * @param centralTiles
    *   The central tiles.
    * @return
    *   The assigned pawns.
    */
  def initial(players: Pair[Player], centralTiles: CentralTiles): AssignedPawns = Map(
    centralTiles.upperLeft -> Pawn(players._2),
    centralTiles.lowerRight -> Pawn(players._2),
    centralTiles.upperRight -> Pawn(players._1),
    centralTiles.lowerLeft -> Pawn(players._1)
  )

  extension (assignedPawns: AssignedPawns)
    /** Returns the number of pawns for each player.
      *
      * @return
      *   The number of pawns for each player.
      */
    def pawnCounts: Map[Player, Int] =
      assignedPawns.values
        .groupBy(_.player)
        .view
        .mapValues(_.size)
        .toMap
