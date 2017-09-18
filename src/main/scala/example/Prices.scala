package example

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import scala.util.Try


object Prices {



  // Format is 9-Aug-17
  private final val formatter = DateTimeFormatter.ofPattern("d-MMM-yy")

  private def pricesURL(ticker: String): String = s"https://www.google.com/finance/historical?q=NASDAQ:$ticker&output=csv"


  //Task

  //Please write Scala functions that will return, feel free to adapt function signatures, these are just indicative. Use Close price everywhere:



  /**
    * 1 - 1 year historic prices given a ticker
    * @param ticker e.g. GOOG
    * @return Seq of tuple containing Date and Price
    */
  def dailyPrices(ticker: String): List[(LocalDate, Double)] = {
    val src = scala.io.Source.fromURL(pricesURL(ticker))

    val res = src.getLines.drop(1).map { l =>
      val t = l.split(",")
      (LocalDate.parse(t(0), formatter), t(4).toDouble)
    }.toList

    src.close()

    res
  }



  /* 2- daily returns, where return = ( Price_Today – Price_Yesterday)/Price_Yesterday */

  /*def returns(ticker: String): Seq[(LocalDate, Double)] = {

  }*/



  /* 3 – 1 year mean returns */

  /*def meanReturn(ticker:String): Double = {

  }*/

}


