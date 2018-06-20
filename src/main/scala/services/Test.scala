package services

import scala.concurrent.duration._
import akka.actor._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import management.RestInterface

object Test extends App with RestInterface {
  val config = ConfigFactory.load()
  val host = "localhost"
  val port = 9000

  implicit val system = ActorSystem("quiz-management-service")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher
  implicit val timeout = Timeout(10 seconds)

  val api = routes

  Http().bindAndHandle(api, host, port) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}")
  } recover { case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }
}
