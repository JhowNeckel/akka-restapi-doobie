package services

import cats.effect.IO
import doobie._
import doobie.implicits._
import doobie.postgres.sqlstate
import doobie.util.query.Query0
import doobie.util.transactor.Transactor
import entities.Pessoa
import statement.StatementGenerator

import scala.concurrent.{ExecutionContext, Future}

class PessoaService(implicit xa: Transactor[IO], ec: ExecutionContext) extends Service[Long, Pessoa] {

  lazy val insertPessoaSQL = StatementGenerator[Pessoa].insert("pessoa")
  lazy val listPessoasSQL = StatementGenerator[Pessoa].select("pessoa")
  lazy val selectPessoaSQL = (pk: Long) => StatementGenerator[Pessoa].selectById("pessoa", pk)
  lazy val removePessoaSQL = (pk: Long) => StatementGenerator[Pessoa].remove("pessoa", pk)

  override def select(pk: Long): Future[Option[Either[String, Pessoa]]] = for {
    restul <- Query0[Pessoa](selectPessoaSQL(pk)).unique.transact(xa).attemptSomeSqlState {
      case _ => "Usuário não encontrado"
    }.unsafeToFuture()
  } yield Option(restul)

  override def list: Future[Option[Either[List[Pessoa], List[Pessoa]]]] = for {
    result <- Query0[Pessoa](listPessoasSQL).to[List].transact(xa).attemptSomeSqlState {
      case _ => List.empty[Pessoa]
    }.unsafeToFuture()
  } yield Option(result)

  override def upsert(e: Pessoa): Future[Int] =
    Update[Pessoa](insertPessoaSQL).run(e).transact(xa).unsafeToFuture()

  override def remove(pk: Long): Future[Int] =
    Query0[Pessoa](removePessoaSQL(pk)).toFragment.update.run.transact(xa).unsafeToFuture()
}
