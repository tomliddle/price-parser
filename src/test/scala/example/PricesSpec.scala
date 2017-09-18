package example

import org.scalatest._

class PricesSpec extends FlatSpec with Matchers {

  "The Prices object" should "get the historic prices" in {

    val prices =  Prices.dailyPrices("GOOG")

    prices.size should be(100)
  }
}
