package models

import play.api.libs.json._
import java.util.Date

case class Message(text: String, createdAt: Date = new Date())

object Message {
  implicit val format = Json.format[Message]
}