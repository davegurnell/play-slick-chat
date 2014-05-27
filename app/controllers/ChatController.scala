package controllers

import play.api._
import play.api.libs.json._
import play.api.mvc._

import models._

object ChatController extends Controller {
  def search = Action { request =>
    Ok(Json.toJson(ChatRoom.messages))
  }

  def create = Action { request =>
    val json = request.body.asJson.getOrElse(JsNull)

    (json \ "text").validate[String].fold(
      invalid = { errors =>
        BadRequest(errors.toString)
      },
      valid = { text =>
        ChatRoom.post(text)
        Ok(Json.toJson(ChatRoom.messages))
      }
    )
  }
}