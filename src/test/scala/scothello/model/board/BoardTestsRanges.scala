package scothello.model.board

trait BoardTestsRanges:
  val rowRangeToTest: Range = 1 to 10
  val colRangeToTest: Range = 1 to 10
  val combinations: IndexedSeq[(Int, Int)] = for
    row <- rowRangeToTest
    col <- colRangeToTest
  yield (row, col)
