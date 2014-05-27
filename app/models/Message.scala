package models

import java.util.Date
import scala.slick.driver.H2Driver.simple._
import play.api.libs.json._

case class Message(text: String, createdAt: Long = System.currentTimeMillis)

class Messages(tag: Tag) extends Table[Message](tag, "messages") {
  def text      = column[String]("text")
  def createdAt = column[Long]("createdat")

  def * = (text, createdAt) <> (Message.tupled, Message.unapply)
}

object Messages extends TableQuery(new Messages(_)) {

}
