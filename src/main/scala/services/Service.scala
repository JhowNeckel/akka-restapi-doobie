package services

import scala.concurrent.Future

trait Service [PK, T] {
  def insert(e: T): Future[Int]
  def update(e: T, pk: PK): Future[Int]
  def remove(pk: PK): Future[Int]
  def select(pk: PK): Future[Option[Either[String, T]]] = Future successful None
  def list: Future[Option[Either[List[T], List[T]]]] = Future successful None
}
