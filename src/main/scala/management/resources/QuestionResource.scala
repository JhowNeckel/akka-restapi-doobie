package management.resources

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.server.Route
import entities.{Question, Status}
import management.routing.MyResource
import services.QuestionService
import spray.json._

import scala.util.Success

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val questionFormat = jsonFormat3(Question)
  implicit val orderFormat = jsonFormat1(Status)
}

trait QuestionResource extends MyResource with JsonSupport {

  val questionService: QuestionService

  def questionRoutes: Route =
    post {
      entity(as[Question]) { question =>
        complete(questionService.createQuestion(question))
      }
    }
//    pathPrefix("questions") {
//    post {
//      entity(as[Question]) { question =>
//        completeWithLocationHeader(
//          resourceId = questionService.createQuestion(question),
//          ifDefinedStatus = Status(201), ifEmptyStatus = Status(409))
//      }
//    }
//    path(Segment) { id =>
//      get {
//        complete(questionService.getQuestion(id))
//      } ~
//      put {
//        entity(as[Question]) { update =>
//          complete(questionService.updateQuestion(id, update))
//        }
//      } ~
//      delete {
//        complete(questionService.deleteQuestion(id))
//      }
//    }
//  }
}
