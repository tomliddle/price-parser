package example


object Prices extends App {


  def pricesURL(ticker: String): String = s"https://www.google.com/finance/historical?q=NASDAQ:$ticker&output=csv"



  //Task

  //Please write Scala functions that will return, feel free to adapt function signatures, these are just indicative. Use Close price everywhere:



  /* 1 - 1 year historic prices given a ticker */
  def dailyPrices(ticker: String): Seq[(String, Double)] = {

  }



  /* 2- daily returns, where return = ( Price_Today – Price_Yesterday)/Price_Yesterday */

  def returns(ticker:String): Seq[(String, Double)] = {

  }



  /* 3 – 1 year mean returns */

  def meanReturn(ticker:String): Double = {

  }



  /* example usage */

  val googleDailyPrices = dailyPrices("GOOG")

  val googleDailyReturns = returns("GOOG")

  val googleAverageReturns = meanReturn("GOOG")
}


