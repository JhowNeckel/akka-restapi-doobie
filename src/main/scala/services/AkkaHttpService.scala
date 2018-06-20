package services

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.io.StdIn

case class Equipamento(serial: Long, nome: String)
case class Usuario(nome: String, idade: Int, email: String)

object AkkaHttpService extends App {

  implicit val system = ActorSystem()
  implicit val ec = system.dispatcher
  implicit val materializer = ActorMaterializer()

  val route =
    path("api") {
      get {
        parameters(('nome, 'idade.as[Int], 'email)).as(Usuario) { usuario =>
            println(usuario)
            complete("Hello world!")
          }
      } ~
      post {
        parameters(('serial.as[Long], 'nome)).as(Equipamento) { equipamento =>
          println(equipamento)
          complete("Hello world!")
        }
      }
    }

  val bindingFuture = Http().bindAndHandle(route, "localhost", 9000)
  println(s"Server online at http://localhost:9000/\nPress RETURN to stop...")
  StdIn.readLine()
  bindingFuture.flatMap(_.unbind()).onComplete(_ => system.terminate())
}
