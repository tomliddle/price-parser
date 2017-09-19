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

  "The mean return method" should "get the mean return for GOOG" in {

    val mean = Prices.meanReturn("GOOG")

    // Should be able to calculate a mean return
    mean.isDefined should be(true)

    // As using live data it is reasonable to assume the avg return will never exceed -100% or 100% a day.
    mean.get should be <= 1.0
    mean.get should be >= -1.0
  }

  it should "return None when given a bad ticker" in {

    val mean = Prices.meanReturn("WRONG TICKER")

    // Should be able to calculate a mean return
    mean.isDefined should be(false)
  }



}
