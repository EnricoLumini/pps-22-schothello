package scothello.model.board

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
