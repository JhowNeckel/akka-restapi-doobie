package management

import cats.effect.IO
import dao.DatabaseConnector
import doobie.util.transactor.Transactor
import management.resources.PessoaResource
import services.PessoaService

import scala.concurrent.ExecutionContext

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext
  implicit def db: DatabaseConnector
  implicit def xa: Transactor[IO] = db.getTransactor

  lazy val pessoaService = new PessoaService()

  val routes = pessoaRoutes

}

trait Resources extends PessoaResource