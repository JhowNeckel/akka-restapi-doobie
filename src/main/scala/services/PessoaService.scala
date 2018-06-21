package services

import dao.DBUtils._
import doobie._
import doobie.implicits._
import entities.Pessoa

import scala.concurrent.{ExecutionContext, Future}

class PessoaService(implicit ec: ExecutionContext) extends Service[Fragment, Pessoa] {

  override def select(sql: Fragment): Future[Option[Pessoa]] = {
    sql.query[Option[Pessoa]].unique.transact(getTransactor).unsafeToFuture()
  }

  override def list(sql: Fragment): Future[Option[List[Pessoa]]] = {
    for {
      r <- sql.query[Pessoa].to[List].transact(getTransactor).unsafeToFuture()
    } yield Option(r)
  }

  override def upsert(sql: Fragment): Future[Option[Int]] = {
    for {
      r <- sql.update.run.transact(getTransactor).unsafeToFuture()
    } yield Option(r)
  }

  override def remove(sql: Fragment): Future[Option[Int]] = {
    for {
      r <- sql.update.run.transact(getTransactor).unsafeToFuture()
    } yield Option(r)
  }
}
