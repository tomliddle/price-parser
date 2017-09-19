package example

import java.io.IOException
import java.time.LocalDate

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

  "PriceListParser" should "parse a list of data" in {
    val p = new PriceListParser {}

    val prices = List(
      "Date,Open,High,Low,Close,Volume",
      "15-Sep-17,924.66,926.49,916.36,920.29,2505430",
      "14-Sep-17,931.25,932.77,924.00,925.11,1397644",
      "13-Sep-17,930.66,937.25,929.86,935.09,1102631",
      "12-Sep-17,932.59,933.48,923.86,932.07,1134397"
    )

    val parsedPrices = p.parsePrices(prices)

    parsedPrices.size should be(4)

    parsedPrices.head._1 should be(LocalDate.of(2017, 9, 15))

    parsedPrices.head._2 should be(920.29)
  }

}
