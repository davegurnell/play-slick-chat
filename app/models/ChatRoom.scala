package models

object ChatRoom {
  var _messages = Seq.empty[Message]

  def post(text: String): Unit =
    _messages = _messages :+ Message(text)

  def messages: List[Message] =
    _messages.toList
}