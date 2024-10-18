package scothello.model.board

import scothello.BaseTest

class TiledBoardTest extends BaseTest with BoardTestsRanges:

  val bigBoardDim: (Int, Int) = (1000, 1000)

  "A TiledBoard" should "be instantiated with valid rows and columns" in {
    combinations foreach { (row: Int, col: Int) =>
      val board = TiledBoard(row, col)
      board.rows shouldEqual row
      board.cols shouldEqual col
    }
  }

  "A TiledBoard" should "initialize the correct number of tiles" in {
    combinations foreach { (row: Int, col: Int) =>
      val board = TiledBoard(row, col)
      board.tiles.size shouldEqual (row * col)
    }
  }

  "A TileBoard" should "have unique tiles based on their coordinates" in {
    combinations foreach { (row: Int, col: Int) =>
      val board = TiledBoard(row, col)
      val tileCoordinates = board.tiles.map(tile => (tile.row, tile.col))
      tileCoordinates.size shouldEqual board.tiles.size
    }
  }

  "A TileBoard" should "handle a large board" in {
    val board = TiledBoard(bigBoardDim._1, bigBoardDim._2)
    board.tiles.size shouldEqual (bigBoardDim._1 * bigBoardDim._2)
  }

  "A TiledBoard" should "throw an exception for invalid dimensions" in {
    an[IllegalArgumentException] should be thrownBy TiledBoard(0, 8)
    an[IllegalArgumentException] should be thrownBy TiledBoard(8, -1)
  }
