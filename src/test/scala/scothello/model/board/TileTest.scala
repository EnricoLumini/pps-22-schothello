package scothello.model.board

import scothello.BaseTest

class TileTest extends BaseTest:

  "A Tile" should "have 2 coordinates" in {
    assertCompiles("Tile(0, 0)")
    assertDoesNotCompile("Tile()") // Missing coordinates
    assertDoesNotCompile("Tile(0)") // Few coordinates
  }
