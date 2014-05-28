package models

import scala.slick.driver.H2Driver.simple._

object ChatRoom {
  def createTables =
    database.withSession { implicit session =>
      AuthTokens.all.ddl.create
      Messages.all.ddl.create
    }

  def dropTables =
    database.withSession { implicit session =>
      AuthTokens.all.ddl.drop
      Messages.all.ddl.drop
    }

  def login(name: String): AuthToken =
    database.withSession { implicit session =>
      val token = AuthToken.create(name)
      AuthTokens.all += token
      token
    }

  def post(text: String): Unit =
    database.withSession { implicit session =>
      Messages.all += Message(text)
    }

  def messages: List[Message] =
    database.withSession { implicit session =>
      Messages.all.list
    }
}