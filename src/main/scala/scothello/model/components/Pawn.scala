package scothello.model.components

import scothello.model.board.{CentralTiles, Tile}
import scothello.model.game.config.Player
import scothello.utils.Pair

/** A pawn in a game of Scothello.
  */
trait Pawn:
  def player: Player

object Pawn:
  def apply(player: Player): Pawn = PawnImpl(player)
  private case class PawnImpl(player: Player) extends Pawn

/** A map of assigned pawns.
  */
type AssignedPawns = Map[Tile, Pawn]

object AssignedPawns:
  def empty: AssignedPawns = Map.empty[Tile, Pawn]

  def initial(players: Pair[Player], centralTiles: CentralTiles): AssignedPawns = Map(
    centralTiles.upperLeft -> Pawn(players._1),
    centralTiles.lowerRight -> Pawn(players._1),
    centralTiles.upperRight -> Pawn(players._2),
    centralTiles.lowerLeft -> Pawn(players._2)
  )
