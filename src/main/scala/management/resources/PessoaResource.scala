package management.resources

import akka.http.scaladsl.server.Route
import doobie.implicits._
import entities.{Pessoa, Status}
import management.routing.MyResource
import services.PessoaService

trait PessoaResource extends MyResource with JsonSupport {

  val pessoaService: PessoaService

  def pessoaRoutes: Route =
    post {
      entity(as[Pessoa]) { pessoa =>
        val result = for {
          r <- pessoaService.upsert(sql"insert into pessoa (nome, email) values (${pessoa.nome}, ${pessoa.email})")
        } yield Status(r.get)
        complete(result)
      }
    }
}
