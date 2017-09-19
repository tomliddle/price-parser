package example

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import com.typesafe.scalalogging.Logger

import scala.util.{Failure, Success, Try}

object Prices {

  private val logger = Logger("prices")

  // Format is 9-Aug-17
  private final val formatter = DateTimeFormatter.ofPattern("d-MMM-yy")

  private def pricesURL(ticker: String): String = s"https://www.google.com/finance/historical?q=NASDAQ:$ticker&output=csv"


  //Task

  //Please write Scala functions that will return, feel free to adapt function signatures, these are just indicative. Use Close price everywhere:



  /**
    * 1 - 1 year historic prices given a ticker
    * @param ticker e.g. GOOG
    * @return List of tuple containing Date and Price or an empty list if ticker not found
    */
  def dailyPrices(ticker: String): List[(LocalDate, Double)] = {
    val src = Try { scala.io.Source.fromURL(pricesURL(ticker)) }

    val res = src match {
      case Success(s) =>
        s.getLines.drop(1).map { l =>
          val t = l.split(",")
          (LocalDate.parse(t(0), formatter), t(4).toDouble)
        }.toList
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

    if (dP.nonEmpty)
      dP.tail.foldLeft(List[(LocalDate, Double)](), dP.head._2) { (acc, curr) =>
        val ret = (curr._2 - acc._2) / acc._2
        ((curr._1, ret) :: acc._1, curr._2)
      }._1
    else List[(LocalDate, Double)]()
  }



  /* 3 – 1 year mean returns */

  /*def meanReturn(ticker:String): Double = {

  }*/

}


