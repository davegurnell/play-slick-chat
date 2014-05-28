package controllers

import play.api._
import play.api.libs.json._
import play.api.mvc._

import models._

object ChatController extends Controller with AuthHelpers {
  implicit val messageFormat   = Json.format[Message]
  implicit val authTokenFormat = Json.format[AuthToken]

  // JSON reader that reads { "text" : ??? }
  // as a string and correctly reports any
  // parse errors:
  val messageTextReads: Reads[String] =
    (__ \ "text").read[String]

  def login(name: String) = Action { request =>
    val token = AuthService.login(name)
    Ok(Json.toJson(token)).withAuthToken(token)
  }

  def search = Action { request =>
    Ok(Json.toJson(ChatService.messages))
  }

  def create = AuthAction { (request, token) =>
    val json = request.body.asJson.getOrElse(JsNull)

    Json.fromJson(json)(messageTextReads).fold(
      invalid = { errors =>
        BadRequest(errors.toString)
      },
      valid = { text =>
        ChatService.post(text)
        Ok(Json.toJson(ChatService.messages))
      }
    )
  }
}
