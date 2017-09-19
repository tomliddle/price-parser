package tomliddle

import java.time.LocalDate

object Implicits {

  implicit def localDateOrdering: Ordering[LocalDate] = Ordering.fromLessThan(_ isBefore _)

}
