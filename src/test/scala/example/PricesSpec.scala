package example

import java.io.IOException

import org.scalatest._

class PricesSpec extends FlatSpec with Matchers {

  "The Prices object" should "get the historic prices" in {

    val prices =  Prices.dailyPrices("GOOG")

    prices.size should be >= 250
  }

  it should "throw an exception when getting a wrong ticker" in {

    assertThrows[IOException] { // Result type: Assertion
      Prices.dailyPrices("WRONG-TICKER")
    }
  }



}
