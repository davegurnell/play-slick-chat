package models

import org.specs2.mutable._

class ChatServiceSpec extends Specification {
  "ChatService.createTables" should {
    "create a blank database" in dbExample {
      ChatService.messages mustEqual Nil
    }
  }

  "ChatService.post" should {
    "add messages to the database" in dbExample {
      ChatService.post("Hi!")
      ChatService.post("Hi there!")

      val messages = ChatService.messages

      messages.map(_.text) mustEqual List("Hi!", "Hi there!")
      messages.map(_.createdAt) mustEqual messages.map(_.createdAt).sorted
    }
  }

  def dbExample[A](fn: => A) = {
    try {
      ChatService.createTables
      fn
    } finally {
      ChatService.dropTables
    }
  }
}
