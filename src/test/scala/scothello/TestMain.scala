package scothello

import org.scalatest.flatspec.AnyFlatSpec

class TestMain extends AnyFlatSpec:

  "An dummyFunction" should "return 1" in:
    assert(dummyFunction() == 1)

  "And dummyFunction2" should "return 2" in:
    assert(dummyFunction2() == 2)
