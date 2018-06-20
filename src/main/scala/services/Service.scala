package services

import doobie.util.fragment.Fragment
import scala.concurrent.Future

trait Service [S, T] {
  def select(sql: Fragment): Future[Option[T]] = Future.successful(None)
  def list(sql: Fragment): Future[Option[List[T]]] = Future.successful(None)
  def upsert(sql: Fragment): Future[Int]
  def remove(sql: Fragment): Future[Option[T]] = Future.successful(None)
}
