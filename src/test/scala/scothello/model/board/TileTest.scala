package scothello.model.board

import scothello.BaseTest

class TileTest extends BaseTest with BoardTestsRanges:

  "A Tile" should "have 2 coordinates" in {
    assertCompiles("Tile(0, 0)")
    assertDoesNotCompile("Tile()")
    assertDoesNotCompile("Tile(0)")
  }

  it should "be equals based on its coordinates" in {
    combinations foreach { (r: Int, c: Int) =>
      val tile1 = Tile(r, c)
      val tile2 = Tile(r, c)
      tile1 shouldBe tile2
    }
  }

  it should "not allow negative coordinates" in {
    an[IllegalArgumentException] should be thrownBy Tile(-1, 2)
    an[IllegalArgumentException] should be thrownBy Tile(0, -1)
  }
