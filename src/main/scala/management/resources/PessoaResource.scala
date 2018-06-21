package management.resources

import akka.http.scaladsl.server.Route
import doobie.implicits._
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
            r <- pessoaService.upsert(sql"insert into pessoa (nome, email) values (${pessoa.nome}, ${pessoa.email})")
          } yield Status(r.get)
          complete(result)
        }
      }
    } ~ path("list") {
      post {
        complete(pessoaService.list(sql"select * from pessoa"))
      }
    } ~ path("search") {
      post {
        extractStrictEntity(1.seconds) { entity =>
          val id = entity.data.utf8String.toLong
          complete(pessoaService.select(sql"select * from pessoa where id = $id"))
        }
      }
    }
}
