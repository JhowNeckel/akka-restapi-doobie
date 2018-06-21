package main

import akka.actor._
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.config.ConfigFactory
import dao.DatabaseConnector
import management.RestInterface

import scala.concurrent.duration._

object WebServer extends App with RestInterface {
  implicit val config = ConfigFactory.load()

  val host = config.getString("http.interface")
  val port = config.getInt("http.port")

  implicit val system = ActorSystem("api")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher
  implicit val db = new DatabaseConnector()
  implicit val timeout = Timeout(10 seconds)

  val api = routes

  Http().bindAndHandle(api, host, port) map { binding =>
    println(s"REST interface bound to ${binding.localAddress}")
  } recover { case ex =>
    println(s"REST interface could not bind to $host:$port", ex.getMessage)
  }
}
