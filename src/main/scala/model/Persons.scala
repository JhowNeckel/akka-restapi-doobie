package model

import doobie._
import doobie.implicits._
import entities.Person
import services.Service
import dao.DBUtils._

import scala.concurrent.Future

abstract class Persons extends Service[Fragment, Person] {

  override def select(sql: Fragment): Future[Option[Person]] = {
    sql.query[Option[Person]].unique.transact(getTransactor).unsafeToFuture()
  }

  override def upsert(sql: Fragment): Future[Int] = {
    sql.update.run.transact(getTransactor).unsafeToFuture()
  }
}
