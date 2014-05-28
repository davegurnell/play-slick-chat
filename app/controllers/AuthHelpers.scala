package controllers

import models._
import play.api.libs.json._
import play.api.mvc._
import play.api.mvc.BodyParsers._

trait AuthHelpers {
  this: Results =>

  val TokenIdRegex = "^Bearer (.*)$".r

  def AuthAction(func: (Request[AnyContent], AuthToken) => Result): Action[AnyContent] =
    AuthAction(parse.anyContent)(func)

  def AuthAction(parser: BodyParser[AnyContent])(func: (Request[AnyContent], AuthToken) => Result): Action[AnyContent] =
    Action(parser) { implicit request =>
      val optionalHeader =
        request.headers.get("Authorization") orElse
        request.getQueryString("auth")

      val token =
        for {
          TokenIdRegex(id) <- optionalHeader
          token            <- AuthService.read(id)
        } yield token

      token match {
        case Some(token) => func(request, token)
        case None => Unauthorized(Json.obj("status" -> "error"))
      }
    }

  implicit class AuthResultOps(result: Result) {
    def withAuthToken(token: AuthToken): Result =
      result.withHeaders(
        "Authorization" -> s"Bearer ${token.id}"
      )
  }
}