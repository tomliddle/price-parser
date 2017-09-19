package tomliddle

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import Implicits._

import com.typesafe.scalalogging.Logger

import scala.util.{Failure, Success, Try}

object Prices extends PriceListParser {

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
        logger.info(s"Failed to get prices: {}", f.getMessage)
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


trait PriceListParser {

  // Format is 9-Aug-17
  private final val formatter = DateTimeFormatter.ofPattern("d-MMM-yy")

  /**
    * @param prices unparsed list of csv lines
    * @return list of dates and their prices
    */
  def parsePrices(prices: List[String]): List[(LocalDate, Double)] = {
    prices.drop(1).map { l =>
      val t = l.split(",")
      // Get the date from idx 0 and close price from idx 4
      (LocalDate.parse(t(0), formatter), t(4).toDouble)
    }
  }

  /**
    * Calculates the daily return
    * @param dailyPrices list of dates and their prices
    * @return list of dates and their daily return ordered by date ASC (note first date of dailyPrices is excluded as we cannot ascertain the return)
    */
  def calculateReturn(dailyPrices: List[(LocalDate, Double)]): List[(LocalDate, Double)] = {
    // An empty list cannot compute any return as we need prev price so check for this
    // A list with one element will still return an empty list as we fold over the tail anyhow
    // (as we need at least 2 elements to calculate a value)
    if (dailyPrices.nonEmpty) {
      val dPSorted = dailyPrices.sorted
      // FoldLeft stores a list of the results and the previous price
      // We reverse the list to order by date as we appended to front of list.
      // Take the tail as we cannot determine the return on the first days data
      dPSorted.tail.foldLeft(List[(LocalDate, Double)](), dPSorted.head._2) { (acc, curr) =>
        // Calculate the daily return: (curr price - prev price) / prev price
        val ret = (curr._2 - acc._2) / acc._2
        ((curr._1, ret) :: acc._1, curr._2)
      }._1.reverse
    }
    else List[(LocalDate, Double)]()
  }


  def mean(returns: List[(LocalDate, Double)]): Option[Double] = {
    if (returns.nonEmpty) Some(returns.map(_._2).sum / returns.size)
    else None
  }

}


