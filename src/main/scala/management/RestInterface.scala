package management

import management.resources.QuestionResource
import services.QuestionService

import scala.concurrent.ExecutionContext

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val questionService = new QuestionService()

  val routes = questionRoutes
}

trait Resources extends QuestionResource