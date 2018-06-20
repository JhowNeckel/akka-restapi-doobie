package management.routing

import akka.http.scaladsl.marshalling.{ToResponseMarshallable, ToResponseMarshaller}

import scala.concurrent.{ExecutionContext, Future}
import akka.http.scaladsl.model.headers.Location
import akka.http.scaladsl.server.{Directives, Route}
import entities.Status
import management.resources.JsonSupport

trait MyResource extends Directives with JsonSupport {

  implicit def executionContext: ExecutionContext

  def completeWithLocationHeader[T](resourceId: Future[Option[T]], ifDefinedStatus: Status, ifEmptyStatus: Status): Route =
    onSuccess(resourceId) {
      case Some(t) => completeWithLocationHeader(ifDefinedStatus, t)
      case None => complete(ToResponseMarshallable(ifEmptyStatus))
    }

  def completeWithLocationHeader[T](status: Status, resourceId: T): Route =
    extractRequestContext { requestContext =>
      val request = requestContext.request
      val location = request.uri.copy(path = request.uri.path / resourceId.toString)
      respondWithHeader(Location(location)) {
        complete(ToResponseMarshallable(status))
      }
    }

  def complete[T: ToResponseMarshaller](resource: Future[Option[T]]): Route =
    onSuccess(resource) {
      case Some(t) => complete(ToResponseMarshallable(t))
    }

}
