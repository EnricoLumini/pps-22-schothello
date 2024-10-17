package scothello.model.board

import scothello.BaseTest
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class TileTest extends BaseTest with ScalaCheckPropertyChecks:

  "A Tile" should "have 2 coordinates" in {
    assertCompiles("Tile(0, 0)")
    assertDoesNotCompile("Tile()") // Missing coordinates
    assertDoesNotCompile("Tile(0)") // Few coordinates
  }

  it should "be equals based on its coordinates" in {
    forAll { (r: Int, c: Int) =>
      val tile1 = Tile(r, c)
      val tile2 = Tile(r, c)
      tile1 shouldBe tile2
    }
  }
