Price parser

* Note that further extensions to this could include the following
  * Using BigDecimal rather than Double
  * Non blocking calls to google api
  * Retries to google api given a failure
  * Handling bad data from google rather than throwing an exception
  * Further edge case testing to check for -ve's in data or missing data.