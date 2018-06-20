package services

import entities.Question

import scala.concurrent.{ExecutionContext, Future}

class QuestionService(implicit val ec: ExecutionContext) {

  var questions = Vector.empty[Question]

  def createQuestion(question: Question): Future[Option[Question]] = {
    println(s"Created Question ${question.title} Succesfuly")
    Future.successful(Some(question))
  }
  def getQuestion(id: String): Future[Option[Question]] = {
    Future.successful(Some(Question(id,"Get Qustion", "The return of a get request")))
  }
  def updateQuestion(id: String, update: Question): Future[Option[Question]] = {
    Future.successful(Some(update))
  }
  def deleteQuestion(id: String): Future[Unit] = {
    println("Quest Deleted")
    Future.successful(None)
  }

}
