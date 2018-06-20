package repositories

import doobie.util.fragment.Fragment

import scala.concurrent.Future

trait Repository {
  def upsert[A](sql: Fragment): Future[Int]
  def select[A](sql: Fragment): Future[A]
  def remove(sql: Fragment): Future[Int]
}
