package scothello

import org.scalatest.flatspec.AnyFlatSpec

class TestMain extends AnyFlatSpec:

  "An dummyFunction" should "return 1" in:
    assert(dummyFunction() == 1)

  "An dummyFunction2" should "return test" in:
    assert(dummyFunction2() == "test")
