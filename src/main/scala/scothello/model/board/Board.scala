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
) extends TiledBoard(rows, cols)
