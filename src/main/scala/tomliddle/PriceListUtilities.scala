package tomliddle

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import Implicits._

trait PriceListUtilities {

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
