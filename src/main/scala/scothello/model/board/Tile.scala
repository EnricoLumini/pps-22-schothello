package scothello.model.board

/** A Tile in the space rapresented by 2 coordinates.
  */
trait Tile:
  def row: Int
  def col: Int

object Tile:
  def apply(row: Int, col: Int): Tile = TileImpl(row, col)
  private case class TileImpl(row: Int, col: Int) extends Tile
