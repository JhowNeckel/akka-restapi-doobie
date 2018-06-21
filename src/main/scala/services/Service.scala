package services

import doobie.util.fragment.Fragment
import scala.concurrent.Future

trait Service [S, T] {
  def select(sql: Fragment): Future[Option[T]]
  def list(sql: Fragment): Future[Option[List[T]]]
  def upsert(sql: Fragment): Future[Option[Int]]
  def remove(sql: Fragment): Future[Option[Int]]
}
