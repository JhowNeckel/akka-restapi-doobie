package services

import handler.PersonHandler
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.{ActorMaterializer, Materializer}
import akka.pattern.ask
import com.typesafe.config.{Config, ConfigFactory}
import handler.PersonHandler.Register
import inj.ApiModule

import scala.io.StdIn

case class Equipamento(serial: Long, nome: String)
case class Usuario(nome: String, idade: Int, email: String)

object AkkaHttpService extends App {

  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val module = ApiModule()
  val config = ConfigFactory.load()
  val personHandler = system.actorOf(PersonHandler.props(module))

  val route =
    path("api") {
      path("register") {
        get {
          parameters(('nome, 'idade.as[Int], 'email)).as(Usuario) { usuario =>
            println(usuario)
            complete("Hello world!")
          }
        }
      } ~
      path("search") {
        get {
          parameters(('serial.as[Long], 'nome)).as(Equipamento) { equipamento =>
            println(equipamento)
            complete("Hello world!")
          }
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 9000)
  println(s"Server online at http://localhost:9000/\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
}
