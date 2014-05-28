package models

import java.util.Date
import scala.slick.driver.H2Driver.simple._
import play.api.libs.json._

case class AuthToken(id: String, name: String)

object AuthToken {
  def create(name: String) = {
    import java.util.UUID.randomUUID
    AuthToken(randomUUID.toString, name)
  }
}

class AuthTokens(tag: Tag) extends Table[AuthToken](tag, "authtokens") {
  def id   = column[String]("id")
  def name = column[String]("name")

  def * = (id, name) <> ((AuthToken.apply _).tupled, AuthToken.unapply)
}

object AuthTokens {
  val all = TableQuery[AuthTokens]
}
