package models

import scala.slick.driver.H2Driver.simple._

object AuthService {
  def login(name: String): AuthToken =
    database.withSession { implicit session =>
      val token = AuthToken.create(name)
      AuthTokens.all += token
      token
    }

  def read(id: String): Option[AuthToken] =
    database.withSession { implicit session =>
      AuthTokens.all.
        filter(_.id === id).
        firstOption
    }
}