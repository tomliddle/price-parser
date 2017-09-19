package example

import java.io.IOException

import org.scalatest._

class PricesSpec extends FlatSpec with Matchers {

  "The daily prices method" should "get the historic prices for GOOG" in {

    val prices =  Prices.dailyPrices("GOOG")

    prices.size should be >= 250
  }

  it should "return an empty list when getting a wrong ticker" in {

    val prices =  Prices.dailyPrices("WRONG-TICKER")

    prices.size should be(0)
  }

  it should "return an empty list when getting an empty string param" in {

    val prices =  Prices.dailyPrices("")

    prices.size should be(0)
  }

  "The returns method" should "get the returns for GOOG" in {

    val rets = Prices.returns("GOOG")

    rets.size should be >= 250
  }

  it should "get an empty list given a wrong ticker" in {

    val rets = Prices.returns("")

    rets.size should be(0)
  }



}
