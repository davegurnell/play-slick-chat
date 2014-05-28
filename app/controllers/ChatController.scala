package controllers

import play.api._
import play.api.libs.json._
import play.api.mvc._

import models._

object ChatController extends Controller {
  implicit val messageFormat   = Json.format[Message]
  implicit val authTokenFormat = Json.format[AuthToken]

  // JSON reader that reads { "text" : ??? }
  // as a string and correctly reports any
  // parse errors:
  val messageTextReads: Reads[String] =
    (__ \ "text").read[String]

  def login(name: String) = Action { request =>
    val token = ChatRoom.login(name)
    Ok(Json.toJson(token)).withHeaders(
      "Authorization" -> s"Bearer ${token.id}"
    )
  }

  def search = Action { request =>
    Ok(Json.toJson(ChatRoom.messages))
  }

  def create = Action { request =>
    val json = request.body.asJson.getOrElse(JsNull)

    Json.fromJson[CreateRoomRequest](json).fold(
      invalid = { errors =>
        BadRequest(errors.toString)
      },
      valid = { req =>
        ChatRoom.post(text)
        Ok(Json.toJson(ChatRoom.messages))
      }
    )
  }
}
