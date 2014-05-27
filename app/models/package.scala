import scala.slick.driver.H2Driver.simple._

package object models {
  lazy val database = Database.forURL(
    "jdbc:h2:mem:testdata;MODE=MYSQL;DB_CLOSE_DELAY=-1",
    driver = "org.h2.Driver"
  )
}