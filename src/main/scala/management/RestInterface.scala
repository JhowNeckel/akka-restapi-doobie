package management

import management.resources.PessoaResource
import services.PessoaService

import scala.concurrent.ExecutionContext

trait RestInterface extends Resources {

  implicit def executionContext: ExecutionContext

  lazy val pessoaService = new PessoaService()

  val routes = pessoaRoutes

}

trait Resources extends PessoaResource