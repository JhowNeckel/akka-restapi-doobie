package model

import akka.actor.{Actor, ActorLogging, Props}
import repositories.Repo

class UserHandler(db: Repo) extends Actor with ActorLogging {

  import UserHandler._

  implicit val ec = context.dispatcher

  override def receive: Receive = {
    case Register(id, pwd) =>
      sender() ! db.upsert(id, pwd)

    case Update(id, details) =>
      sender() ! db.upsert(id, details)

    case GetUser(username) =>
      val originalSender = sender()

      db.get(username).foreach {
        case Some(details) => originalSender ! User(username, details)
        case None => originalSender ! UserNotFound
      }

    case DeleteUser(username) =>
      val originalSender = sender()

      db.del(username).foreach {
        case i if i > 0 => originalSender ! UserDeleted(username)
        case _ => originalSender ! UserNotFound(username)
      }
  }
}

object UserHandler {
  def props(db: Repo): Props = Props(new UserHandler(db))

  case class User(username: String, details: String)
  case class Register(username: String, password: String)
  case class Update(username: String, details: String)
  case class GetUser(username: String)
  case class DeleteUser(username: String)
  case class UserNotFound(username: String)
  case class UserDeleted(username: String)
}
