package management.resources

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import entities.{Pessoa, Status}

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val statusFormat = jsonFormat1(Status)
  implicit val pessoaFormat = jsonFormat3(Pessoa)
}
