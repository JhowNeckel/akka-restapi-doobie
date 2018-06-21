package services

import cats.effect.IO
import doobie._
import doobie.implicits._
import doobie.util.transactor.Transactor
import entities.Pessoa

import scala.concurrent.{ExecutionContext, Future}

class PessoaService(implicit xa: Transactor[IO], ec: ExecutionContext) extends Service[Fragment, Pessoa] {

  override def select(sql: Fragment): Future[Option[Pessoa]] = {
    sql.query[Option[Pessoa]].unique.transact(xa).unsafeToFuture()
  }

  override def list(sql: Fragment): Future[Option[List[Pessoa]]] = {
    for {
      r <- sql.query[Pessoa].to[List].transact(xa).unsafeToFuture()
    } yield Option(r)
  }

  override def upsert(sql: Fragment): Future[Option[Int]] = {
    for {
      r <- sql.update.run.transact(xa).unsafeToFuture()
    } yield Option(r)
  }

  override def remove(sql: Fragment): Future[Option[Int]] = {
    for {
      r <- sql.update.run.transact(xa).unsafeToFuture()
    } yield Option(r)
  }
}
