package management.resources

import akka.http.scaladsl.server.Route
import entities.{Pessoa, Status}
import management.routing.MyResource
import services.PessoaService
import scala.concurrent.duration._

trait PessoaResource extends MyResource with JsonSupport {

  val pessoaService: PessoaService

  def pessoaRoutes: Route =
    path("register") {
      post {
        entity(as[Pessoa]) { pessoa =>
          val result = for {
            result <- pessoaService.insert(pessoa)
          } yield Status(result)
          complete(result)
        }
      }
    } ~ path("list") {
      post {
        complete(pessoaService.list)
      }
    } ~ path("search") {
      post {
        extractStrictEntity(1.second) { entity =>
          val id = entity.data.utf8String.toLong
          complete(pessoaService.select(id))
        }
      }
    } ~ path("remove") {
      post {
        extractStrictEntity(1.second) { entity =>
          val id = entity.data.utf8String.toLong
          val result = for {
            result <- pessoaService.remove(id)
          } yield Status(result)
          complete(result)
        }
      }
    }
}
