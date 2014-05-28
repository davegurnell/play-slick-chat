package models

import org.specs2.mutable._

class ChatRoomSpec extends Specification {
  "ChatRoom.createTables" should {
    "create a blank database" in dbExample {
      ChatRoom.messages mustEqual Nil
    }
  }

  "ChatRoom.post" should {
    "add messages to the database" in dbExample {
      ChatRoom.post("Hi!")
      ChatRoom.post("Hi there!")

      val messages = ChatRoom.messages

      messages.map(_.text) mustEqual List("Hi!", "Hi there!")
      messages.map(_.createdAt) mustEqual messages.map(_.createdAt).sorted
    }
  }

  def dbExample[A](fn: => A) = {
    try {
      ChatRoom.createTables
      fn
    } finally {
      ChatRoom.dropTables
    }
  }
}
