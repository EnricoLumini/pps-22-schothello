package scothello.model.board

class TiledBoard(
    val rows: Int,
    val cols: Int
):
  require(rows > 0, s"Number of rows must be non-negative, but got: $rows")
  require(cols > 0, s"Number of columns must be non-negative, but got: $cols")
  val tiles: Set[Tile] =
    (for
      r <- 0 until rows
      c <- 0 until cols
    yield Tile(r, c)).toSet
