package tomliddle

import java.time.LocalDate

import org.scalatest.{FlatSpec, Matchers}

class PriceListParserSpec extends FlatSpec with Matchers {

  "PriceListParser parsePrices" should "parse a list of data" in {
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

  // Test for bad data. There isn't much we can do here if the api isn't returning correct data so ensure an exception is thrown
  it should "throw an ArrayIndexOutOfBoundsException if the data is malformed" in {
    val p = new PriceListParser {}

    val prices = List(
      "Date,Open,High,Low,Close,Volume",
      "15-Sep-17,924.66,926.49,916.36",
      "14-Sep-17,931.25,932.77,924.00,925.11,1397644",
      "13-Sep-17,930.66,937.25,929.86,935.09,1102631",
      "12-Sep-17,932.59,933.48,923.86,932.07,1134397"
    )

    assertThrows[IndexOutOfBoundsException] {
      val parsedPrices = p.parsePrices(prices)
    }
  }

  "PriceListParser calculateReturn" should "calculate the correct return" in {
    val p = new PriceListParser {}

    val prices = List(
      "Date,Open,High,Low,Close,Volume",
      "15-Sep-17,924.66,926.49,916.36,920.29,2505430",
      "14-Sep-17,931.25,932.77,924.00,925.11,1397644",
      "13-Sep-17,930.66,937.25,929.86,935.09,1102631",
      "12-Sep-17,932.59,933.48,923.86,932.07,1134397"
    )

    val ret = p.calculateReturn(p.parsePrices(prices))

    ret.size should be(3)

    ret.head._1 should be(LocalDate.of(2017, 9, 13))

    ret.head._2 should be(1)
  }

}
