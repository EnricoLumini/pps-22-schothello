package scothello.model.board

/** A Tile in the space rapresented by 2 coordinates.
  */
trait Tile:
  def row: Int
  def col: Int

object Tile:
  def apply(row: Int, col: Int): Tile =
    require(row >= 0, s"Row must be non-negative, but got: $row")
    require(col >= 0, s"Column must be non-negative, but got: $col")
    TileImpl(row, col)
  private case class TileImpl(row: Int, col: Int) extends Tile
