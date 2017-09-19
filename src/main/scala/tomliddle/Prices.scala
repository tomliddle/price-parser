package tomliddle

import java.time.LocalDate

import com.typesafe.scalalogging.Logger

import scala.util.{Failure, Success, Try}

/**
  * Calculates daily prices, daily return and mean from google stock prices
  *
  */
object Prices extends PriceListUtilities {

  private val logger = Logger("prices")

  private def pricesURL(ticker: String): String = s"https://www.google.com/finance/historical?q=NASDAQ:$ticker&output=csv"

  /**
    * 1 - 1 year historic prices given a ticker
    * @param ticker e.g. GOOG
    * @return List of tuple containing Date and Price or an empty list if ticker not found
    */
  def dailyPrices(ticker: String): List[(LocalDate, Double)] = {
    val src = Try { scala.io.Source.fromURL(pricesURL(ticker)) }

    val res = src match {
      case Success(s) =>
        // Drop first line headers
        parsePrices(s.getLines.toList)

      case Failure(f) =>
        logger.warn(s"Failed to get prices: {}", f.getMessage)
        List[(LocalDate, Double)]()
    }

    src.foreach(_.close())

    res
  }

  /**
    * 2- daily returns, where return = ( Price_Today – Price_Yesterday)/Price_Yesterday
    * @param ticker e.g. GOOG
    * @return List of tuple containing Date and return
    */
  def returns(ticker: String): List[(LocalDate, Double)] = {
    val dP = dailyPrices(ticker)

    calculateReturn(dP)
  }

  /**
    * 3 – 1 year mean returns
    * @param ticker e.g. GOOG
    * @return Option of mean return. If no data then will return None
    */
  def meanReturn(ticker:String): Option[Double] = {
    val ret = returns(ticker)
    // take the second parameter of the returns value (i.e. the return) and sum. Divide by size to get mean return.
    mean(ret)
  }

}


