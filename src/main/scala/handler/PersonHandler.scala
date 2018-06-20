package handler

import akka.actor.{Actor, ActorLogging, Props}
import doobie._
import doobie.implicits._
import entities.Person
import inj.ApiModule
import services.Service

class PersonHandler(implicit val service: Service[Fragment, Person]) extends Actor with ActorLogging {

  import PersonHandler._

  val ec = context.dispatcher

  override def receive: Receive = {
    case Register(name, age) =>
      println("Gravando usuário")
      sender() ! service.upsert(sql"insert into person (name, age) values ($name, $age)")
  }
}

object PersonHandler {
  def props(module: ApiModule): Props = Props(classOf[PersonHandler], module.db.person)

  case class Register(name: String, age: Int)
}
